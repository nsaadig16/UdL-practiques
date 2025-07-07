import acm.program.*;
import java.util.StringTokenizer;

public class Problema4 extends ConsoleProgram{

    String DELIMITERS = " .,:;0123456789";

    public void run(){
        String line = readLine("Write something:");
        String longest = longestWord(line);
        println("The longest word in \"" + line + "\" is \"" + longest + "\"" );
    }

    public String longestWord(String line){
        StringTokenizer txt = new StringTokenizer(line,DELIMITERS);
        String longest = "";
        while(txt.hasMoreTokens()){
            String word = txt.nextToken();
            if(longest.length() < word.length()){ // Si hi ha més paraules de la mateixa llargada, es queda amb la primera.
                longest = word;
            }
        }
        return longest;
    }
    public static void main(String[] args){
        new Problema4().start(args);
    }
}
