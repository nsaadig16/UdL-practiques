
package cat.udl.eps.ed.practica1.sorting;


import cat.udl.eps.ed.practica1.sorting.IntArraySorter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author jmgimeno
 */
public abstract class AbstractSortTest {
    
    protected IntArraySorter sorting;
    
    protected abstract void doSort();
    
    @Test
    public void empty() {
        int[] array = new int[0];
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }

    @Test
    public void singleton() {
        int[] array = new int[] { 42 };
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }
    
    @Test
    public void sortedPair() {
        int[] array = new int[] { 21, 42 };
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }
    
    @Test
    public void unsortedPair() {
        int[] array = new int[] { 42, 21 };
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }
    
    @Test
    public void fibonacci() {
        int[] array = new int[] { 0, 1, 1, 2, 3, 5, 8, 13, 21 };
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }
    
    @Test
    public void reverseFibonacci() {
        int[] array = new int[] { 21, 13, 8, 5, 3, 2, 1, 1, 0 };
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }
    
    @Test
    public void unsorted() {
        int[] array = new int[] { 12, 7, 21, 34, 3, 8, 21, 17 };
        sorting = new IntArraySorter(array);
        doSort();
        assertTrue(sorting.isSorted());
    }
}
