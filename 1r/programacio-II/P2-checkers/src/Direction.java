public class Direction {

    public static final Direction NW = new Direction(-1,-1);
    public static final Direction NE = new Direction(1,-1);
    public static final Direction SW = new Direction(-1,1);
    public static final Direction SE = new Direction(1,1);

    private final int dx;
    private final int dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Position apply(Position from) {
        return new Position(from.getX() + this.dx, from.getY() + this.dy);
    }
}
