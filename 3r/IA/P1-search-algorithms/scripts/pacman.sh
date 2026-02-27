#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/.." || {
    echo "Failed to change directory to parent of $SCRIPT_DIR"
    exit 1
}
trap "echo 'Aborted'; exit 130" SIGINT
source ../.venv/bin/activate
if [[ $# -lt 3 || $# -gt 4 ]]
then
    echo "Usage: $0 [my|hlog] [graph|tree] [algorithm] <heuristic>"
    exit 64
fi
who=$1
version=$2
algorithm=$3
if [[ ${algorithm} == "astar" ]]
then
    if [[ $# -lt 4 ]]
    then
        echo "Error: Missing heuristic on A* algorithm"
        exit 1
    else
        heuristic="-${4}"
        hf="-hf ${4}"
    fi
else
    heuristic=""
    hf=""
fi
echo "$0: ${who}-${algorithm}${heuristic}"
directory="out/pacman/${who}-${version}-${algorithm}${heuristic}"
rm -rf ${directory}
mkdir -p ${directory}
run() {
    for path in "$1"/*; do
        if [[ -f "$path" ]]; then
                echo "Doing ${who}-${version}-${algorithm}${heuristic} on ${path##*/}"
                output="${directory}/${who}-${version}-${algorithm}_${path##*/}.log"
                timeout --foreground 500 hlogedu-search run -a "${who}"-${version}-${algorithm} -p Pacman -pp file=${path} -o none ${hf} 2> >(grep -E '^(Max|Solution|Search|Error)' >> ${output})
                status=$?
                if [[ ${status} -eq 1 ]]
                then
                    echo -e "\033[31m Error: Command failed on ${who}-${version}-${algorithm} \033[0m"
                    exit 1
                elif [[ ${status} -eq 124 ]]
                then
                    echo "Error: Search not completed in time limit" >> ${output}
                fi
                echo >> ${output}
        elif [[ -d "$path" ]]; then
        run "$path"
        fi
    done
}

run "problems/layouts"

exit 0