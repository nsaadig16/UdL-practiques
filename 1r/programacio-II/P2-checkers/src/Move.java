import java.util.Objects;

public class Move {

    // Assumes it is a valid move !!!

    private final Position from;
    private final Position middle;
    private final Position to;

    public Move(Position from, Position middle, Position to) {
        this.from = from;
        this.middle = middle;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getMiddle() {
        return middle;
    }

    public Position getTo() {
        return to;
    }

    public boolean isCapture() {
        return middle != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return from.equals(move.from) &&
                Objects.equals(middle, move.middle) &&
                to.equals(move.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, middle, to);
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", middle=" + middle +
                ", to=" + to +
                '}';
    }
}
