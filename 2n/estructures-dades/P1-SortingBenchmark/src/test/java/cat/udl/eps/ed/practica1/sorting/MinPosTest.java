package cat.udl.eps.ed.practica1.sorting;
import cat.udl.eps.ed.practica1.sorting.IntArraySorter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinPosTest {
    @Test
    public void minPosTest() {
        IntArraySorter[] sorting = new IntArraySorter[]{
                new IntArraySorter(new int[]{4,3,1,9}),
                new IntArraySorter(new int[]{1,2,7,9,9}),
                new IntArraySorter(new int[]{8,3,6,9}),
                new IntArraySorter(new int[]{69})
        };
        int[][] solutions = new int[][] {
                {2,2,2,3},
                {0,1,2,3,4},
                {1,1,2,3},
                {0}
        };

        for (int i = 0; i < sorting.length; i++){
            for(int j = 0; j < solutions[i].length; j++){
                assertEquals(solutions[i][j], sorting[i].min(j));
            }
        }
    }
}
