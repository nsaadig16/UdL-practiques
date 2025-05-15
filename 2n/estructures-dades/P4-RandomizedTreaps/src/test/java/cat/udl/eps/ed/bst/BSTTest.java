package cat.udl.eps.ed.bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BSTTest extends MapTest {

    @BeforeEach
    void createMap() {
        map = new BST<>();
    }

    @RepeatedTest(value = 5, failureThreshold = 2)
    @DisplayName("The height after randomly inserting n elements is less than 3 * log n")
    void testHeight() {
        var log = 10;
        var evens = shuffledEvensFrom2(1 << log);
        for (var i: evens) {
            map.put(i, i);
        }
        var bst = (BST<Integer, Integer>) map;
        assertTrue(bst.height() < log * 3);
    }

    @Test
    void testBSTProperty() {
        var bst = (BST<Integer, Integer>) map;
        var evens = shuffledEvensFrom2(1000);
        for (Integer e : evens) {
            bst.put(e, e);
        }
        assertTrue(isBSTPropertyMaintained(bst));
    }

    private boolean isBSTPropertyMaintained(BST<Integer,Integer> bst) {
        var inOrderList = bst.inorderTraversal();

        for (int i = 1; i < inOrderList.size(); i++) {
            if (inOrderList.get(i) <= inOrderList.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

}
