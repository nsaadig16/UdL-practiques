package Examen_20_21.ex1;

import utils.PackUtils;

public class Record {
    public static final int GRID_SIZE = 500;
    public static final int SIZE = 4 + 4 + Duration.SIZE + 8;

    private final int x; // 0 <= x < GRID_SIZE
    private final int y; // 0 <= y < GRID_SIZE
    private Duration totalDuration;
    private double totalLiters; // 0.0 <= totalLiters

    public Record(int x, int y) {
        // Precondición: 0 <= x < GRID_SIZE && 0 <= y < GRID_SIZE
        this.x = x; this.y = y;
        this.totalDuration = new Duration(0, 0);
        this.totalLiters = 0.0;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void addRainEvent(Duration duration, double liters) {
        this.totalDuration = Duration.add(this.totalDuration,duration);
        this.totalLiters += liters;
    }
    public byte[] toBytes() {
        //TODO
        return null;
    }
    public static Record fromBytes(byte[] record) {
        int offset = 0;
        int x = PackUtils.unpackInt(record,offset);
        offset += 4;
        int y = PackUtils.unpackInt(record,offset);
        offset += 4;
        Record newRecord = new Record(x,y);
        Duration duration = Duration.unpackDuration(record,offset);
        offset += Duration.SIZE;
        double totalLiters = PackUtils.unpackDouble(record,offset);
        newRecord.addRainEvent(duration,totalLiters);
        return newRecord;
    }
}
