#!/bin/bash

FOLDER="tests"
mkdir -p "$FOLDER"


for i in $(seq 1 20); do
    python3 rnd-cnf-gen.py 20 60 3 $i > "$FOLDER/easy_$(printf '%02d' $i).cnf"
    echo "  easy_$(printf '%02d' $i).cnf  (20 vars, 60 clausulas)"
done

for i in $(seq 1 15); do
    seed=$((i + 100))
    python3 rnd-cnf-gen.py 50 214 3 $seed > "$FOLDER/hard_$(printf '%02d' $i).cnf"
    echo "  hard_$(printf '%02d' $i).cnf  (50 vars, 214 clausulas)"
done