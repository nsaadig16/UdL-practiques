import acm.program.GraphicsProgram;
import acm.graphics.GOval;
import java.awt.Color;
import acm.util.RandomGenerator;

public class Problema2 extends GraphicsProgram {

    int NUM_TURTLES = 5 ;
    double PADDING = 8.0;
    double TIMEOUT = 20.0;

    public void run() {
        GOval[] turtles = new GOval[NUM_TURTLES];
        addTurtles(turtles);
        startRace(turtles);
    }

    public void addTurtles(GOval[] turtles){
        double size = (getHeight() / (double) NUM_TURTLES) - (PADDING * 2);
        for(int i = 0; i < NUM_TURTLES; i++) {
            turtles[i] = new GOval(0.0, i*(size + PADDING) + PADDING, size, size);
            turtles[i].setColor(Color.GREEN);
            turtles[i].setFilled(true);
            add(turtles[i]);
        }
    }

    public void startRace(GOval[] turtles){
        waitForClick();
        while(!touchingEdge(turtles)){
            moveTurtles(turtles);
        }
    }
    public void moveTurtles(GOval[] turtles){
        for(int i = 0; i < NUM_TURTLES; i++){
            turtles[i].move(rollDie(),0.0);
        }
        pause(TIMEOUT);
    }

    public boolean touchingEdge(GOval[] turtles){
        boolean finish = false;

        for(int i = 0; i < NUM_TURTLES && !finish; i++){
            if(getWidth() < turtles[i].getX() + turtles[i].getWidth()){
                finish = true;
                turtles[i].setColor(Color.RED);
                turtles[i].setFillColor(Color.RED);
            }
        }
        return finish;
    }
    public int rollDie(){
        RandomGenerator rgen = RandomGenerator.getInstance();
        return rgen.nextInt(1,6);
    }
    public static void main(String[] args){
        new Problema2().start(args);
    }
}
