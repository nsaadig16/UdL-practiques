package reader_i_writer;

import acm.program.CommandLineProgram;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Llegir_i_escriure extends CommandLineProgram {

    private static final String INPUT_NAME = "llegir.txt";
    private static final String OUTPUT_NAME = "escriure.txt";

    public void run (){
        try {
            FileReader input = new FileReader(INPUT_NAME);
            FileWriter output = new FileWriter(OUTPUT_NAME,false);
            int c = input.read();
            while( c != -1){
                if (c != 'a') {
                    output.write((char) c);
                }
                c = input.read();
            }
            input.close();
            output.close();
        } catch (IOException e) {
            println("Error :(");
        }
    }

    public static void main(String[] args) {
        new Llegir_i_escriure().start(args);
    }
}
