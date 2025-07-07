package Problema1;

import acm.program.*;

import java.io.*;

public class CountingVocals extends ConsoleProgram {

    private final static String FILE_NAME = "input.txt";
    private final static String VOCALS = "aeiou";

    public void run() {
        try {
            CharCounter counters = new CharCounter(VOCALS, false);
            FileReader input = new FileReader(FILE_NAME);
            int c = input.read();
            while ( c != -1 ) {
                 counters.countIfTargeted((char) c);
                 c = input.read();
            }
            input.close();
            println(counters.toString());
        } catch (IOException ex) {
             println("Something bad has happened :-(");
        }
    }

    public static void main(String[] args) {
        new CountingVocals().start(args);
    }
}