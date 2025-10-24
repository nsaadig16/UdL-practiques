import acm.program.*;

import java.io.*;

public class Codi_ASCII extends ConsoleProgram{

    private static final String INPUT_FILE = "ascii_input.txt";
    private static final String OUTPUT_FILE = "ascii_output.txt";
    private String highByte;
    private final String[] colors = {"000","001","010","011","100","101","110","111"};
    @Override
    public void run() {
        askColors();
        try {
            BufferedReader input = new BufferedReader(new FileReader(INPUT_FILE));
            BufferedWriter output = new BufferedWriter(new FileWriter(OUTPUT_FILE,false));
            String line = input.readLine();
            while (line != null){
                output.write(ascii(line));
                output.newLine();
                line = input.readLine();
            }
            input.close();
            output.close();
            println("Tot correcte");
        } catch (IOException e) {
            println("ERROR");
        }
    }

    private void askColors(){
        String colorTable = "0: Negre\n" +
                "1: Blau\n" +
                "2: Verd\n" +
                "3: Groc\n" +
                "4: Roig\n" +
                "5: Cià\n" +
                "6: Gris\n" +
                "7: Blanc\n";
        print(colorTable);
        int background = readInt("Color del fons: ");
        int text = readInt("Color del text: ");
        String result =  colors[background] + colors[text];
        int res = Integer.parseInt(result,2);
        this.highByte = res < 16 ? "0" + Integer.toHexString(res) : Integer.toHexString(res);
    }

    private String ascii(String line){
        String result = "";
        for(int i = 0; i < line.length(); i++){
            result += transform(line.charAt(i));
            if( i != line.length() -1){
                result += ", ";
            }
        }
        return result;
    }

    private String transform (int c){
        String hexCode = Integer.toHexString(c).toUpperCase();
        return this.highByte + hexCode + "h";
    }

    public static void main(String[] args) {
        new Codi_ASCII().start(args);
    }
}
