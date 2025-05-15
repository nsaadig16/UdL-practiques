
import acm.program.CommandLineProgram;

public class Problema1 extends CommandLineProgram{


    public void run(){

        test();
        
    }

    public boolean isPerfect(int n){

        int divisors = 0;

        for (int i = 1; i < n ; i++){

            if(n % i == 0){

                divisors += i;

            }
        }

        return(n == divisors);
    }

    public void test (){

        int[] nums = new int[] {6,496, 8128, 37, 88, 123};

        for(int i = 0; i < nums.length; i++){

            if(isPerfect(nums[i])){

                if(i<3){
                    println("OK");
                }else{
                    println("NOT OK");
                }
            }else{

                if(i<3){
                    println("NOT OK");
                }else{
                    println("OK");
                }
            }
        }
    }
    public static void main(String[] args){
        new Problema1().start(args);
    }
}