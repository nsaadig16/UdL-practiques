package cat.udl.eps.ed.bst;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class MapTest {

    Map<Integer, Integer> map;

    static List<Integer> shuffledEvensFrom2(int n) {
        var list = new ArrayList<Integer>(n);
        for (int i = 1; i <= n; i++) {
            list.add(i * 2);
        }
        Collections.shuffle(list);
        return list;
    }

    @Test
    @DisplayName("isEmpty / put / containsKey / get are coherent")
    void testPutAndGet() {
        var evens = shuffledEvensFrom2(1000);
        assertTrue(map.isEmpty());
        for (var i: evens) {
            map.put(i, i);
        }
        assertFalse(map.isEmpty());
        // We have inserted 2, 4, 6, ..., 2000
        for (var i: evens) {
            // we can find all of them in the map
            assertTrue(map.containsKey(i));
            // we get the value we put
            assertEquals(i, map.get(i));
        }
        for (int i = 1; i <= 2001; i += 2) {
            // note that we test numbers not in the map considering
            // numbers lower than the minimum (2), between the min and the max,
            // and higher than the maximum (2000)
            // the map does not contain the odd numbers
            assertFalse(map.containsKey(i));
            // we cannot find them
            assertNull(map.get(i));
        }
    }

    @Test
    @DisplayName("get() should be coherent with put()")
    void testGet() {
        map.put(10, 10);
        map.put(20, 20);
        map.put(5, 5);

        assertEquals(10, map.get(10));
        assertEquals(20, map.get(20));
        assertEquals(5, map.get(5));
        map.put(10,500);
        assertEquals(500,map.get(10));
    }

    @Test
    @DisplayName("isEmpty() should return true when the map is empty")
    void isEmpty() {
        assertTrue(map.isEmpty());

        map.put(10, 10);
        assertFalse(map.isEmpty());

        map.remove(10);
        assertTrue(map.isEmpty());

        map.put(20, 20);
        map.put(5, 5);
        assertFalse(map.isEmpty());
    }

    @Test
    @DisplayName("containsKey() should return true when the map contains a key")
    void containsKey() {
        assertFalse(map.containsKey(10));

        map.put(10, 10);
        map.put(20, 20);
        map.put(5, 5);

        assertTrue(map.containsKey(10));
        assertTrue(map.containsKey(20));
        assertTrue(map.containsKey(5));

        assertFalse(map.containsKey(15));
        assertFalse(map.containsKey(30));
    }


    @Test
    @DisplayName("Passing null as a parameter should throw NullPointerException")
    void nullParameter() {
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.put(null, 0));
        assertThrows(NullPointerException.class, () -> map.put(0, null));
        assertThrows(NullPointerException.class, () -> map.put(null, null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }

    @Test
    @DisplayName("remove() should remove the nodes from the map")
    void remove() {
        map.put(10, 10);
        map.put(20, 20);
        map.put(5, 5);

        map.remove(5);
        assertTrue(map.containsKey(10));
        assertTrue(map.containsKey(20));
        assertFalse(map.containsKey(5));

        map.remove(20);
        assertTrue(map.containsKey(10));
        assertFalse(map.containsKey(20));
        assertFalse(map.containsKey(5));

        map.remove(10);
        assertFalse(map.containsKey(10));
        assertFalse(map.containsKey(20));
        assertFalse(map.containsKey(5));
        assertTrue(map.isEmpty());
    }

    @Test
    @DisplayName("remove() on a non-existent key should do nothing")
    void testRemoveNonExistentKey() {
        map.put(10, 10);
        map.put(20, 20);
        assertTrue(map.containsKey(10));
        assertTrue(map.containsKey(20));
        map.remove(30);
        assertTrue(map.containsKey(10));
        assertTrue(map.containsKey(20));
    }
}
        
