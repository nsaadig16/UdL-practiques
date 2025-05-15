package cat.udl.eps.ed.practica2.factorial;

class FactorialIterTest extends AbstractFactorialTest {

    @Override
    public long factorialImplementation(int n) {
        return Factorial.factorialIter(n);
    }

}
