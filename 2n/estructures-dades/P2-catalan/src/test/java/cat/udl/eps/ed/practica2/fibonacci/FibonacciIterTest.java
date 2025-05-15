package cat.udl.eps.ed.practica2.fibonacci;

class FibonacciIterTest extends AbstractFibonacciTest {

    @Override
    public int fibonacciImplementation(int n) {
        return Fibonacci.fibonacciIter(n);
    }
}
