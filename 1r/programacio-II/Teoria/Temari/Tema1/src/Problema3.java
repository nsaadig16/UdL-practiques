import acm.program.CommandLineProgram;
public class Problema3 extends CommandLineProgram {

    public void run (){

        char[] chars = new char[]{'a','b','c','d','e'};
        char[] result = removeExtrems(chars);
        println(result);

    }

    public char[] removeExtrems (char[] chars){

        char[] result = new char[chars.length - 2];

        for( int i = 1; i < chars.length -1; i++){

            result[i-1] = chars[i];
        }
        return result;
    }

    //No requerit directament:

    public void println(char[] array){

        print("{");
        for( int i = 0; i < array.length; i++) {

            if (i != 0) {
                print(", " + array[i]);
            }else{
                print(array[i]);
            }
        }

        println("}");
    }
    public static void main(String[] args){
        new Problema3().start(args);
    }
}
