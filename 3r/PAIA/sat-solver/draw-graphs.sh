#!/bin/bash

uv sync

FOLDER="graphs"
PYTHON=".venv/bin/python3"
SOLVER="sat-solver.py"
DISPLAYER="show-graph.py"
OUTPUT_FOLDER="imgs"
mkdir -p $OUTPUT_FOLDER
for i in {1..4}; do
    echo -e "\033[33mDrawing graph $i...\033[0m"
    INSTANCE="$FOLDER/instance_${i}.cnf"
    $PYTHON $SOLVER $INSTANCE | $PYTHON $DISPLAYER $INSTANCE --output "${OUTPUT_FOLDER}/graph${i}.png"
done