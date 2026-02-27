from hlogedu.search.algorithm import Algorithm, Node, Solution #type: ignore
from hlogedu.search.containers import Stack #type: ignore
from hlogedu.search.problem import Problem #type: ignore

class TreeIDS(Algorithm):
    NAME = "my-tree-ids"
    def __init__(self, problem : Problem):
        super().__init__(problem)
        self.fringe = Stack()
    
    def run(self):
        def dls(problem : Problem, limit : int):
            roots = [Node(s) for s in self.problem.get_start_states()]
            for n in roots:
                if problem.is_goal_state(n.state):
                    return Solution(problem, roots, solution_node=n)
                self.fringe.push(n) 
            expand_counter = 0 
            cut = False
            while self.fringe:
                n = self.fringe.pop()
                if n.depth == limit:
                    cut = True
                else:
                    expand_counter += 1
                    n.expanded_order = expand_counter
                    n.location = Node.Location.EXPANDED
                    for s, a, c in sorted(problem.get_successors(n.state), key=lambda x: x[0]):
                        ns = Node(s, a, cost=n.cost + c, parent=n)
                        n.add_successor(ns)
                        if problem.is_goal_state(ns.state):
                            return Solution(problem, roots, solution_node = ns)
                        self.fringe.push(ns)
            return Solution(problem=problem,root_nodes=roots,solution_node=None,cutoff=cut)

        limit = 0
        while True:
            result = dls(self.problem, limit)
            if not result.has_been_cutoff():
                return result
            limit += 1