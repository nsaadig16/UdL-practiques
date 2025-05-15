public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
         return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean sameDiagonalAs(Position other) {
        return  this.x + this.y == other.x + other.y ||
                this.x - this.y == other.x - other.y
        ;
    }

    public static int distance(Position pos1, Position pos2) {
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y);
    }

    public static Position middle(Position pos1, Position pos2) {
        if(distance(pos1,pos2) == 2){
            return null;  //if there is no middle position, we return null
        }
        return new Position((pos1.x + pos2.x) / 2 , (pos1.y + pos2.y) / 2 );
    }

    // Needed for testing and debugging

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

