#!/bin/bash
echo "$0"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/.." || {
    echo "Failed to change directory to parent of $SCRIPT_DIR"
    exit 1
}
trap "echo 'Aborted'; exit 130" SIGINT
source ../.venv/bin/activate

algorithms=("ucs" "astar")
version=("graph" "tree")

mkdir -p "out/nqueens/"
output="out/nqueens/nqueens.log"
: > ${output}
seeds=()
for i in {1..5}
do
    seeds+=($RANDOM)
done
for q in {4..7}
do 
    for s in "${seeds[@]}"
    do
        for a in "${algorithms[@]}"
        do 
            for v in "${version[@]}"
            do
                echo -e "\033[35m${v} ${a} (seed ${q} ${s})\033[0m"
                echo "${v} ${a} (seed ${q} ${s})" >> ${output}
                echo >> ${output}
                timeout --foreground 500 hlogedu-search run -a hlog-"${v}"-"${a}" -p NQueensIR -pp n_queens="${q}" -pp seed="${s}" -o none -hf RepairHeuristic 2> >(grep -E '^(Max|Solution|Search|graph|tree|Error)' >> ${output})
                status=$?
                if [[ ${status} -eq 1 ]]
                then
                    echo -e "\033[31m Error: Command failed on hlog-${v}-${a} \033[0m"
                elif [[ ${status} -eq 124 ]]
                then
                    echo "Error: Search not completed in time limit" >> ${output}
                fi
                echo >> ${output}
            done
        done
    done
done
