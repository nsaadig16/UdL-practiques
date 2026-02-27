#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/.." || {
    echo "Failed to change directory to parent of $SCRIPT_DIR"
    exit 1
}
trap "echo 'Aborted'; exit 130" SIGINT
if [[ $# -eq 1 ]]
then
    case $1 in
        -c|clean)
            echo "Cleaning log folders..."
            rm -rf out/kiwis_dogs
            echo "(1/3)..."
            rm -rf out/nqueens
            echo "(2/3)..."
            rm -rf out/pacman
            echo "(3/3)..."
            echo "Successfully cleaned"
            exit 0
        ;;
        pacman)
            just_pac=1
        ;;
        *)
            echo -e "\033[31mUsage: $0 <clean|pacman>\033[0m"
            exit 64

    esac
fi
function showerror(){
    if [[ ! $? -eq 0 ]]
    then
        echo -e "\033[31mError: Fail with code $?\033[0m"
    fi
}

if [[ ! $just_pac ]]
then
    echo "Logs generation started"
    echo -e "\033[35mRunning kiwisdogs.sh\033[0m"
    ./scripts/kiwisdogs.sh
    showerror
    echo -e "\033[35mRunning nqueens.sh\033[0m"
    ./scripts/nqueens.sh
    showerror
fi
echo -e "\033[35mRunning pacman.sh (hlog-graph-bfs)\033[0m"
./scripts/pacman.sh hlog graph bfs 
showerror
echo -e "\033[35mRunning pacman.sh (hlog-graph-ucs)\033[0m"
./scripts/pacman.sh hlog graph ucs 
showerror
echo -e "\033[35mRunning pacman.sh (hlog-graph-astar-mahattan)\033[0m"
./scripts/pacman.sh hlog graph astar Manhattan 
showerror
echo -e "\033[35mRunning pacman.sh (hlog-graph-astar-euclidean)\033[0m"
./scripts/pacman.sh hlog graph astar Euclidean 
showerror
echo -e "\033[35mRunning pacman.sh (my-graph-astar-manhattan)\033[0m"
./scripts/pacman.sh my graph astar Manhattan 
showerror
echo -e "\033[35mRunning pacman.sh (my-graph-astar-euclidean)\033[0m"
./scripts/pacman.sh my graph astar Euclidean 
showerror
echo -e "\033[35mRunning pacman.sh (my-graph-ids)\033[0m"
./scripts/pacman.sh my graph ids 
echo -e "\033[35mRunning pacman.sh (hlog-tree-bfs)\033[0m"
./scripts/pacman.sh hlog tree bfs 
showerror
echo -e "\033[35mRunning pacman.sh (hlog-tree-ucs)\033[0m"
./scripts/pacman.sh hlog tree ucs 
showerror
echo -e "\033[35mRunning pacman.sh (hlog-tree-astar-mahattan)\033[0m"
./scripts/pacman.sh hlog tree astar Manhattan 
showerror
echo -e "\033[35mRunning pacman.sh (hlog-tree-astar-euclidean)\033[0m"
./scripts/pacman.sh hlog tree astar Euclidean 
showerror
echo -e "\033[35mRunning pacman.sh (my-tree-astar-manhattan)\033[0m"
./scripts/pacman.sh my tree astar Manhattan 
showerror
echo -e "\033[35mRunning pacman.sh (my-tree-astar-euclidean)\033[0m"
./scripts/pacman.sh my tree astar Euclidean 
showerror
echo -e "\033[35mRunning pacman.sh (my-tree-ids)\033[0m"
./scripts/pacman.sh my tree ids 
showerror
echo "Finished log generation"
