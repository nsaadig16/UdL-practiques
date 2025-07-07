package Examen_20_21.ex2;

import acm.program.CommandLineProgram;

public class SumOfEven extends CommandLineProgram {

    public void run(){
        int[] vector = {2,4,4,8};
        println(sumOfPairs(vector) == 18);
    }

    private int sumOfPairs(int[] vector){
        return sumOfPairs(vector,vector.length);
    }

    private int sumOfPairs(int[] vector, int pos){
        if( pos == 1 ){
            if(vector[pos - 1] % 2 == 0){
                return vector[pos - 1];
            }
            return 0;
        }
        if(vector[pos - 1] % 2 == 0){
            return vector[pos - 1] + sumOfPairs(vector,pos - 1);
        }
        return sumOfPairs(vector,pos - 1);
    }

    public static void main(String[] args) {
        new SumOfEven().start(args);
    }
}
