package Examen_22_23.ex1;

import java.io.*;
public class League {

    private final RandomAccessFile raf;

    public League(String fileName) throws IOException {
        this.raf = new RandomAccessFile(fileName, "rw");
    }

    public Team readTeam(long id) throws IOException {
        byte[] record = new byte[Team.SIZE];
        this.raf.seek( (id - 1L) * Team.SIZE);
        this.raf.read(record);
        return Team.fromBytes(record);
    }
    public boolean exists(long id) throws IOException {
        return 1L <= id && id <= numTeams();
    }
    public void writeTeam(Team team) throws IOException {
        byte[] record = team.toBytes();
        this.raf.seek((team.getId() * Team.SIZE) - 1L);
        this.raf.write(record);
    }
    public int numTeams() throws IOException {
        return (int) this.raf.length() / Team.SIZE;
    }
    public void close() throws IOException {
        this.raf.close();
    }

}
