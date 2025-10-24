import acm.program.ConsoleProgram;

public class Problema3 extends ConsoleProgram {
    String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    int LETTERS = UPPERCASE.length();

    public void run(){

        String message = readLine("Write a message for it to be encoded:");
        int distance = readInt("Select the key:");
        String encoded = encodeCaesar(message,realDistance(distance));
        println("The encoded message is: " + encoded );
        println("The plaintext message is: " + encodeCaesar(encoded,realDistance(-distance)));

        test();
    }

    public String encodeCaesar(String message, int distance){
        char[] result = new char[message.length()];
        for(int i = 0; i < message.length(); i++){
            result[i] = newChar(message.charAt(i), distance);
        }
        return new String(result);
    }

    public boolean isLower(char ch){
        return 'a' <= ch && ch <= 'z';
    }
    public boolean isUpper(char ch){
        return 'A' <= ch && ch <= 'Z';
    }
    public int realDistance(int distance){
        int realDistance = distance % LETTERS;
        if(realDistance < 0){
            realDistance += LETTERS;
        }
        return realDistance;
    }
    public char newChar(char ch, int distance){
        int position;
        if(isUpper(ch)){
            position = (UPPERCASE.indexOf(ch) + distance) % LETTERS;
            return UPPERCASE.charAt(position);
        }
        if(isLower(ch)){
            position = (LOWERCASE.indexOf(ch) + distance) % LETTERS;
            return LOWERCASE.charAt(position);
        }
        return ch;
    }
    public void test(){
        String[] text = new String[] {"This is an easy test.","This is an inverse test.","This!!test$%67576has&/72982symbols.","This should remain the same."};
        int[] distance = new int[] {15,-8,21,0};
        String[] result = new String[] {"Iwxh xh pc tphn ithi.","Lzak ak sf afnwjkw lwkl.","Ocdn!!ozno$%67576cvn&/72982nthwjgn.","This should remain the same."};

        for(int i = 0; i < text.length; i++){
            String expected = encodeCaesar(text[i],realDistance(distance[i]));
            if(result[i].equals(expected)){
                println("OK " + (i+1));
            }
        }
    }
    public static void main(String[] args){
        new Problema3().start(args);
    }
}

