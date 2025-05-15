package cat.udl.eps.ed.experiments;

import cat.udl.eps.ed.bst.BST;
import cat.udl.eps.ed.bst.Treap;

import java.util.ArrayList;

public class ExperimentUtils {

    static ArrayList<Integer> makeList(int size) {
        var elems = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            elems.add(i + 1);
        }
        return elems;
    }

    static BST<Integer, Integer> balancedInsertions(
            ArrayList<Integer> elems) {
        var bst = new BST<Integer, Integer>();
        balancedInsertions(bst, elems, 0, elems.size());
        return bst;
    }

    private static void balancedInsertions(
            BST<Integer, Integer> bst,
            ArrayList<Integer> elems,
            int begin,
            int end) {
        if (begin >= end) return;
        int midIndex = begin + (end - begin) / 2;

        bst.put(elems.get(midIndex), elems.get(midIndex));
        balancedInsertions(bst, elems, begin, midIndex);
        balancedInsertions(bst, elems, midIndex + 1, end);
    }

    static BST<Integer, Integer> sequentialBSTInsertions(
            ArrayList<Integer> elems) {
        var bst = new BST<Integer, Integer>();
        for (int elem : elems) {
            bst.put(elem, elem);
        }
        return bst;
    }

    static Treap<Integer, Integer> sequentialTreapInsertions(
            ArrayList<Integer> elems) {
        var treap = new Treap<Integer, Integer>();
        for (int elem : elems) {
            treap.put(elem, elem);
        }
        return treap;
    }
}
