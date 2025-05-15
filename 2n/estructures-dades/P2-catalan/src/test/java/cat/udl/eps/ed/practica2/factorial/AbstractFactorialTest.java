package cat.udl.eps.ed.practica2.factorial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractFactorialTest {

    abstract long factorialImplementation(int n);

    @ParameterizedTest(name = "factorial({0}) should be {1}")
    @CsvSource({
        "0, 1",
        "1, 1",
        "2, 2",
        "3, 6",
        "4, 24",
        "5, 120",
        "6, 720",
        "7, 5040",
        "8, 40320",
        "9, 362880",
        "10, 3628800",
        "11, 39916800",
        "12, 479001600",
        "13, 6227020800",
        "14, 87178291200",
        "15, 1307674368000",
    })
    void test(int n, long expected) {
        assertEquals(expected, factorialImplementation(n));
    }
}
