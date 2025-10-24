package Examen_18_19;
import java.io.*;

public class PilotFile {

    private RandomAccessFile raf;

    public PilotFile(String fname) throws IOException {
        this.raf = new RandomAccessFile(fname, "rw");
    }

    public void close() throws IOException { this.raf.close(); }

    public Pilot read(int id) throws IOException {
        byte[] record = new byte[Pilot.SIZE];
        this.raf.seek((long) (id - 1) * Pilot.SIZE);
        this.raf.read(record);
        return Pilot.fromBytes(record);
    }

    public void write(Pilot pilot) throws IOException {
        byte[] record = pilot.toBytes();
        this.raf.seek((long) (pilot.getId() - 1) * Pilot.SIZE);
        this.raf.write(record);
    }

    public boolean contains(int id) throws IOException {
        return 1 <= id && id <= (this.raf.length() / Pilot.SIZE);
    }

}
