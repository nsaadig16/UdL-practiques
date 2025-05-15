package Problema4_5;
import acm.program.*;
import java.io.*;

public class TurtleProgram extends GraphicsProgram {
    int NUM_TURTLES = 4 ;
    double PADDING = 8.0;
    double TIMEOUT = 20.0;
    private static final String FILE_NAME = "photoFinish.txt";
    private static final String BUFF_FILE_NAME = "buffPhotoFinish.txt";

    public void run(){
        Turtle[] turtles = new Turtle[NUM_TURTLES];
        addTurtles(turtles);
        startRace(turtles);
    }

    private void doPhotoFinish(Turtle[] turtles){
        try {
            FileWriter camera = new FileWriter(FILE_NAME);
            BufferedWriter bufferedCamera = new BufferedWriter(new FileWriter(BUFF_FILE_NAME));
            for (Turtle turtle : turtles) {
                shootPhoto(camera, turtle);
                shootPhoto(bufferedCamera,turtle);
            }
            camera.close();
            bufferedCamera.close();
        }catch(IOException e){
            println("Problem :(");
        }
    }

    private void shootPhoto(FileWriter camera, Turtle turtle) throws IOException{
        camera.write(String.valueOf(turtle.getRightExtrem()));
        camera.write(",");
        camera.write(String.valueOf(turtle.getY()));
        camera.write(";");
    }
    private void shootPhoto(BufferedWriter camera, Turtle turtle) throws IOException{
        camera.write(String.valueOf(turtle.getRightExtrem()));
        camera.write(",");
        camera.write(String.valueOf(turtle.getY()));
        camera.newLine();
    }


    private void addTurtles(Turtle[] turtles){
        double size = (getHeight() / (double) NUM_TURTLES) - (PADDING * 2);
        for(int i = 0; i < NUM_TURTLES; i++){
            turtles[i] = new Turtle(i*(size + PADDING) + PADDING,size,String.valueOf(i+1));
            turtles[i].addToCanvas(this);
        }
    }

    private void startRace(Turtle[] turtles){
        waitForClick();
        while(!touchingEdge(turtles)){
            moveTurtles(turtles);
        }
    }

    private void moveTurtles(Turtle[] turtles){
        for (int i = 0; i < NUM_TURTLES; i++) {
            turtles[i].move();
        }
        pause(TIMEOUT);
    }
    private boolean touchingEdge(Turtle[] turtles){
        for(int i = 0; i < NUM_TURTLES; i++){
            if(turtles[i].getRightExtrem() > getWidth()){
                doPhotoFinish(turtles);
                turtles[i].declareWinner();
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        new TurtleProgram().start(args);
    }


}
