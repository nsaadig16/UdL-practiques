#!/bin/bash

mkdir -p out
rm -f out/bayesian.csv
output="out/bayesian.csv"

case $# in
0)
    iterations=1000
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
echo "iteration,Assumed probability,score" >> ${output}
assumed=(0.25 0.5 0.75 1)

for i in $(seq 1 ${iterations})
do
    seed=$RANDOM
    echo "$i/${iterations}"
    for assumedprob in "${assumed[@]}"
    do
        echo -e "\tAssumed probability: ${assumedprob}"
        echo -n "$i,${assumedprob}," >> ${output}
        uv run python3 bayesian.py datasets/supervised/SMSSpamCollection.txt --seed ${seed} --assumed_probability ${assumedprob} --quiet >> ${output}
    done
done
echo "Finished!"