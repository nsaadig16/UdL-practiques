package reader_i_writer;

import acm.program.CommandLineProgram;

import java.io.*;

public class Llegir_Buffered extends CommandLineProgram {

    private static final String INPUT_NAME = "llegir.txt";
    private static final String OUTPUT_NAME = "escriure.txt";

    public void run (){
        try {
            BufferedReader input = new BufferedReader(new FileReader(INPUT_NAME));
            BufferedWriter output = new BufferedWriter(new FileWriter(OUTPUT_NAME,false));
            String line = input.readLine();
            while(line != null){
                output.write(line);
                output.newLine();
                line = input.readLine();
            }
            input.close();
            output.close();
        } catch (IOException e) {
            println("Error :(");
        }
    }

    public static void main(String[] args) {
        new Llegir_Buffered().start(args);
    }
}
