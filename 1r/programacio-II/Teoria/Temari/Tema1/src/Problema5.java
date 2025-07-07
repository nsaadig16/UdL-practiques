import acm.program.CommandLineProgram;

public class Problema5 extends CommandLineProgram {

    public void run(){

        int[][] left = new int[][] {{2,1,4},{4,3,1},{6,3,9}};
        int[][] right = new int[][] {{4,7,7},{2,5,4},{3,2,1}};
        int[][] result = matrixMultiplication(left, right);

        print(result);


    }

    public int[][] matrixMultiplication (int[][] left, int[][] right){

        int[][] result = new int[left.length][right[0].length];

        for(int i = 0; i < left.length; i++){

            for( int j = 0; j < right[0].length; j++){

                int sum = 0;

                for(int k = 0;k < right.length; k++){

                    sum += left[i][k] * right[k][j];

                }

                result[i][j] = sum;

            }
        }

        return result;
    }

    public void print(int[][] matrix){

        print("{");

        for(int i = 0; i < matrix.length; i++){


            print("{");

            for (int j = 0; j < matrix.length; j++){

                if( j != 0){
                    print(", ");
                }

                print(matrix[i][j]);

            }
            print("}");

            if ( i != matrix.length - 1){

                println(",");
            }
        }
        println("}");

    }
    public static void main(String[] args){
        new Problema5().start(args);
    }
}