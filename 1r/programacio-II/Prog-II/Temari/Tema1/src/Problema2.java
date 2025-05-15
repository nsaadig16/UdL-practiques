import acm.program.CommandLineProgram;

public class Problema2 extends CommandLineProgram{

    public void run() {
        int[] nums = new int[]{4, 12, -5, 3};
        int max = max(nums);
        println( "El resultado es " + max);
    }

    public int max (int[] nums){

        int max = nums[0];

        for(int i = 1; i < nums.length ; i+= 1){

            if(nums[i] > max){

                max = nums[i];

            }
        }

        return max;
    }
    public static void main(String[] args){
        new Problema2().start(args);
    }
}
