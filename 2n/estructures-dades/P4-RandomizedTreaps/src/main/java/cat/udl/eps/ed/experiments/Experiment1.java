package cat.udl.eps.ed.experiments;

import static cat.udl.eps.ed.experiments.ExperimentUtils.*;

public class Experiment1 {

    public static void main(String[] args) {
        int log = 20;
        int size = 1 << (log + 1) - 1;
        var elems = makeList(size);
        var bst = balancedInsertions(elems);
        System.out.println(bst.height());
    }
}
