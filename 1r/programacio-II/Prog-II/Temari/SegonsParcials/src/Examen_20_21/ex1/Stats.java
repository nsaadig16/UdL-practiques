package Examen_20_21.ex1;

import java.io.*;

public class Stats {

    private final RandomAccessFile raf;

    public Stats(String name) throws IOException {
        this.raf = new RandomAccessFile(name, "rw");
    }

    public Record read(int x, int y) throws IOException {
        byte[] record = new byte[Record.SIZE];
        long posInFile = ((long)x * Record.GRID_SIZE + y) * Record.SIZE;
        this.raf.seek(posInFile);
        this.raf.read(record);
        return Record.fromBytes(record);
    }

    public void write(Record record)throws IOException {
       //TODO
    }

    public void close() throws IOException {
        this.raf.close();
    }

}
