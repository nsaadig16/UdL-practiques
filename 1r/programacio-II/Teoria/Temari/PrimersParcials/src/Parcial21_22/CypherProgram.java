package Parcial21_22;
import acm.program.*;

import java.util.StringTokenizer;

public class CypherProgram extends CommandLineProgram {
    private static final int INITIAL_SIZE = 28;
    private Cypher cypher;
    private int numErrors;
    private void initializeResults() {
        this.cypher = new Cypher(INITIAL_SIZE);
        this.numErrors = 0;
    }
    public void run() {
        this.initializeResults();
        this.createCypher();
        this.encode();
    }

    private void createCypher(){
        String key = readLine("Enter the key: ");
        StringTokenizer pairs = new StringTokenizer(key);
        while(pairs.hasMoreTokens()){
            String pair = pairs.nextToken();
            if (!this.cypher.addPair(pair.charAt(0),pair.charAt(2))) {
                this.numErrors += 1;
            }
        }
    }

    private void encode(){
        String message = readLine("Enter the message to encode: ");
        if(this.cypher.encode(message) == null){
            println("The message couldn't be encoded.");
        } else {
            println("Errors: " + this.numErrors);
            println("Cypher: " + this.cypher.encode(message));
        }
    }

    public static void main(String[] args) {
        new CypherProgram().start(args);
    }
}



