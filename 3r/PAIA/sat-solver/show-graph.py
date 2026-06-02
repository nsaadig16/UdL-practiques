import networkx as nx
import matplotlib.pyplot as plt
import sys
from argparse import ArgumentParser
from pathlib import Path

COLORS = ["#FF0000", "#0000FF", "#00FF00", "#FFF200", "#9B59B6", "#1ABC9C"]

def read_cnf_file(path):
    clauses = []
    num_vars = 0
    with open(Path(path)) as f:
        lines = f.readlines()
        if not lines:
            print("Error! Instance file is emtpy!", file=sys.stderr)
            exit(1)

        for line in lines:
            line = line.strip()
            if line.startswith("c") or not line:
                continue
            elif line.startswith("p"):
                num_vars = int(line.split()[2])
            else:
                clauses.append(list(map(int, line.split()[:-1])))
    return num_vars, clauses

def create_graph(num_vars, clauses, solution_file, output_file="out.png"):
    k = next(len(c) for c in clauses if all(l > 0 for l in c))
    num_nodes = num_vars // k

    edges = set()
    for c in clauses:
        if len(c) == 2 and c[0] < 0 and c[1] < 0:
            v1, v2 = abs(c[0]) - 1, abs(c[1]) - 1
            n1, col1 = v1 // k, v1 % k
            n2, col2 = v2 // k, v2 % k
            if n1 != n2 and col1 == col2:
                edges.add((min(n1, n2), max(n1, n2)))

    if solution_file:
        with open(solution_file, 'r') as f:
            solution_lines = f.readlines()
    else:
        if sys.stdin.isatty():
            print("Error! Solution file not piped!", file=sys.stderr)
            exit(1)
        solution_lines = sys.stdin.readlines()
        if not solution_lines:
            print("Error! Solution file is emtpy!", file=sys.stderr)
            exit(1)

    v_line = next((l for l in solution_lines if l.startswith("v ")), None)

    node_color = {}
    for v in map(int, v_line.split()[1:]):
        if v > 0:
            idx = v - 1
            node, color = idx // k, idx % k
            if node not in node_color:
                node_color[node] = color

    # Dibuja el grafo
    G = nx.Graph()
    G.add_nodes_from(range(num_nodes))
    G.add_edges_from(edges)

    colors = [COLORS[node_color.get(n, 0) % len(COLORS)] for n in G.nodes()]
    nx.draw(G, node_color=colors, node_size=500, edge_color="#000000", with_labels=True)
    plt.savefig(output_file)

def main():
    parser = ArgumentParser(description='Graph display given a CNF file and its solution')
    parser.add_argument('cnf_instance', type=str, help="CNF instance with the graph constraints")
    parser.add_argument('--solution_file', type=str, default='', help="Solution file for the given CNF instance")
    parser.add_argument('--output', '-o', type=str, default='out.png', help="Name of the output image")
    args = parser.parse_args()
    num_vars, clauses = read_cnf_file(args.cnf_instance)
    create_graph(num_vars, clauses, args.solution_file, args.output)


if __name__ == "__main__":
    main()