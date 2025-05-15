package Problema7;
import acm.program.*;

public class Problema7 extends CommandLineProgram{

    public void run(){
        test();
    }

    public void test(){

        testInstance();

        testStatic();

    }

    public void testInstance(){
        println("INSTANCE METHODS:");
        printBar();
        testInstanceAdd();
        testInstanceSubstract();
        testInstanceMultiply();
        testInstanceDivide();
    }

    public void testStatic (){
        println("STATIC METHODS:");
        printBar();
        testStaticAdd();
        testStaticSubstract();
        testStaticMultiply();
        testStaticDivide();
    }

    public void testInstanceAdd(){
        Rational[] expected = new Rational[] {
                new Rational(3,4),
                new Rational(5,6),
                new Rational(-4,3),
                new Rational(4,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(1,4),
                new Rational(7,12),
                new Rational(13,-6),
                new Rational(20,9)
        };
        Rational[] operator2 = new Rational[] {
                new Rational(2,4),
                new Rational(2,8),
                new Rational(5,6),
                new Rational(16,9)
        };
        for( int i = 0; i < expected.length; i++){
            operator1[i].add(operator2[i]);
            check(expected[i],operator1[i],"add",i+1);
        }
        printBar();
    }

    public void testInstanceSubstract(){
        Rational[] expected = new Rational[] {
                new Rational(-11,10),
                new Rational(0,1),
                new Rational(7,30),
                new Rational(1,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(3,-5),
                new Rational(2,8),
                new Rational(5,6),
                new Rational(6,4)
        };
        Rational[] operator2 = new Rational[] {
                new Rational(2,4),
                new Rational(1,4),
                new Rational(3,5),
                new Rational(1,2)
        };
        for( int i = 0; i < expected.length; i++){
            operator1[i].substract(operator2[i]);
            check(expected[i],operator1[i],"substract",i+1);
        }
        printBar();
    }

    public void testInstanceMultiply(){
        Rational[] expected = new Rational[] {
                new Rational(3,14),
                new Rational(4,1),
                new Rational(5,2),
                new Rational(-12,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(3,4),
                new Rational(4,3),
                new Rational(-5,3),
                new Rational(-9,6)
        };
        Rational[] operator2 = new Rational[] {
                new Rational(2,7),
                new Rational(6,2),
                new Rational(9,-6),
                new Rational(8,1)
        };
        for( int i = 0; i < expected.length; i++){
            operator1[i].multiply(operator2[i]);
            check(expected[i],operator1[i],"multiply",i+1);
        }
        printBar();
    }

    public void testInstanceDivide(){
        Rational[] expected = new Rational[] {
                new Rational(5,6),
                new Rational(1,1),
                new Rational(33,4),
                new Rational(0,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(4,3),
                new Rational(3,4),
                new Rational(-9,3),
                new Rational(0,3)
        };
        Rational[] operator2 = new Rational[]{
                new Rational(8, 5),
                new Rational(15, 20),
                new Rational(4, -11),
                new Rational(4, 8)
        };
        for( int i = 0; i < expected.length; i++){
            operator1[i].divide(operator2[i]);
            check(expected[i],operator1[i],"divide",i+1);
        }
        printBar();
    }

    public void testStaticAdd(){
        Rational[] expected = new Rational[] {
                new Rational(3,4),
                new Rational(5,6),
                new Rational(-4,3),
                new Rational(4,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(1,4),
                new Rational(7,12),
                new Rational(13,-6),
                new Rational(20,9)
        };
        Rational[] operator2 = new Rational[] {
                new Rational(2,4),
                new Rational(2,8),
                new Rational(5,6),
                new Rational(16,9)
        };
        for( int i = 0; i < expected.length; i++){
            check(expected[i],Rational.add(operator1[i],operator2[i]),"add",i+1);
        }
        printBar();
    }

    public void testStaticSubstract(){
        Rational[] expected = new Rational[] {
                new Rational(-11,10),
                new Rational(0,1),
                new Rational(7,30),
                new Rational(1,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(3,-5),
                new Rational(2,8),
                new Rational(5,6),
                new Rational(6,4)
        };
        Rational[] operator2 = new Rational[] {
                new Rational(2,4),
                new Rational(1,4),
                new Rational(3,5),
                new Rational(1,2)
        };
        for( int i = 0; i < expected.length; i++){
            check(expected[i],Rational.substract(operator1[i],operator2[i]),"substract",i+1);
        }
        printBar();
    }

    public void testStaticMultiply(){
        Rational[] expected = new Rational[] {
                new Rational(3,14),
                new Rational(4,1),
                new Rational(5,2),
                new Rational(-12,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(3,4),
                new Rational(4,3),
                new Rational(-5,3),
                new Rational(-9,6)
        };
        Rational[] operator2 = new Rational[] {
                new Rational(2,7),
                new Rational(6,2),
                new Rational(9,-6),
                new Rational(8,1)
        };
        for( int i = 0; i < expected.length; i++){
            check(expected[i],Rational.multiply(operator1[i],operator2[i]),"multiply",i+1);
        }
        printBar();
    }
    public void testStaticDivide(){
        Rational[] expected = new Rational[] {
                new Rational(5,6),
                new Rational(1,1),
                new Rational(33,4),
                new Rational(0,1)
        };
        Rational[] operator1 = new Rational[] {
                new Rational(4,3),
                new Rational(3,4),
                new Rational(-9,3),
                new Rational(0,3)
        };
        Rational[] operator2 = new Rational[]{
                new Rational(8, 5),
                new Rational(15, 20),
                new Rational(4, -11),
                new Rational(4, 8)
        };
        for( int i = 0; i < expected.length; i++){
            check(expected[i],Rational.divide(operator1[i],operator2[i]),"divide",i+1);
        }
        printBar();
    }

    public void check(Rational expected, Rational actual, String operation, int test){

        if(expected.equalsRational(actual)){
            println("OK in test #" + test + " of " + operation);
        }else{
            print("WRONG in test #" + test + " of " + operation);
            println(":it is " + actual.toString() + " but it should be " + expected.toString());
        }
    }

    public void printBar(){
        println("--------------------------------------------------------------------------");
    }

    public static void main(String[] args){
        new Problema7().start(args);
    }

}
