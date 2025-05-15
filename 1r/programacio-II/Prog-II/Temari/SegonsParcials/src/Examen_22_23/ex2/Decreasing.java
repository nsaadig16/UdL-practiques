package Examen_22_23.ex2;

import acm.program.CommandLineProgram;

public class Decreasing extends CommandLineProgram {

    public void run(){
        int[] decreasingVector = {9,8,7,6,5,4,3,2,1};
        int[] notDecreasingVector = {5,7,1,9,2,5};
        println(isDecreasing(decreasingVector));
        println(isDecreasing(notDecreasingVector));
    }

    private boolean isDecreasing(int[] vector){
        return isDecreasing(vector,vector.length);
    }

    private boolean isDecreasing(int[] vector, int pos){
        if(pos == 0){
            return true;
        }
        if( pos == 1){
            return vector[pos] <= vector[pos - 1] && isDecreasing(vector, 0);
        }
        return vector[pos - 1] <= vector[pos - 2] && isDecreasing(vector,pos-1);
    }

    public static void main(String[] args) {
        new Decreasing().start(args);
    }
}
