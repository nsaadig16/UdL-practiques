from hlogedu.search.problem import action, Problem, DInterval
from typing import Tuple
from dataclasses import dataclass

@dataclass(frozen=True, order=True)
class State():
    @dataclass(frozen=True, order=True)
    class Boat():
        pos : str = "W"
        monks : int = 0
        cannibals : int = 0

        def __str__(self):
            return f"Boat[{self.pos}] ({'M' * self.monks}{'C' * self.cannibals})"

    monks : Tuple[int,int]
    cannibals : Tuple[int,int]
    boat : Boat

    def __str__(self):
        w = f"{'M' * self.monks[0]}{'C' * self.cannibals[0]}"
        e = f"{'M'*self.monks[1]}{'C'*self.cannibals[1]}"
        return f"West( {w} ) || {self.boat} || East:( {e} )"

class MonksCannibals(Problem):
    """
    ## Monks and Cannibals problem
    > This class implements the **Monks and cannibals problem**.

    In this problem there are three monks and three cannibals in the west side of a river,
    and the objective is to get them to the east side by boat. The boat can only carry
    two people at the same time, and there always has to be someone in it so it can travel
    to the other side. We can't let there be more cannibals than monks on one side at any
    moment because the monks will be eaten.

    The possible actions are:
    - Traversing to the other side
    - Pick up someone
    - Drop someone
    """
    NAME = "MonksCannibals"

    def get_start_states(self):
        return [State(
            monks=(3,0), cannibals=(3,0),
            boat=State.Boat()
        )]

    def is_goal_state(self, state : State):
        return state == State(
            monks=(0,3), cannibals=(0,3),
            boat=State.Boat("E", 0, 0)
        )

    def is_valid_state(self, state):
        return True
    

    @action(cost=1)
    def traverse(self, state : State):
        boat = state.boat

        if boat.monks + boat.cannibals < 1:
            return None

        i = 0 if boat.pos == "W" else 1
        monks = state.monks[i]
        cannibals = state.cannibals[i]
        if monks > 0 and monks < cannibals:
            return None
        
            
        new_pos = "E" if boat.pos == "W" else "W"

        return State(
            monks=state.monks, cannibals=state.cannibals,
            boat=State.Boat(
                pos=new_pos,
                monks=boat.monks, cannibals=boat.cannibals
            )
        )

    @action(DInterval(0,2), DInterval(0,2), cost=1)
    def pick_up(self, state : State, monks_picked : int, cannibals_picked : int):
        people_picked = monks_picked + cannibals_picked # 2 + 0
        people_on_boat = state.boat.monks + state.boat.cannibals # 1 + 0

        if people_picked > 2 or people_picked == 0:
            return None
 
        if people_on_boat == 2 or people_on_boat + people_picked > 2:
            return None

        if people_on_boat > people_picked:
            return None
        
        i = 0 if state.boat.pos == "W" else 1
        monks_on_land = state.monks[i]
        cannibals_on_land = state.cannibals[i]
        
        if monks_picked > monks_on_land or cannibals_picked > cannibals_on_land:
            return None
        
        monks_remaining_on_land = monks_on_land - monks_picked
        cannibals_remaining_on_land = cannibals_on_land - cannibals_picked

        if monks_remaining_on_land > 0 and monks_remaining_on_land < cannibals_remaining_on_land:
            return None
        
        return self._update_state(state, monks_remaining_on_land, cannibals_remaining_on_land)

    @action(DInterval(0,2), DInterval(0,2), cost=1)
    def drop_off(self, state : State, monks_dropped : int, cannibals_dropped : int):
        people_dropped = monks_dropped + cannibals_dropped
        people_on_boat = state.boat.monks + state.boat.cannibals

        if people_dropped > 2 or people_dropped == 0:
            return None
        
        if people_on_boat < people_dropped:
            return None
        
        if state.boat.monks < monks_dropped or state.boat.cannibals < cannibals_dropped:
            return None
        
        i = 0 if state.boat.pos == "W" else 1

        monks_remaining_on_land = state.monks[i] + monks_dropped
        cannibals_remaining_on_land = state.cannibals[i] + cannibals_dropped

        if monks_remaining_on_land > 0 and monks_remaining_on_land < cannibals_remaining_on_land:
            return None
        
        return self._update_state(state, monks_remaining_on_land, cannibals_remaining_on_land)


    def _update_state(self, state : State, updated_monks : int, updated_cannibals : int):
        if state.boat.pos == "W":
            new_monks = (updated_monks, state.monks[1])
            new_cannibals = (updated_cannibals, state.cannibals[1])
        else:
            new_monks = (state.monks[0], updated_monks)
            new_cannibals = (state.cannibals[0], updated_cannibals)

        return State(
            monks=new_monks, cannibals=new_cannibals,
            boat=State.Boat(
                pos=state.boat.pos, 
                monks= 3 - (new_monks[0] + new_monks[1]),
                cannibals= 3 - (new_cannibals[0] + new_cannibals[1])
            )
        )