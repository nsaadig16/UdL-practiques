package Examen_18_19;

import utils.PackUtils;

public class Pilot {

    private static final int NAME_LIMIT = 30;
    public static final int SIZE = 4 + 2 * NAME_LIMIT + 4 + 4;
    private int id; // identificador
    private String name; // nombre
    private int points; // puntos en el campeonato
    private int finished; // carreras finalizadas

    public Pilot(int id, String name, int points, int finished) {
        this.id = id; this.name = name;
        this.points = points; this.finished = finished;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getPoints(){
        return this.points;
    }

    public int getFinished () {
        return this.finished;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void setFinished(int finished){
        this.finished = finished;
    }

    public byte[] toBytes() {
        byte[] record = new byte[SIZE];
        int offset = 0;
        PackUtils.packInt(this.id,record,offset);
        offset += 4;
        PackUtils.packLimitedString(this.name,NAME_LIMIT,record,offset);
        PackUtils.packInt(this.points,record,offset);
        offset += 4;
        PackUtils.packInt(this.finished,record,offset);
        return record;
    }

    public static Pilot fromBytes(byte[] record) {
        int offset = 0;
        int id = PackUtils.unpackInt(record,offset);
        offset +=4;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,record,offset);
        int points = PackUtils.unpackInt(record,offset);
        offset +=4;
        int finished = PackUtils.unpackInt(record,offset);
        return new Pilot(id,name,points,finished);
    }

}
