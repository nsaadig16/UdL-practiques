package Problema3;
import acm.program.CommandLineProgram;
import java.io.*;

public class Cypher extends CommandLineProgram {
    private static final int LETTERS = 26;
    private static final String INPUT_NAME = "plaintext.txt";
    private static final String OUTPUT_NAME = "ciphertext.txt";

    public void run(){
        try {
            FileReader input = new FileReader(INPUT_NAME);
            FileWriter output = new FileWriter(OUTPUT_NAME,false);
            int key = realDistance(readInt("Enter a key: "));
            int c = input.read();
            while (c != -1){
                output.write(encodedChar((char)c,key));
                c = input.read();
            }
            input.close();
            output.close();
            println("The text has been encoded successfully.");
        } catch (IOException e) {
            println("There has been an error ;(");
        }
    }
    private int realDistance(int distance){
        int realDistance = distance % LETTERS;
        if(realDistance < 0){
            realDistance += LETTERS;
        }
        return realDistance;
    }
    private char encodedChar(char ch, int distance){
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = uppercase.toLowerCase();
        String alphabet = Character.isUpperCase(ch) ? uppercase : lowercase;
        int position = (alphabet.indexOf(ch) + distance) % LETTERS;
        return alphabet.charAt(position);
    }
    public static void main(String[] args){
        new Cypher().start(args);
}
}
