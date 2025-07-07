from collections import defaultdict
import networkx as nx
import matplotlib.pyplot as plt
import copy, re, sys

class Automaton:
    class State:
        """
        Class representing a state in the automaton.
        """
        def __init__(self, num : str):
            self.name = num

        def __eq__(self, other):
            if isinstance(other,Automaton.State):
                return self.name == other.name
            return False
        
        def __hash__(self):
            return hash(self.name)
        
        def __str__(self):
            return self.name

        def __repr__(self):
            return str(self)

    class Symbol:
        """
        Class representing a symbol in the automaton.
        """
        def __init__(self, symbol : str ):
            self.symbol = symbol
        
        def __eq__(self, other):
            if isinstance(other,Automaton.Symbol):
                return self.symbol == other.symbol
            return False
        
        def __hash__(self):
            return hash(self.symbol)
        
        def __str__(self):
            return self.symbol
        
        def __repr__(self):
            return str(self)


    def __init__(self, file : str):
        try:
            pattern = r'\w+'
            states_symbol = re.compile(pattern)
            trans_regex = re.compile(r'\('+ pattern + r',' + pattern + r'\)' + r'->' + pattern)

            # Read from file
            with open(file,'r') as f:
                lines = f.readlines()
                f.close()
            
            if len(lines) < 5:
                raise ValueError("File must contain at least 5 lines.")

            # Define the states
            states = [self.State(s) for s in lines[0].split()]
            for state in states:
                if not states_symbol.fullmatch(state.name):
                    raise ValueError(f"Invalid state name: {state}")

            # Define the alphabet
            alphabet = [self.Symbol(s) for s in lines[1].split()]

            for symbol in alphabet:
                if not states_symbol.fullmatch(symbol.symbol):
                    raise ValueError(f"Invalid symbol name: {symbol}")
                

            relations = lines[2].split()
            # Check if the transition function is valid
            if len(relations) != len(states) * len(alphabet):
                raise ValueError("Not deterministic: not every transition.")
            for trans in relations:
                if not trans_regex.fullmatch(trans):
                     raise ValueError(f"Invalid transition: {trans}")

            trans_func = {}
            # Define the transition function using a dictionary
            for a in relations:
                keys,val = a.split('->')
                if self.State(val) not in states:
                    raise ValueError(f"Transition destination {val} not in states.")

                key1, key2 = keys.strip('()').split(',')
                if self.State(key1) not in states:
                    raise ValueError(f"Transition source {key1} not in states.")
                if self.Symbol(key2) not in alphabet:
                    raise ValueError(f"Transition symbol {key2} not in alphabet.")
                
                trans_func[(self.State(key1),self.Symbol(key2))] = self.State(val)
            

            # Check if the initial state is valid
            if len(lines[3].strip().split()) != 1:
                raise ValueError("Not deterministic: more than one initial state.")
            if not states_symbol.fullmatch(lines[3].strip()):
                raise ValueError(f"Invalid initial state name: {lines[3].strip()}")
            
            # Define the initial state     
            initial_state = self.State(lines[3].strip())

            # Check if the initial state is in the states
            if initial_state not in states:
                raise ValueError(f"Initial state {initial_state} not in states.")
            
            final_states = [self.State(s) for s in lines[4].split()]
            # Check if the final states are valid
            for state in final_states:
                if not states_symbol.fullmatch(state.name):
                    raise ValueError(f"Invalid final state name: {state}")

            # Check if the final states are in the states   
            if not all(s in states for s in final_states):
                raise ValueError(f"Final states {[s for s in final_states if s not in states]} not in states.")

            # Create the automaton
            self.states = states
            self.alphabet = alphabet
            self.transition_function = trans_func
            self.initial_state = initial_state
            self.final_states = final_states

        except ValueError as e:
            print(f"\033[31mError creating automaton:\033[0m {e}")
            sys.exit(1)
        except FileNotFoundError:
            print(f"\033[31mFile {file} not found.\033[0m")
            sys.exit(1)


    def __str__(self):
        autom_str = ""
        # States
        state_list = [s.name for s in self.states]
        state_list.sort()
        states = ' '.join(state_list)
        autom_str += states + '\n'

        # Alphabet
        symbol_list = [s.symbol for s in self.alphabet]
        symbol_list.sort()
        alphabet = ' '.join(symbol_list)
        autom_str += alphabet + '\n'
        
        # Transition function
        applications_str = ""
        applications = [(st.name,sy.symbol,self.transition_function[(st,sy)].name) for st,sy in self.transition_function]
        for i in applications:
            applications_str += f'({i[0]},{i[1]})->{i[2]} '
        applications_str = applications_str.strip()
        autom_str += applications_str + '\n'

        # Initial state
        autom_str += str(self.initial_state.name) + '\n'

        # Final states
        final_state_list = [s.name for s in self.final_states]
        final_state_list.sort()
        final_states = ' '.join(final_state_list)
        autom_str += final_states
        return autom_str

    def save(self, file : str):
        # Save the automaton to a file
        with open(file,'w') as f:
            f.write(str(self))

    def apply_transition(self, state : State, symbol : Symbol) -> State:
        # Returns the state reached by applying the transition function to the given state and symbol
        return self.transition_function.get((state,symbol))
    
    def get_transitions(self, state : State) -> list:
        # Returns the list of states reached by applying all transitions from the given state
        transitions = []
        for sy in self.alphabet:
            transitions.append(self.apply_transition(state,sy))
        return transitions
    
    def get_source_transitions(self, st : State) -> list:
        # Returns the list of transitions that lead to the given state
        transitions = []
        for s in self.states:
            for sy in self.alphabet:
                if self.apply_transition(s,sy) == st:
                    transitions.append((s,sy))
        return transitions

    def minimize(self):
        # Minimize the automaton using the partition refinement algorithm
        sets = []
        new_sets = []
        finals = [s for s in self.states if s in self.final_states]
        not_finals = [s for s in self.states if s not in self.final_states]
        # Add the initial state to the sets
        new_sets.append(finals) ; new_sets.append(not_finals)
        
        # Create the sets
        while sets != new_sets:
            sets = new_sets.copy()
            new_sets = []
            belong = {}
            for i, s in enumerate(sets):
                for st in s:
                    belong[st] = i
                
            for s in sets:
                belong_set = {}
                for st in s:
                    aux = self.get_transitions(st)
                    for i in range(len(aux)):
                        if aux[i] != None:
                            aux[i] = belong[aux[i]]
                    belong_set[st] = aux

                grouped = self.group_sets(belong_set)
                for i in grouped:
                    new_sets.append(i)

        removed = [self.State(f'{s}') for s in self.states if s not in [st[0] for st in new_sets]]

        # Reorder the transition function
        for l in new_sets:
            if len(l) > 1:
                for i in l[1:]:
                    src_trans = self.get_source_transitions(i)
                    for t in src_trans:
                        self.transition_function[t] = l[0]

        keys_to_remove = []
        for t in self.transition_function:
            if t[0] in removed:
                keys_to_remove.append(t)
        for k in keys_to_remove:
            self.transition_function.pop(k)

        # Set the new final states
        self.states = [s[0] for s in new_sets]
        new_final_states = []
        for s in new_sets:
            for st in s:
                if st in self.final_states and s[0] not in new_final_states:
                    new_final_states.append(s[0])
        self.final_states = new_final_states

        # Set the new initial state
        for s in new_sets:
            for st in s:
                if st == self.initial_state:
                    self.initial_state = s[0]
                    break

    def group_sets(self,belong_set):
        # Group the states based on their transitions
        grouped = defaultdict(list)
        for st, belongings in belong_set.items():
            grouped[tuple(belongings)].append(st) 
        print(list(grouped.values()))
        return list(grouped.values())
    
    def draw(self, title = "Automaton Visualization"):
        # Create a directed graph
        G = nx.DiGraph()

        # Add nodes for states
        for state in self.states:
            G.add_node(state.name, shape='circle')

        # Add edges for transitions
        for (src, symbol), dest in self.transition_function.items():
            G.add_edge(src.name, dest.name, label=symbol.symbol)

        # Highlight initial and final states
        node_colors = []
        for node in G.nodes:
            if node == self.initial_state.name:
                node_colors.append('green')  # Initial state
            elif any(node == fs.name for fs in self.final_states):
                node_colors.append('red')  # Final state
            else:
                node_colors.append('lightblue')  # Regular state

        # Draw the graph
        pos = nx.spring_layout(G)  # Layout for the graph
        nx.draw(G, pos, with_labels=True, node_color=node_colors, node_size=1500, font_size=10, font_weight='bold')
        edge_labels = nx.get_edge_attributes(G, 'label')
        nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_size=8)

        # Set the window title
        fig = plt.gcf()  # Get the current figure
        fig.canvas.manager.set_window_title(title)
        
        # Show the plot
        plt.title(title)
        plt.show()

    def copy(self):
        return copy.deepcopy(self)