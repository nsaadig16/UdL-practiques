package Problema3;

import acm.program.CommandLineProgram;

import java.io.*;

public class Cypher2 extends CommandLineProgram {
    private static final int LETTERS = 26;
    private static final String INPUT_NAME = "plaintext.txt";
    private static final String OUTPUT_NAME = "ciphertext.txt";

    public void run(){
        try {
            BufferedReader input = new BufferedReader(new FileReader(INPUT_NAME));
            BufferedWriter output = new BufferedWriter(new FileWriter(OUTPUT_NAME,false));
            int key = realDistance(readInt("Enter a key: "));
            String line = input.readLine();
            while (line != null){
                output.write(encodedLine(line,key));
                output.newLine();
                line = input.readLine();
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

    private String encodedLine (String line, int distance){
        String encoded = "";
        for (int i = 0; i < line.length(); i++){
            encoded += encodedChar(line.charAt(i),distance);
        }
        return encoded;
    }
    private char encodedChar(char ch, int distance){
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = uppercase.toLowerCase();
        String alphabet = Character.isUpperCase(ch) ? uppercase : lowercase;
        int position = (alphabet.indexOf(ch) + distance) % LETTERS;
        return alphabet.charAt(position);
    }
    public static void main(String[] args){
        new Cypher2().start(args);
    }
}
