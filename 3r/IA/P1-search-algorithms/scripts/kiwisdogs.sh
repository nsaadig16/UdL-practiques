#!/bin/bash
echo "$0"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/.." || {
    echo "Failed to change directory to parent of $SCRIPT_DIR"
    exit 1
}
source ../.venv/bin/activate

algorithms=("bfs" "ucs" "dfs")

mkdir -p out
mkdir -p out/kiwis_dogs
output="out/kiwis_dogs/kiwis_dogs.log"
: > ${output}
for a in "${algorithms[@]}"
do
    echo -e "\033[35mgraph ${a}\033[0m"
    echo "graph ${a}" >> ${output}
    timeout 500 hlogedu-search run -a hlog-graph-"${a}" -p KiwisDogs -o none 2> >(grep -E '^(Max|Solution|Search|graph|tree|Error)' >> ${output})
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
