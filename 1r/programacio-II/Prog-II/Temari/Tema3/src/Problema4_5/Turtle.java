package Problema4_5;
import acm.program.GraphicsProgram;
import acm.graphics.*;
import java.awt.Color;
import acm.util.RandomGenerator;
public class Turtle {

    private final GOval turtle;
    private final GLabel label;


    public Turtle (double position, double size, String dorsal){
        this.turtle = new GOval(0.0,position,size,size);
        this.turtle.setColor(Color.GREEN);
        this.turtle.setFilled(true);
        this.label = new GLabel(dorsal,size/2.0,position + size/2.0);
        this.label.setFont("*-*-20");
    }

    public void move(){
        double movement = rollDie();
        this.turtle.move(movement,0.0);
        this.label.move(movement,0.0);
    }
    public double getY(){
        return this.turtle.getY();
    }
    public double getRightExtrem(){
        return this.turtle.getX() + this.turtle.getWidth();
    }

    public void declareWinner(){
        this.turtle.setColor(Color.RED);
    }

    public void addToCanvas(GraphicsProgram program){
        program.add(this.turtle);
        program.add(this.label);
    }

    private int rollDie(){
        RandomGenerator rgen = RandomGenerator.getInstance();
        return rgen.nextInt(1,6);
    }




}
