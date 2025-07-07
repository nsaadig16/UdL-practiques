import acm.program.*;
public class Problema6 extends DialogProgram{
    public void run(){

        String str = readLine("Write something and I'll remove the extremes :");
        String result = removeExtremes(str);
        println("|" + str + "|");
        println("|" + result + "|");

    }

    public String removeExtremes ( String str){

        char[] chars = str.toCharArray();
        if(chars.length < 2){
            return "The String either is empty or only has one character.";
        }
        return new String (chars, 1, chars.length - 2);
    }
    public static void main(String[] args){
        new Problema6().start(args);
    }
}
