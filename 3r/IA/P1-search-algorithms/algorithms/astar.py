from hlogedu.search.algorithm import Algorithm, Node, Solution
from hlogedu.search.containers import PriorityQueue
from hlogedu.search.problem import Problem

class TreeAStar(Algorithm):
    NAME = "my-tree-astar"

    def __init__(self, problem : Problem):
        super().__init__(problem)
        self.fringe : PriorityQueue = PriorityQueue(key= lambda node: node.state)
    
    def run(self, heuristic):
        expand_counter = 0
        roots = [Node(state=s, f_cost=heuristic(s)) for s in self.problem.get_start_states()]

        for r in roots:
            self.fringe.push(r, r.f_cost)

        while self.fringe:
            n : Node = self.fringe.pop()
 
            if self.problem.is_goal_state(n.state):
                return Solution(self.problem, roots, solution_node=n)
            expand_counter += 1
            n.expanded_order = expand_counter
            n.location = Node.Location.EXPANDED

            for s, a, c in sorted(self.problem.get_successors(n.state), key= lambda x : x[0]):
                g_cost = n.cost + c
                h_cost = heuristic(s)
                ns = Node(state=s, action= a, cost=g_cost, h_cost=h_cost, f_cost=g_cost + h_cost, parent=n)
                if s in [st for st in self.fringe]:
                    continue
                n.add_successor(ns)
                self.fringe.push(ns, ns.f_cost)
                
        return Solution(self.problem, roots)

class GraphAStar(Algorithm):
    NAME = "my-graph-astar"

    def __init__(self, problem : Problem):
        super().__init__(problem)
        self.fringe : PriorityQueue = PriorityQueue(key= lambda node: node.state)
        self.expanded : set = set()
    
    def run(self, heuristic):
        expand_counter = 0
        roots = [Node(state=s, f_cost=heuristic(s)) for s in self.problem.get_start_states()]
        # Dictionary where the best cost for each state is stored
        best_f_cost = {}

        for r in roots:
            self.fringe.push(r, r.f_cost)
            best_f_cost[r.state] = r.f_cost
        

        while self.fringe:
            n : Node = self.fringe.pop()

            # If the node has been replaced (the best cost is lower than it), ignore it
            if best_f_cost.get(n.state, float('inf')) < n.f_cost:
                continue

            self.expanded.add(n.state)
            expand_counter += 1
            n.expanded_order = expand_counter
            n.location = Node.Location.EXPANDED
            if self.problem.is_goal_state(n.state):
                return Solution(self.problem, roots, solution_node=n)
            

            for s, a, c in sorted(self.problem.get_successors(n.state), key= lambda x : x[0]):
                g_cost = n.cost + c
                h_cost = heuristic(s)
                ns = Node(state=s, action= a, cost=g_cost, h_cost=h_cost, f_cost=g_cost + h_cost, parent=n)

                # Since the cost of the node popped is guaranteed to be the lowest, we only check if the node has been expanded
                if ns.state in self.expanded:
                    continue

                if best_f_cost.get(ns.state, float('inf')) <= ns.f_cost:
                    continue
                n.add_successor(ns)
                self.fringe.push(ns, ns.f_cost)
                best_f_cost[ns.state] = ns.f_cost
        
        return Solution(self.problem, roots)