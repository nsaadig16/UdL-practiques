package Parcial22_23;
import acm.program.*;

import java.util.StringTokenizer;

public class WordStatsProgram extends CommandLineProgram {
    private static final int INITIAL_SIZE = 28;
    private WordStats stats;
    public void run() {
        this.stats = new WordStats(INITIAL_SIZE);
        countWords();
    }

    private void countWords(){
        String sentence = readLine("Enter a sentence: ");
        if(sentence.length() == 0){
            println("The message is empty.");
        }else{
            StringTokenizer words = new StringTokenizer(sentence," ,.");
            while(words.hasMoreTokens()){
                String word = words.nextToken();
                stats.countWord(word);
            }
            println("The word that appears the most is " + stats.max() + ".");
        }
    }

    //These methods DON'T have to be implemented.

    public static void main(String[] args) {
        new WordStatsProgram().start(args);
    }
}
