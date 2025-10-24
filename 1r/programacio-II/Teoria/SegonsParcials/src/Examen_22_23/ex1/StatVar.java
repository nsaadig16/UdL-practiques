package Examen_22_23.ex1;

public class StatVar {

    public static final int SIZE = 4 + 4 + 8;
    private int count;
    private int sum;
    private long sumSquares;

    public StatVar() {
        this.count = 0;
        this.sum = 0;
        this.sumSquares = 0L;
    }
    public void addObservation(int value) {
        this.count += 1;
        this.sum += value;
        this.sumSquares += (long) value * value;
    }

    public double mean() {
        return (double) sum / count;
    }

    public void pack(byte[] record, int offset) {
        //TODO
    }
    public static StatVar unpack(byte[] record, int offset) {
        //TODO
        return null;
    }
}
