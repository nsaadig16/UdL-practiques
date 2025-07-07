import acm.program.*;
import java.util.StringTokenizer;
public class Problema5 extends ConsoleProgram{

    String[] IGNORE = new String[] {"el","la","un","unos","y","o","de"};

    public void run(){
        String line = readLine("Write something and I'll show you its acronym:");
        String acronym = makeAcronym(line);
        println("\"" + line + "\"'s" + " acronym is " + "\"" + acronym + "\"");
    }

    public String makeAcronym(String line){
        StringTokenizer txt = new StringTokenizer(line);
        String acronym = "";
        while(txt.hasMoreTokens()){
            String word = txt.nextToken();
            if(!ignoreWord(word)){
                String first = word.substring(0,1);
                acronym += first.toUpperCase() + ".";
            }
        }
        return acronym;
    }

    public boolean ignoreWord(String word){
        for(int i = 0; i < IGNORE.length; i++){
            if(word.equals(IGNORE[i])){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        new Problema5().start(args);
    }
}
