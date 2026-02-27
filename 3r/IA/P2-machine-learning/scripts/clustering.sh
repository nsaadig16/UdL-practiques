#!/bin/bash

mkdir -p out
rm -f out/clustering.csv
output="out/clustering.csv"

case $# in
0)
    max_k=25
    n_restarts=100
;;
1)
    max_k=$1
    n_restarts=100
;;
2)
    max_k=$1
    n_restarts=$2
;;
*)
    echo "Too many arguments"
    exit 1
;;
esac

seed=$RANDOM

echo "k:${max_k}, n_restarts:${n_restarts}"
echo "Starting..."
echo "k,distance">>${output}
for k in $(seq 1 ${max_k})
do
    echo "$k/${max_k}"
    distance=$(uv run clustering.py --k $k --distance squared-euclidean --n-restarts ${n_restarts} --seed ${seed} --quiet datasets/unsupervised/blogdata.csv)
    echo "${k},${distance}" >> ${output}
done
echo "Finished!"