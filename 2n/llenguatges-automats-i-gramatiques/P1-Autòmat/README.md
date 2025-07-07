# Pràctica 1 de Llenguatges, Autòmats i Gramàtiques

## Installation

To install the dependencies needed for this program, run the following commands:

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Usage

Executing the program using `python3 -m minimizer [filename]` will print the minimized automaton in the terminal.

You can use these flags if you prefer the input to be displayed in a different way:

- `-s`: draws the input automaton in a graph by using `networkx` and `matplotlib`.
- `-d`: draws the minimized automaton.
- `-o [OUTPUT]`: writes the minimized output to the file specified, creating it if it doesn't exist.

## Defining an automaton

An automaton consists of five elements: the **states**, the **alphabet**, the **transition function**, the **initial state** and the **final states**.

You can define an automaton for this program using a simple `.txt`, using a line for each one of the elements. An automaton should end up with these lines:

```txt
{states}
{alphabet}
{transition function}
{initial state}
{final states}
```

### States

The states are **strings**, and are separated by simple spaces. So defining the states $Q=\{0,1,2\}$ is done by writing:

```txt
0 1 2
```

As simple as that!

### Alphabet

The *symbols* (elements of the alphabet) are **strings**, and are also separated by simple spaces. So defining the alphabet $\Sigma = \{a,b\}$ is done by writing:

```txt
a b
```

### Transition function

The transition function is define using a **dictionary**, with the key being a tuple formed by a *state* and a *symbol* and the value being another *state*.

The syntax is written in this format: `(State,Symbol)->State`, with each relation being separated using simple spaces.
So defining the following transition function:

<div align="center">

$\delta: Q\times \Sigma \longrightarrow Q\\$
$~~~~~~(0,a)\longmapsto 1\\$
$~~~~~~(0,b)\longmapsto 0\\$
$~~~~~~(1,a)\longmapsto 1\\$
$~~~~~~(1,b)\longmapsto 2\\$
$~~~~~~(2,a)\longmapsto 2\\$
$~~~~~~(2,b)\longmapsto 0\\$

</div>

Is done by writing:

```txt
(0,a)->1 (0,b)->0 (1,a)->1 (1,b)->2 (2,a)->2 (2,b)->0
```

### Initial state

The initial state is an **integer** and it's just defined by writing it on the 4th line. So defining the initial state $q_0=0$ is done by just writing:

```txt
0
```

### Final states

The final states are **integers** and are just a subset of states, separated by simple spaces. So defining the final states $F = \{1,2\}$ is done by writing:

```txt
1 2
```

So the automaton is defined by this text:

```txt
0 1 2
a b
(0,a)->1 (0,b)->0 (1,a)->1 (1,b)->2 (2,a)->2 (2,b)->0
0
1 2
```

---
