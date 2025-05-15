package cat.udl.eps.ed.practica2.fibonacci;

class FibonacciOriginalTest extends AbstractFibonacciTest {

    @Override
    public int fibonacciImplementation(int n) {
        return Fibonacci.fibonacciOriginal(n);
    }
}
