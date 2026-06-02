import argparse
from random import Random
from typing import List, Tuple, Dict, Set

Clause = List[int]


def read_file(path: str) -> Tuple[int, List[Clause]]:
    with open(path, "r") as f:
        lines = f.readlines()
    clauses = []
    num_vars = 0
    for line in lines:
        if line.startswith("c"):
            continue
        elif line.startswith("p"):
            num_vars = int(line.split()[2])
        else:
            clause = list(map(int, line.split()[:-1]))
            if clause:
                clauses.append(clause)
    if num_vars == 0 or not clauses:
        print("Wrong file format!")
        exit()
    return num_vars, clauses


def build_structures(num_vars: int, clauses: List[Clause]):
    var_to_clauses = [[] for _ in range(num_vars + 1)]
    lit_sign: List[Dict[int, bool]] = [{} for _ in range(num_vars + 1)]

    for ci, clause in enumerate(clauses):
        for lit in clause:
            v = abs(lit)
            var_to_clauses[v].append(ci)
            lit_sign[v][ci] = lit > 0

    return var_to_clauses, lit_sign


def assign_vars(num_vars: int, rnd: Random) -> List[bool]:
    vals = [False] * (num_vars + 1)
    for i in range(1, num_vars + 1):
        vals[i] = rnd.getrandbits(1) == 1
    return vals


def compute_degrees(
    num_clauses: int,
    num_vars: int,
    var_to_clauses: List[List[int]],
    lit_sign: List[Dict[int, bool]],
    vals: List[bool],
) -> List[int]:
    degrees = [0] * num_clauses
    for v in range(1, num_vars + 1):
        cur = vals[v]
        for ci in var_to_clauses[v]:
            if lit_sign[v][ci] == cur:
                degrees[ci] += 1
    return degrees


def flip_delta(var: int, vals: List[bool], lit_sign: List[Dict[int, bool]], ci: int) -> int:
    cur = vals[var]
    positive = lit_sign[var][ci]
    if positive == cur:
        return -1   # will become unsatisfied
    else:
        return 1    # will become satisfied


def best_cscore_flip(
    num_vars: int,
    vals: List[bool],
    var_to_clauses: List[List[int]],
    lit_sign: List[Dict[int, bool]],
    degrees: List[int],
    d: int,
) -> int:
    best_var = -1
    best_cs = None
    has_cd = False

    for var in range(1, num_vars + 1):
        score = 0
        subscore = 0

        for ci in var_to_clauses[var]:
            db = degrees[ci]
            delta = flip_delta(var, vals, lit_sign, ci)
            da = db + delta

            if db == 0 and da > 0:
                score += 1
            elif db > 0 and da == 0:
                score -= 1

            if db == 1 and da == 2:
                subscore += 1
            elif db == 2 and da == 1:
                subscore -= 1

        cs = score + subscore // d
        is_cd = score >= 0 and cs > 0

        if not has_cd and is_cd:
            best_var, best_cs, has_cd = var, cs, True
        elif has_cd and is_cd:
            if cs > best_cs:
                best_var, best_cs = var, cs
        elif not has_cd:
            if best_cs is None or cs > best_cs:
                best_var, best_cs = var, cs

    return best_var


def apply_flip(
    var: int,
    vals: List[bool],
    var_to_clauses: List[List[int]],
    lit_sign: List[Dict[int, bool]],
    degrees: List[int],
    unsatisfied: Set[int],
):
    vals[var] = not vals[var]
    for ci in var_to_clauses[var]:
        cur = vals[var]
        positive = lit_sign[var][ci]
        if positive == cur:
            degrees[ci] += 1   # literal went false→true
        else:
            degrees[ci] -= 1   # literal went true→false
        if degrees[ci] == 0:
            unsatisfied.add(ci)
        else:
            unsatisfied.discard(ci)


def main(args: argparse.Namespace):
    rnd = Random(args.seed)
    num_vars, clauses = read_file(args.cnf_file)
    var_to_clauses, lit_sign = build_structures(num_vars, clauses)
    num_clauses = len(clauses)

    if args.d is not None:
        d = args.d
    else:
        avg_k = sum(len(c) for c in clauses) / len(clauses)
        d = max(1, int(13 - avg_k))

    stagnation_limit = args.stagnation if args.stagnation else args.max_flips

    for tries in range(args.max_tries):
        vals = assign_vars(num_vars, rnd)
        degrees = compute_degrees(num_clauses, num_vars, var_to_clauses, lit_sign, vals)
        unsatisfied = {i for i, d_ in enumerate(degrees) if d_ == 0}

        best_unsat = len(unsatisfied)
        no_improve = 0

        if args.debug:
            print(f"=== Try {tries} | unsat={len(unsatisfied)} | d={d} ===")

        for j in range(args.max_flips):
            n_unsat = len(unsatisfied)

            if n_unsat == 0:
                if args.debug:
                    print(f"SOLVED at try={tries} flip={j}")
                    for i in range(1, num_vars + 1):
                        print(f"X{i} -> {'T' if vals[i] else 'F'}")
                else:
                    result = [str(i if vals[i] else -i) for i in range(1, num_vars + 1)]
                    result.append("0")
                    print("s SATISFIABLE")
                    print("v " + " ".join(result))
                return

            if n_unsat < best_unsat:
                best_unsat = n_unsat
                no_improve = 0
            else:
                no_improve += 1
                if no_improve >= stagnation_limit:
                    if args.debug:
                        print(f"  stagnation restart at flip {j}")
                    break

            picked_ci = rnd.choice(list(unsatisfied))

            if rnd.random() < args.probability:
                clause = clauses[picked_ci]
                lit = rnd.choice(clause)
                var_index = abs(lit)
            else:
                var_index = best_cscore_flip(
                    num_vars, vals, var_to_clauses, lit_sign, degrees, d
                )
                if var_index == -1:
                    continue

            apply_flip(var_index, vals, var_to_clauses, lit_sign, degrees, unsatisfied)

    print("s UNSATISFIABLE")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="SAT solver (WalkSAT + cscore)")
    parser.add_argument("cnf_file", type=str)
    parser.add_argument("--max_tries", type=int, default=100)
    parser.add_argument("--max_flips", type=int, default=5000)
    parser.add_argument("--probability", type=float, default=0.5)
    parser.add_argument("--seed", type=int, default=123456)
    parser.add_argument("--d", type=int, default=None)
    parser.add_argument("--stagnation", type=int, default=100,
                        help="Early restart after N flips with no improvement")
    parser.add_argument("--debug", action="store_true", default=False)
    args = parser.parse_args()
    main(args)