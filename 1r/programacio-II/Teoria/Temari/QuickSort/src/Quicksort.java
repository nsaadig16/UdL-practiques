import acm.program.CommandLineProgram;

public class Quicksort extends CommandLineProgram {

    private final int[] v = {3,5,2,-5,1,4,2};

    public void run() {
        quickSort(0, v.length);
        println(v);
    }
    public void quickSort(int left, int right){
        if (right - left > 1) {
            int pivotPos = pivotPos(left,right);
            int pivotValue = v[pivotPos];
            swap(left,pivotPos);
            int pos = partition(pivotValue, left + 1, right);
            swap(left,pos - 1);
            quickSort(left,pos -1);
            quickSort(pos, right);
        }
    }

    private void swap(int pos1, int pos2){
        int aux = v[pos1];
        v[pos1] = v[pos2];
        v[pos2] = aux;
    }

    private int pivotPos(int left, int right){
        return left + (right - left) / 2;
    }

    private int partition(int pivot, int inf, int sup){
        if(inf == sup){
            return inf;
        } else if (v[inf] <= pivot) {
            return partition(pivot,inf + 1, sup);
        } else if (v[sup - 1] > pivot) {
            return partition(pivot, inf, sup - 1);
        }else{
            swap(inf, sup - 1);
            return partition(pivot, inf + 1, sup - 1);
        }
    }

    private void println (int[] v){
        print("{");
        for (int i = 0; i < v.length; i++){
            if(i != v.length - 1){
                print(v[i] + ",");
            }else {
                print(v[i]);
            }
        }
        println("}");
    }

    public static void main(String[] args) {
        new Quicksort().start(args);
    }
}
