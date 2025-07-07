import acm.program.*;

public class Problema7 extends DialogProgram {

    public void run(){
        String str = readLine("Write something:");
        String result = removeSpaces(str);
        println(result);
    }

    public String removeSpaces (String str){

        char[] chars = str.toCharArray();
        char[] result = new char[chars.length];
        int spaces = 0;

        for(int i = 0; i < chars.length; i++){

            if(chars[i] == ' '){
                spaces += 1;
            }else{
                result[i - spaces] = chars[i];
            }
        }
        return new String(result);
    }
    public static void main(String[] args){
        new Problema7().start(args);
    }
}
