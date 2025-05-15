package Problema7;

public class Rational {

    private int num;
    private int den;

    public Rational(int numerator, int denominator){
        this.num = numerator;
        this.den = denominator;
        this.simplify();
    }

    //-------------------------------------------------------------------
    //Instance methods

    public void add(Rational rational){
        int lcm = lcm(this.den,rational.den);
        int num1 = lcm * this.num / this.den;
        int num2 = lcm * rational.num / rational.den;
        this.num = num1 + num2;
        this.den = lcm;
        this.simplify();
    }
    public void substract( Rational rational){
        Rational negativeRational = new Rational(-rational.num,rational.den);
        this.add(negativeRational);
    }

    public void multiply(Rational rational){
        this.num *= rational.num;
        this.den *= rational.den;
        this.simplify();
    }

    public void divide(Rational rational){
        this.num *= rational.den;
        this.den *= rational.num;
        this.simplify();
    }

    //---------------------------------------------------------------
    //Static methods

    public static Rational add(Rational r1, Rational r2){
        int lcm = lcm(r1.den,r2.den);
        int num1 = lcm * r1.num / r1.den;
        int num2 = lcm * r2.num / r2.den;

        return new Rational(num1 + num2,lcm);
    }
    public static Rational substract(Rational r1, Rational r2){
        Rational negativeR2 = new Rational(-r2.num,r2.den);
        return Rational.add(r1,negativeR2);
    }
    public static Rational multiply(Rational r1, Rational r2){
        int resNum = r1.num * r2.num;
        int resDen = r1.den * r2.den;
        return new Rational(resNum,resDen);
    }
    public static Rational divide(Rational r1, Rational r2){
        int resNum = r1.num * r2.den;
        int resDen = r1.den * r2.num;
        return new Rational(resNum,resDen);
    }

    //--------------------------------------------------

    private void simplify (){
        int gcd = this.gcd();
        this.num /= gcd;
        this.den /= gcd;
        this.moveSign();
    }

    private static int lcm(int den1, int den2){
        int lcm = Math.max(den1,den2);
        while(lcm % den1 != 0 || lcm % den2 != 0){
            lcm += 1;
        }
        return lcm;
    }
    private int gcd(){
        int num = Math.abs(this.num);
        int den = Math.abs(this.den);
        if (num == 0 ){
            return den;
        }
        while(num != den){
            if(num > den){
                num -= den;
            }else{
                den -= num;
            }
            if(num == 1 || den == 1){
                return 1;
            }
        }
        return num;
    }

    public String toString (){
        if(this.den == 1){
            return String.valueOf(this.num);
        }
        return this.num + "/" + this.den;
    }

    public boolean equalsRational (Rational r){
        return this.num == r.num && this.den == r.den;
    }

    private void moveSign (){
        if(this.den < 0){
            this.den = -this.den;
            this.num = -this.num;
        }
    }
}