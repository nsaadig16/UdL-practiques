import acm.program.GraphicsProgram;
import acm.graphics.GRect;
import java.awt.Color;
public class Problema1 extends GraphicsProgram{

    double SIZE = 50.0;
    double TIMEOUT = 20.0;
    double MOVEMENT = 3.0;

    public void run(){
        double x = (getWidth() - SIZE) / 2.0;
        double y = (getHeight()- SIZE) / 2.0;
        GRect square = new GRect(x,y,SIZE,SIZE);
        addSquare(square);
        moveSquare(square);
    }

    public void addSquare(GRect square){
        square.setColor(Color.RED);
        square.setFilled(true);
        //square.setFillColor(Color.RED); not needed, fill color starts as figure color
        add(square);
    }

   public void moveSquare(GRect square){
       double movement = MOVEMENT;
       while(true){
           square.move(movement,0.0);
           pause(TIMEOUT);
           if(touchingEdge(square)){
               movement = -movement;
           }
       }
    }
    /*
    public void moveSquare(GRect square){
        double movement = MOVEMENT;
        double x,y;

        while(true){
            x = square.getX() + movement;
            y = square.getY();
            square.setLocation(x,y);
            pause(TIMEOUT);
            if(touchingEdge(square)){
                movement = -movement;
            }
        }
    }
    */
    public boolean touchingEdge(GRect square){
        return getWidth() < square.getX() + SIZE || square.getX() < 0.0;
    }

    public static void main(String[] args){
        new Problema1().start(args);
    }
}