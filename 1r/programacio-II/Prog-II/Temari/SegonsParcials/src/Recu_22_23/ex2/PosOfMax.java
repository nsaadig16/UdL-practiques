package Recu_22_23.ex2;

import acm.program.CommandLineProgram;

public class PosOfMax extends CommandLineProgram {

    // L'ha fet ChatGPT, sorry, us he decebut :C

    private final int[] vect = {3,7,1,5,7,3,8,9,0};

    public void run(){
        println(posOfMax(vect));
    }

    public int posOfMax(int[] v){
        return posOfMax(v,v.length - 1);
    }

    public int posOfMax(int[] v, int pos){
        if(pos == 0) {
            return 0;
        }
        int maxIndex = posOfMax(v,pos - 1);
        if(v[pos] > v[maxIndex]){
            return pos;
        }else{
            return maxIndex;
        }
    }


    public static void main(String[] args) {
        new PosOfMax().start(args);
    }
}
