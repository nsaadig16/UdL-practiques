package cat.udl.eps.ed.HeapSort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Comparator.naturalOrder;
import static org.junit.jupiter.api.Assertions.*;

public class SwapTest {
    @Test
    void SwapBetweenString() {
        String[] elements = new String[5];
        String[] expected = {"Armari", "Porta", "Catalunya", "Penelope", "patata"};
        var heap = new HeapSort.BinaryHeap<>(elements, naturalOrder());

        heap.add("patata");
        heap.add("Penelope");
        heap.add("Catalunya");
        heap.add("Porta");
        heap.add("Armari");

        heap.swap(0, 4);

        assertArrayEquals(expected, elements);
    }

    @Test
    void SwapBetweenInteger() {
        Integer[] elements = new Integer[5];
        Integer[] expected = {3069, 30, 2, 12, 25};
        var heap = new HeapSort.BinaryHeap<>(elements, naturalOrder());

        heap.add(25);
        heap.add(30);
        heap.add(2);
        heap.add(3069);
        heap.add(12);
        heap.swap(3, 4);

        assertArrayEquals(expected, elements);
    }
    @Test
    @DisplayName("swap() on non-existent node should throw OutOfBoundsException")
    void SwapOutOfBounds() {
        Integer[] elements = new Integer[5];
        var heap = new HeapSort.BinaryHeap<>(elements, naturalOrder());

        heap.add(25);
        heap.add(30);
        heap.add(2);
        heap.add(3069);
        heap.add(12);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            heap.swap(6, 0);
        });
    }
}
