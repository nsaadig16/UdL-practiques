package Problema1;

public class CharCounter {

    private int counter;
    private final String target;
    private final boolean checkCase;

    public CharCounter (String target, boolean checkCase){
        this.counter = 0;
        this.target = target;
        this.checkCase = checkCase;
    }
    public void countIfTargeted(char c){
        if(targeted(c)){
            counter += 1;
        }
    }

    private boolean targeted(char c){
        if(!checkCase) {
            char lowerC = Character.toLowerCase(c);
            String lowerTarget = target.toLowerCase();
            return found(lowerC, lowerTarget);
        }
        return found(c,target);
    }

    private boolean found(char c, String target){
        for(int i = 0; i < target.length(); i++){
            if(target.charAt(i) == c){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        return String.valueOf(this.counter);
    }




}
