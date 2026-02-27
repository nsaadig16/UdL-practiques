from hlogedu.search.problem import action, Problem, Categorical # type: ignore


E = "_"

def idx_to_coords(idx):
    """
    Given the index in a 1D list, returns 
    the coords of the grid
    """
    return (idx % 3, idx // 3)


def coords_to_idx(x, y):
    """
    Given a position in the grid, returns
    the index of the 1D list
    """
    return y * 3 + x


def is_valid_coords(x, y):
    return x in range(3) and y in range(3)

def swap(state, pos1_x, pos1_y, pos2_x, pos2_y):
    pos1 = coords_to_idx(pos1_x,pos1_y)
    pos2 = coords_to_idx(pos2_x,pos2_y)
    st = list(state)
    aux = st[pos1]
    st[pos1] = st[pos2]
    st[pos2] = aux
    return "".join(st)

class Puzzle8(Problem):
    """
    ## Puzzle8 problem
    > This class implements the **Puzzle8 problem**.

    In this problem we have 8 pieces numbered 1 through 8 and an empty
    space in a 3x3 grid. The pieces start randomly in 8 of the 9 positions and the
    objective is to order the pieces in the drig, and leaving the first space empty.

    The possible actions are:
    - moving up into an empty space
    - moving down into an empty space
    - moving left into an empty space
    - moving right into an empty space
    """
    NAME = "Puzzle8"

    def get_start_states(self):
        # The state will be a string
        # For the provided example, it will be
        # "3_2615748"
        grid = [
            [3, E, 2],
            [6, 1, 5],
            [7, 4, 8]
        ]
        return ["".join(
            "".join([str(c) for c in row])
            for row in grid
        )]

    def is_goal_state(self, state):
        # TIP: sorted(lst) returns the list 'lst' sorted
        return state == "_12345678"

    def is_valid_state(self, state):
        return "".join(sorted(state)) == "12345678_"
    
    @action(Categorical(["U", "D", "L", "R"]), cost=1)
    def move(self, state, direction):
        empty_x, empty_y = idx_to_coords(state.index(E))
        if direction == "U":
            new_empty_x, new_empty_y = (empty_x,empty_y + 1)
            if is_valid_coords(new_empty_x, new_empty_y):
                return swap(state, empty_x, empty_y, new_empty_x, new_empty_y)
        elif direction == "D":
            new_empty_x, new_empty_y = (empty_x,empty_y - 1)
            if is_valid_coords(new_empty_x, new_empty_y):
                return swap(state, empty_x, empty_y, new_empty_x, new_empty_y)
        elif direction == "L":
            new_empty_x, new_empty_y = (empty_x + 1,empty_y)
            if is_valid_coords(new_empty_x, new_empty_y):
                return swap(state, empty_x, empty_y, new_empty_x, new_empty_y)
        elif direction == "R":
            new_empty_x, new_empty_y = (empty_x - 1,empty_y)
            if is_valid_coords(new_empty_x, new_empty_y):
                return swap(state, empty_x, empty_y,new_empty_x, new_empty_y)
