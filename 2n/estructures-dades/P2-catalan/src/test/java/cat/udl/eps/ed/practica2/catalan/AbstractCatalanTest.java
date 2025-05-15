package cat.udl.eps.ed.practica2.catalan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractCatalanTest {

    abstract long catalanImplementation(int n);

    @ParameterizedTest(name = "catalan({0}) should be {1}")
    @CsvSource({
            "0,1",
            "1,1",
            "2,2",
            "3,5",
            "4,14",
            "5,42",
            "6,132",
            "7,429",
            "8,1430",
            "9,4862",
            "10,16796",
            "11,58786",
            "12,208012",
    })
    void test(int n, long expected) {
        assertEquals(expected, catalanImplementation(n));
    }
}