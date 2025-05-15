package cat.udl.eps.ed.practica2.fibonacci;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractFibonacciTest {

    abstract int fibonacciImplementation(int n);

    @ParameterizedTest(name = "fibonacci({0}) should be {1}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "2, 1",
            "3, 2",
            "4, 3",
            "5, 5",
            "6, 8",
            "7, 13",
            "8, 21",
            "9, 34",
            "10, 55",
            "11, 89",
            "12, 144",
            "13, 233",
            "14, 377",
            "15, 610",
    })
    void test(int n, long expected) {
        assertEquals(expected, fibonacciImplementation(n));
    }
}
