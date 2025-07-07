package Problema8;
import java.text.DecimalFormat;

public class Complex {

    private double real;
    private double imaginary;

    public Complex (double realPart, double imaginaryPart){
        setReal(realPart);
        setImaginary(imaginaryPart);
    }


    public void setReal(double real){
        this.real = real;
    }
    public void setImaginary(double imaginary){
        this.imaginary = imaginary;
    }

    private double module(){
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }
    private double argument(){
        if(this.real == 0.0 && this.imaginary == 0.0){
            return 0.0;
        }
        if(imaginary == 0.0 && this.real < 0){
            return -90.0;
        }
        return Math.toDegrees(Math.atan(this.imaginary / this.real));
    }

    public void setModule(double module){
        double arg = this.argument();
        this.real = module * Math.cos(arg);
        this.imaginary = module * Math.sin(arg);
    }

    public void setArgument(double argument){
        double mod = this.module();
        this.real = mod * Math.cos(argument);
        this.imaginary = mod * Math.sin(argument);
    }

    public double getReal(){
        return this.real;
    }

    public double getImaginary(){
        return this.imaginary;
    }

    public double getModule(){
        return this.module();
    }

    public double getArgument(){
        return this.argument();
    }

    //----------------------------------------------------
    //Instance methods

    public void add (Complex z){
        this.real += z.real;
        this.imaginary += z.imaginary;
    }

    public void substract(Complex z){
        this.real -= z.real;
        this.imaginary -= z.imaginary;
    }

    public void multiply (Complex z){
        double module = this.module() * z.module();
        double argument = this.argument() + z.argument();
        this.real = module * Math.cos(argument);
        this.imaginary = module * Math.sin(argument);
    }

    public void divide (Complex z){
        double module = this.module() / z.module();
        double argument = this.argument() - z.argument();
        this.real = module * Math.cos(argument);
        this.imaginary = module * Math.sin(argument);
    }

    //--------------------------------------------------------------------
    //Static methods

    public Complex add (Complex z1, Complex z2){
        double real = z1.real + z2.real;
        double imaginary = z1.imaginary + z2.imaginary;
        return new Complex(real,imaginary);
    }
    public Complex substract (Complex z1, Complex z2){
        double real = z1.real - z2.real;
        double imaginary = z1.imaginary - z2.imaginary;
        return new Complex(real,imaginary);
    }
    public Complex multiply (Complex z1, Complex z2){
        double module = z1.module() * z2.module();
        double argument = z1.argument() + z2.argument();
        return new Complex(module * Math.cos(argument),module * Math.sin(argument));
    }
    public Complex divide (Complex z1, Complex z2){
        double module = z1.module() / z2.module();
        double argument = z1.argument() - z2.argument();
        return new Complex(module * Math.cos(argument),module * Math.sin(argument));
    }


    public boolean equalsComplex(Complex z){
        return this.real == z.real && this.imaginary == z.imaginary;
    }

    public String toStringBinomial (){
        String formattedRealPart = new DecimalFormat("0.##").format(this.real);
        String formattedImaginaryPart = new DecimalFormat("0.##").format(this.imaginary);
        if (this.real == 0.0 && this.imaginary == 0.0){
            return "0";
        }
        if(this.real == 0.0){
            return formattedImaginaryPart + "i";
        }
        if (this.imaginary == 0.0){
            return formattedRealPart;
        }
        return formattedRealPart + " + " + formattedImaginaryPart + "i";
    }

    public String toStringPolar (){
        String formattedModule = new DecimalFormat("0.##").format(module());
        String formattedArgument = new DecimalFormat("0.##").format(argument());
        return formattedModule + "∠" + formattedArgument + "º";
    }
}
