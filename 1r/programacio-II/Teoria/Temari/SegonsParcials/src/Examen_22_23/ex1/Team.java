package Examen_22_23.ex1;

import utils.PackUtils;

public class Team {
    private static final int POINTS_FOR_WIN = 3; // puntos por ganar
    private static final int POINTS_FOR_DRAW = 1; // puntos por empate
    private static final int NAME_LIMIT = 20;
    public static final int SIZE = 8 + 2 * NAME_LIMIT + 4 + StatVar.SIZE + StatVar.SIZE;

    private long id;
    private String name;
    private int points;
    private StatVar goalsScored; // goles que ha marcado
    private StatVar goalsConceded; // goles que han recibido

    public Team(long id, String name) {
        this.id = id;
        this.name = name;
        this.points = 0;
        this.goalsScored = new StatVar();
        this.goalsConceded = new StatVar();
    }

    private Team ( long id, String name, int points, StatVar goalsScored, StatVar goalsConceded){
        this.id = id;
        this.name = name;
        this.points = points;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
    }

    public byte[] toBytes() {
        //TODO
        return null;
    }

    public static Team fromBytes(byte[] record) {
        int offset = 0;
        long id = PackUtils.unpackLong(record,offset);
        offset += 8;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        int points = PackUtils.unpackInt(record,offset);
        offset += 4;
        StatVar goalsScored = StatVar.unpack(record,offset);
        offset += StatVar.SIZE;
        StatVar goalsConceded = StatVar.unpack(record,offset);
        return new Team(id,name,points,goalsScored,goalsConceded);
    }

    public void addGameResult(int scored, int conceded) {
        this.goalsConceded.addObservation(scored);
        this.goalsConceded.addObservation(conceded);
        if(scored > conceded){
            this.points += POINTS_FOR_WIN;
        } else if (scored == conceded) {
            this.points += POINTS_FOR_DRAW;
        }
    }

    public long getId() {
        return this.id;
    }
}
