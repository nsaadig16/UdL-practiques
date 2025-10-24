import acm.program.*;

import java.io.*;

public class Registres extends ConsoleProgram {

    private int num;
    private static final String INPUT_NAME = "registres_input.txt";
    private static final String OUTPUT_NAME = "registres_output.txt";

    public void run(){
        try {
            BufferedReader input = new BufferedReader(new FileReader(INPUT_NAME));
            BufferedWriter output = new BufferedWriter(new FileWriter(OUTPUT_NAME,false));
            int num = readInt("Fins quin registre vols?\n");
            for(int i = 0; i <= num; i++){
                output.write("PUSH R" + i);
                output.newLine();
            }
            output.newLine();
            String line = input.readLine();
            while(line != null){
                output.write(line);
                output.newLine();
                line = input.readLine();
            }
            output.newLine();
            for(int i = num; i >= 0; i--){
                output.write("POP R" + i);
                output.newLine();
            }
            input.close();
            output.close();
            println("Finished");
        } catch (IOException e) {
            println("ERROR");
        }
    }


    public static void main(String[] args) {
        new Registres().start(args);
    }
}
