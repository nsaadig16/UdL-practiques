package cat.udl.eps.ed.practica2.fibonacci;

class FibonacciRecTest extends AbstractFibonacciTest {

    @Override
    public int fibonacciImplementation(int n) {
        return Fibonacci.fibonacciRec(n);
    }
}
