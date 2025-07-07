package reader_i_writer;

import acm.program.ConsoleProgram;
import java.io.*;

public class Llegir extends ConsoleProgram {

    private static final String FILE_NAME = "llegir.txt";

    public void run (){
        try {
            FileReader input = new FileReader(FILE_NAME);
            int c = input.read();
            while( c != -1){
                println((char) c);
                c = input.read();
            }
            input.close();
        } catch (IOException e) {
            println("Error :(");
        }
    }

    public static void main(String[] args) {
        new Llegir().start(args);
    }
}
