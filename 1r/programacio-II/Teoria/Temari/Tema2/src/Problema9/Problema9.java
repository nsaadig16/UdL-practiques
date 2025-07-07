package Problema9;
import acm.program.*;

public class Problema9 extends GraphicsProgram {
    int NUM_TURTLES = 5 ;
    double PADDING = 8.0;
    double TIMEOUT = 20.0;

    public void run(){
        Turtle[] turtles = new Turtle[NUM_TURTLES];
        addTurtles(turtles);
        startRace(turtles);
    }

    public void addTurtles(Turtle[] turtles){
        double size = (getHeight() / (double) NUM_TURTLES) - (PADDING * 2);
        for(int i = 0; i < NUM_TURTLES; i++){
            turtles[i] = new Turtle(i*(size + PADDING) + PADDING,size,String.valueOf(i+1));
            turtles[i].addToCanvas(this);
        }
    }

    public void startRace(Turtle[] turtles){
        waitForClick();
        while(!touchingEdge(turtles)){
            moveTurtles(turtles);
        }
    }

    public void moveTurtles(Turtle[] turtles){
        for (int i = 0; i < NUM_TURTLES; i++) {
            turtles[i].move();
        }
        pause(TIMEOUT);
    }
    public boolean touchingEdge(Turtle[] turtles){
        for(int i = 0; i < NUM_TURTLES; i++){
            if(turtles[i].getRightExtrem() > getWidth()){
                turtles[i].declareWinner();
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        new Problema9().start(args);
    }

}
