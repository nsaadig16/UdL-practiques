"""
YalSAT — Yet Another Local Search SAT Solver
Based on: Biere (2014), YalSAT at SAT Competition 2017
Core: probSAT variable selection + Luby restart scheme
probSAT: Balint & Schöning (2012), SAT Competition winner
"""

import argparse
from random import Random
from typing import List, Tuple, Dict

Clause = List[int]


def luby():
    i = 0
    while True:
        i += 1
        k = i.bit_length()
        if i == (1 << k) - 1:
            yield 1 << (k - 1)
        else:
            yield (1 << (k - 1 - (i - (1 << (k - 1))).bit_length()))

def luby_seq():
    def _luby(i):
        k = 1
        while (1 << k) <= i:
            k += 1
        if i == (1 << k) - 1:
            return 1 << (k - 1)
        return _luby(i - (1 << (k - 1)) + 1)
    i = 1
    while True:
        yield _luby(i)
        i += 1


def read_file(path: str) -> Tuple[int, List[Clause]]:
    with open(path, "r") as f:
        lines = f.readlines()
    clauses = []
    num_vars = 0
    for line in lines:
        if not line or line[0] == 'c':
            continue
        elif line[0] == 'p':
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
    lit_pos: List[Dict[int, bool]] = [{} for _ in range(num_vars + 1)]
    clauses_vars = []
    for ci, clause in enumerate(clauses):
        cv = []
        for lit in clause:
            v = abs(lit)
            positive = lit > 0
            var_to_clauses[v].append(ci)
            lit_pos[v][ci] = positive
            cv.append((v, positive))
        clauses_vars.append(cv)
    return var_to_clauses, lit_pos, clauses_vars


def compute_sat_counts(num_clauses, num_vars, var_to_clauses, lit_pos, vals):
    sat_count = [0] * num_clauses
    for v in range(1, num_vars + 1):
        cur = vals[v]
        for ci in var_to_clauses[v]:
            if lit_pos[v][ci] == cur:
                sat_count[ci] += 1
    return sat_count


def main(args: argparse.Namespace):
    rnd = Random(args.seed)
    num_vars, clauses = read_file(args.cnf_file)
    var_to_clauses, lit_pos, clauses_vars = build_structures(num_vars, clauses)
    num_clauses = len(clauses)

    avg_k = sum(len(c) for c in clauses) / len(clauses)

    cb = args.cb if args.cb is not None else max(2.3, min(4.5, 2.3 + (avg_k - 3) * 0.4))
    max_break = max(len(c) for c in clauses)
    weight_table = [1.0 / (1.0 + b) ** cb for b in range(max_break + 2)]

    luby_unit = args.luby_unit
    luby_gen = luby_seq()
    total_flips_allowed = args.max_tries * args.max_flips  # global budget

    if args.debug:
        print(f"avg_k={avg_k:.2f}  cb={cb:.3f}  luby_unit={luby_unit}")

    flips_done = 0
    restart_num = 0

    while flips_done < total_flips_allowed:
        run_limit = luby_unit * next(luby_gen)
        restart_num += 1

        # Random initial assignment
        vals = [False] * (num_vars + 1)
        for i in range(1, num_vars + 1):
            vals[i] = rnd.getrandbits(1) == 1

        sat_count = compute_sat_counts(num_clauses, num_vars, var_to_clauses, lit_pos, vals)

        unsat_list: List[int] = []
        unsat_pos = [-1] * num_clauses
        for ci in range(num_clauses):
            if sat_count[ci] == 0:
                unsat_pos[ci] = len(unsat_list)
                unsat_list.append(ci)

        def add_unsat(ci):
            if unsat_pos[ci] == -1:
                unsat_pos[ci] = len(unsat_list)
                unsat_list.append(ci)

        def remove_unsat(ci):
            pos = unsat_pos[ci]
            if pos == -1:
                return
            last = unsat_list[-1]
            unsat_list[pos] = last
            unsat_pos[last] = pos
            unsat_list.pop()
            unsat_pos[ci] = -1

        watch1 = [-1] * num_clauses
        for ci in range(num_clauses):
            if sat_count[ci] > 0:
                for v, _ in clauses_vars[ci]:
                    if lit_pos[v][ci] == vals[v]:
                        watch1[ci] = v
                        break

        def flip(var):
            vals[var] = not vals[var]
            cur = vals[var]
            for ci in var_to_clauses[var]:
                if lit_pos[var][ci] == cur:
                    sat_count[ci] += 1
                    if sat_count[ci] == 1:
                        watch1[ci] = var
                        remove_unsat(ci)
                else:
                    sat_count[ci] -= 1
                    if sat_count[ci] == 0:
                        watch1[ci] = -1
                        add_unsat(ci)
                    elif watch1[ci] == var:
                        # find new watcher
                        for v2, _ in clauses_vars[ci]:
                            if lit_pos[v2][ci] == vals[v2]:
                                watch1[ci] = v2
                                break

        if args.debug:
            print(f"restart={restart_num} run_limit={run_limit} unsat={len(unsat_list)}")

        for _ in range(min(run_limit, total_flips_allowed - flips_done)):
            n_unsat = len(unsat_list)
            if n_unsat == 0:
                if args.debug:
                    print(f"SOLVED at restart={restart_num} total_flips={flips_done}")
                    for i in range(1, num_vars + 1):
                        print(f"X{i} -> {'T' if vals[i] else 'F'}")
                else:
                    result = [str(i if vals[i] else -i) for i in range(1, num_vars + 1)]
                    result.append("0")
                    print("s SATISFIABLE")
                    print("v " + " ".join(result))
                return

            flips_done += 1

            picked_ci = unsat_list[rnd.randrange(n_unsat)]
            clause_vars = clauses_vars[picked_ci]

            weights = []
            for v, _ in clause_vars:
                brk = 0
                for ci2 in var_to_clauses[v]:
                    if sat_count[ci2] == 1 and watch1[ci2] == v:
                        brk += 1
                weights.append(weight_table[min(brk, max_break + 1)])

            total_w = sum(weights)
            r = rnd.random() * total_w
            chosen = clause_vars[-1][0]
            for idx in range(len(clause_vars)):
                r -= weights[idx]
                if r <= 0:
                    chosen = clause_vars[idx][0]
                    break

            flip(chosen)

    print("s UNSATISFIABLE")


# ---------------------------------------------------------------------------
if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="YalSAT: probSAT + Luby restarts (SAT Competition state-of-the-art for random k-SAT)"
    )
    parser.add_argument("cnf_file", type=str)
    parser.add_argument("--max_tries", type=int, default=200, help="Max Luby restart cycles (total budget = max_tries * max_flips)")
    parser.add_argument("--max_flips", type=int, default=10000, help="Flips per try (also sets total budget with max_tries)")
    parser.add_argument("--luby_unit", type=int, default=None, help="Base flips per Luby unit (default: auto ~ num_vars)")
    parser.add_argument("--seed", type=int, default=123456)
    parser.add_argument("--cb", type=float, default=None, help="probSAT break exponent (default: auto ~2.5 for 3-SAT)")
    parser.add_argument("--debug", action="store_true", default=False)
    args = parser.parse_args()

    if args.luby_unit is None:
        with open(args.cnf_file) as f:
            for line in f:
                if line.startswith('p'):
                    n = int(line.split()[2])
                    args.luby_unit = max(32, n // 2)
                    break

    main(args)