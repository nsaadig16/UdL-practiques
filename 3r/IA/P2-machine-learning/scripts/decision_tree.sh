#!/bin/bash

mkdir -p out
rm -f out/decision_tree.csv
output="out/decision_tree.csv"

case $# in
0)
    iterations=100
;;
1)
    iterations=$1
;;
*)
    echo "Too many arguments"
    exit 1
;;
esac

echo "Iterations:${iterations}"
echo "Starting..."
echo "iteration,scoref,beta,prune_threshold,score" >> ${output}
scorefs=(gini entropy)
# Broader ranges to observe differences
betas=(0 0.01 0.05 0.3 0.5)
prune_thresholds=(0 0.05 0.1 0.2 0.5 1 2 5)

for i in $(seq 1 ${iterations})
do
    seed=$RANDOM
    echo "$i/${iterations}"
    for scoref in "${scorefs[@]}"
    do
        for beta in "${betas[@]}"
        do
            for prune_threshold in "${prune_thresholds[@]}"
            do
                echo -e "\tScoref: ${scoref}, Beta: ${beta}, Prune threshold: ${prune_threshold}"
                echo -n "$i,${scoref},${beta},${prune_threshold}," >> ${output}
                uv run decision_tree.py datasets/supervised/iris.csv --seed ${seed} --scoref ${scoref} --beta ${beta} --prune-threshold ${prune_threshold} --quiet >> ${output}
            done
        done
    done
done
echo "Finished!"