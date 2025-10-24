package Parcial22_23;

public class WordCounter {

    private final String word;
    private int counter;

    public WordCounter (String word){
        this.word = word;
        this.counter = 1;
    }

    public String getWord(){
        return this.word;
    }
    public int getCounter(){
        return this.counter;
    }
    public void increment(){
        this.counter += 1;
    }
}
