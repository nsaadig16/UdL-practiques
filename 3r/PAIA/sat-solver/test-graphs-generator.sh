#!/bin/bash
GENERATOR="rnd-graph-gen.py"
SOLVER="sat-solver.py"
FOLDER="graphs"
TIMEOUT=5

mkdir -p $FOLDER 

for f in "$GENERATOR" "$SOLVER"; do
    [ -f "$f" ] || { echo "ERROR: No se encuentra $f"; exit 1; }
done

POOLS=(
    "5 0.4 3 42|4 0.3 3 42|3 0.5 3 42|3 0.3 2 42"
    "6 0.5 3 7|5 0.4 3 7|4 0.3 3 7|3 0.4 2 7"
    "7 0.3 3 99|5 0.3 3 99|4 0.2 3 99|3 0.3 2 99"
    "6 0.6 4 2024|5 0.5 3 2024|4 0.4 3 2024|3 0.3 2 2024"
)

for i in "${!POOLS[@]}"; do
    IDX=$((i + 1))
    CNF_FILE="${FOLDER}/instance_${IDX}.cnf"
    IFS='|' read -ra ATTEMPTS <<< "${POOLS[$i]}"

    for PARAMS in "${ATTEMPTS[@]}"; do
        read -r NUM_NODES EDGE_PROB NUM_COLORS SEED <<< "$PARAMS"
        echo "[$IDX] nodos=$NUM_NODES prob=$EDGE_PROB colores=$NUM_COLORS seed=$SEED"

        python3 "$GENERATOR" "$NUM_NODES" "$EDGE_PROB" "$NUM_COLORS" "$SEED" > "$CNF_FILE" \
            || { rm -f "$CNF_FILE"; continue; }

        OUTPUT=$(timeout "$TIMEOUT" python3 "$SOLVER" "$CNF_FILE" 2>&1)
        [ $? -eq 124 ] && { echo "  TIMEOUT — reintentando..."; rm -f "$CNF_FILE"; continue; }

        if   echo "$OUTPUT" | grep -q "^s SATISFIABLE";   then STATUS="SATISFIABLE"
        elif echo "$OUTPUT" | grep -q "^s UNSATISFIABLE"; then STATUS="UNSATISFIABLE"
        else STATUS="UNKNOWN"; fi

        break
    done
done
