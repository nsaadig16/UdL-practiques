package Problema8;
import acm.program.*;

public class Problema8 extends CommandLineProgram{

    public void run(){
        test();
        Complex z = new Complex(-1,-1);
        println(z.toStringPolar());
        println(z.toStringBinomial());
    }

    public void test(){
        Complex z1 = new Complex(3.0,4.0);

        println("CONSTRUCTOR:");
        println("real part:" + z1.getReal());
        println("imaginary part:" + z1.getImaginary());
        println(z1.toStringBinomial());
        println("module:" + z1.getModule());
        println("argument:" + z1.getArgument());
        println(z1.toStringPolar());
        printBar();

        println("SETTERS:");
        println("SET BINOMIAL:");
        z1.setReal(9.0);
        z1.setImaginary(16.0);
        println("real part:" + z1.getReal());
        println("imaginary part:" + z1.getImaginary());
        println(z1.toStringBinomial());;
        println("module:" + z1.getModule());
        println("argument:" + z1.getArgument());
        println(z1.toStringPolar());
        printBar();

        println("SET POLAR:");
        z1.setModule(Math.sqrt(2.0));
        z1.setArgument(45.0);
        println("real part:" + z1.getReal());
        println("imaginary part:" + z1.getImaginary());
        println(z1.toStringBinomial());;
        println("module:" + z1.getModule());
        println("argument:" + z1.getArgument());
        println(z1.toStringPolar());
        printBar();



    }
    public void printBar(){
        println("--------------------------------------------------------------------------");
    }



    public static void main(String[] args){
        new Problema8().start(args);
    }
}


