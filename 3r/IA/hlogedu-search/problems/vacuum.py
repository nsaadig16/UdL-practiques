from hlogedu.search.problem import action, Problem, DRange # type: ignore


class VacuumProblem(Problem):
    """
    ## Vacuum problem
    > This class implements the **Vacuum problem**.

    In this problem we have 2 positions that can be clean or dirty. 
    The vacuum starts in one of these two positions and the objective
    is to ensure both positions are clean.

    The possible actions are:
    - move left
    - move right
    - sweep the current position
    """
    NAME = "Vacuum"

    def __init__(self):
        super().__init__()
        self.initial_pos = 0

    def get_start_states(self):
        return [
            (self.initial_pos, (True, True))
        ]

    def is_goal_state(self, state):
        return state[1][0] is False and state[1][1] is False

    def is_valid_state(self, state):
        return state[0] in range(0,2)
    
    # Step 1:
    # Use a function for Left, one for Right, and one for Sweep

    # Step 2:
    # Use DRange to combine Left and Right into "move"

    # Approx 3:
    # Implement the actions such that no redundant actions
    # exist.

    @action(cost=1)
    def left(self, state):
        if state[0] == 0:
            return None
        return (0, (state[1][0], state[1][1]))

    @action(cost=1)
    def right(self, state):
        if state[0] == 1:
            return None
        return (1, (state[1][0], state[1][1]))
    

    @action(cost=1)
    def sweep(self, state):
        pos = state[0]
        if state[1][pos] == False:
            return None
       # state[1][pos] = False
        cleaned = [state[1][0],state[1][1]]
        cleaned[pos] = False
        return (pos,tuple(cleaned))

    @action(DRange(2), cost = 1)
    def move(self, state, pos):
        return (pos,(state[1][0],state[1][1]))
 
        
    