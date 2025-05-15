package Examen_21_22.ex2;

import acm.program.CommandLineProgram;

public class Balanced extends CommandLineProgram {

    public void run(){
        int[] vector = new int[] {21, 84, 7, 42, 35,-42, 21};
        int factor = 42;
        println(balanced(vector,factor));
    }

    private boolean balanced(int[] vector, int factor){
        return balanced(vector,factor,0,vector.length);
    }

    private boolean balanced(int[] vector, int factor, int begin, int end){
        if(begin == end){
            return true;
        }else if(begin == end - 1){
            return vector[begin] == factor;
        }

        return vector[begin] + vector[end - 1] == factor && balanced(vector,factor,begin + 1, end - 1);
    }

    public static void main(String[] args) {
        new Balanced().start(args);
    }

}
