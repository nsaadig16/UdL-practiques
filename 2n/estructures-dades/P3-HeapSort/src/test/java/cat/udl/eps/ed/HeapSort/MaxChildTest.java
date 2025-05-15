package cat.udl.eps.ed.HeapSort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Comparator.naturalOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxChildTest {

    @Test
    void maxChildString() {
        String[] elements = new String[5];
        var heap = new HeapSort.BinaryHeap<>(elements, naturalOrder());

        heap.add("patata");
        heap.add("Penelope");
        heap.add("Catalunya");
        heap.add("Porta");
        heap.add("Armari");

        assertEquals(3, heap.maxChild(1));
    }

    @Test
    void maxChildInteger() {
        Integer[] elements = new Integer[5];
        var heap = new HeapSort.BinaryHeap<>(elements, naturalOrder());

        heap.add(25);
        heap.add(30);
        heap.add(2);
        heap.add(3069);
        heap.add(26);

        assertEquals(1, heap.maxChild(0)); // Right child is larger
        assertEquals(4, heap.maxChild(1)); // Left child is larger
    }

    //TODO: Fes testos així per les excepcions
    @Test
    @DisplayName("maxChild() on non-existent node should throw OutOfBoundsException")
    void MaxChildOutOfBounds() {
        Integer[] elements = new Integer[5];
        var heap = new HeapSort.BinaryHeap<>(elements, naturalOrder());

        heap.add(25);
        heap.add(30);
        heap.add(2);
        heap.add(3069);
        heap.add(12);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            heap.maxChild(6);
        });
    }

}

