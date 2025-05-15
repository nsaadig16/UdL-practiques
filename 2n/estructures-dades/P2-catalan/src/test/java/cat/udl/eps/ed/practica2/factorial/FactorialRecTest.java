package cat.udl.eps.ed.practica2.factorial;

class FactorialRecTest extends AbstractFactorialTest {

    @Override
    public long factorialImplementation(int n) {
        return Factorial.factorialRec(n);
    }

}
