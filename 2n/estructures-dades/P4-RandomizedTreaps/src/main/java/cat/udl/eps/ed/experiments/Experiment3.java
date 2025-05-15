package cat.udl.eps.ed.experiments;

import java.util.Collections;

import static cat.udl.eps.ed.experiments.ExperimentUtils.*;

public class Experiment3 {

    public static void main(String[] args) {
        int log = 20;
        int size = 1 << (log + 1) - 1;
        for(int i = 0; i < 101; i++) {
            var elems = makeList(size);
            var treap = sequentialTreapInsertions(elems);
            System.out.printf("%.2f\n", treap.height() / (double) log);
        }
    }
}
