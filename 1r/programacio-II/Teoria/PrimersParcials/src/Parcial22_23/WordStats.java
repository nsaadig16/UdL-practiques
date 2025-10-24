package Parcial22_23;


public class WordStats {

    private WordCounter[] words;
    private int numWords;

    public WordStats (int initialSize){
        this.words = new WordCounter[initialSize];
        this.numWords = 0;
    }
    private WordCounter getWordCounter(String word){
        for (int i = 0; i < this.numWords; i++){
            if(this.words[i].getWord().equals(word)){
                return this.words[i];
            }
        }
        return null;
    }

    public boolean countWord (String word){
        if(getWordCounter(word) != null){
            getWordCounter(word).increment();
            return false;
        }
        if(this.numWords == this.words.length){
            widenWords();
        }
        this.words[this.numWords] = new WordCounter(word);
        numWords += 1;
        return true;
    }

    public int getCounter(String word){
        if(getWordCounter(word) != null){
            return getWordCounter(word).getCounter();
        }
        return 0;
    }

    public String max(){
        if(this.numWords == 0){
            return null;
        }
        WordCounter max = this.words[0];
        for (int i = 1; i < this.numWords; i++){
            if(this.words[i].getCounter() > max.getCounter()){
                max = this.words[i];
            }
        }
        return max.getWord();
    }


    private void widenWords(){
        WordCounter[] widened = new WordCounter[this.words.length * 2];
        for (int i = 0; i < this.words.length; i++){
            widened[i] = this.words[i];
        }
        this.words = widened;
    }


}
