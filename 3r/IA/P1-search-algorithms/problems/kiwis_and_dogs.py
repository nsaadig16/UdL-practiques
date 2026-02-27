from dataclasses import dataclass

from hlogedu.search.problem import Problem, action, Categorical, DCategorical


@dataclass(frozen=True, order=True)
class State:
    kiwis: tuple[str, ...]
    dogs: tuple[str, ...]


# Problem
##############################################################################


class KiwisAndDogsProblem(Problem):
    NAME = "KiwisDogs"

    def __init__(self):
        super().__init__()
        # Assume we only have `nobody(X)` and `somebody(X)` conditions.
        # In case of having more than one condition, these will always be
        # a conjunction and will be separated by a comma.
        self.graph = {
            # A
            ("A", "B"): (3, "nobody(E)"),
            ("A", "C"): (4, ""),
            # B
            ("B", "A"): (3, "nobody(E)"),
            ("B", "C"): (1, ""),
            ("B", "G"): (5, ""),
            # C
            ("C", "B"): (1, ""),
            ("C", "D"): (2, "somebody(E),somebody(G)"),
            # D
            ("D", "C"): (2, "somebody(E),somebody(G)"),
            ("D", "E"): (8, "somebody(A)"),
            ("D", "F"): (3, "somebody(C)"),
            # E
            ("E", "D"): (8, "somebody(A)"),
            ("E", "F"): (5, ""),
            # F
            ("F", "D"): (3, "somebody(C)"),
            # G
            ("G", "F"): (7, ""),
            ("G", "B"): (5, ""),
        }
        self.num_kiwis = 2
        self.num_dogs = 1
        self.vertices = list(set([k[0] for k in self.graph.keys()]))

    def get_start_states(self):
        return [State(kiwis=("D", "F"), dogs=("C",))]

    def is_goal_state(self, state):
        return state == State(kiwis=("A", "A"), dogs=("E",))

    def is_valid_state(self, _):
        return True


    @action(
        DCategorical("vertices"),
        DCategorical("vertices"),
        Categorical(["kiwi", "dog"]),
    )
    def move(self, state: State, src: str, dst: str, animal: str):
        if (src, dst) not in self.graph:
            return None
        if animal == "kiwi" and src not in state.kiwis:
            return None
        if animal == "dog" and src not in state.dogs:
            return None
        constraints = self.graph[(src, dst)][1]
        if constraints and not self.can_traverse(state, constraints.split(",")):
            return None

        cost = self.graph[(src, dst)][0]
        kiwis = self.move_animal(state.kiwis, src, dst) if animal == "kiwi" else state.kiwis
        dogs = self.move_animal(state.dogs, src, dst) if animal == "dog" else state.dogs

        return (cost, State(kiwis, dogs))

    def can_traverse(self, state: State, constraints: list[str]):
        occupied_vertices = {v for v in state.kiwis}.union({v for v in state.dogs})
        sb_constraints = {c[-2] for c in constraints if c.startswith("somebody")}
        nb_constraints = {c[-2] for c in constraints if c.startswith("nobody")}
        sb_constraints_satisfied = sb_constraints.issubset(occupied_vertices)
        nb_constraints_satisfied = nb_constraints.isdisjoint(occupied_vertices) 
        return sb_constraints_satisfied and nb_constraints_satisfied     

    def move_animal(self, positions: tuple[str, ...], src: str, dst: str):
        new_positions = list(positions)
        new_positions.remove(src)
        new_positions.append(dst)
        return tuple(new_positions)
