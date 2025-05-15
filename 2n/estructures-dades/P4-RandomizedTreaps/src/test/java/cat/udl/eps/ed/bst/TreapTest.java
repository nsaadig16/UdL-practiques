package cat.udl.eps.ed.bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TreapTest extends MapTest {

    @BeforeEach
    void createMap() {
        map = new Treap<>();
    }

    @RepeatedTest(value = 5, failureThreshold = 2)
    @DisplayName("The height after inserting n elements in order is less than 3 * log n")
    void testHeight() {
        var log = 10;
        for (int i = 0; i < (1 << log); i++) {
            map.put(i, i);
        }
        var treap = (Treap<Integer, Integer>) map;
        assertTrue(treap.height() < log * 3);
    }

    @Test
    @DisplayName("Treap maintains heap property after insertions")
    void testHeapProperty() {
        List<Integer> evens = shuffledEvensFrom2(100);
        for (Integer e : evens) {
            map.put(e, e);
        }
        var treap = (Treap<Integer, Integer>) map;
        assertTrue(treap.keepsHeapProperty());
    }


    @Test
    @DisplayName("Treap maintains order property")
    void testOrderProperty() {
        var treap = new Treap<Integer,Integer>();
        List<Integer> evens = shuffledEvensFrom2(100);
        for (Integer e : evens) {
            treap.put(e, e);
        }
        evens.sort(null);
        assertEquals(evens, treap.getInOrder());
    }


}
