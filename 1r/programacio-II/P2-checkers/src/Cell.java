public class Cell {

    private static final char C_FORBIDDEN = '·';
    private static final char C_EMPTY = ' ';
    private static final char C_WHITE = 'w';
    private static final char C_BLACK = 'b';

    public static final Cell FORBIDDEN = new Cell(C_FORBIDDEN);
    public static final Cell EMPTY = new Cell(C_EMPTY);
    public static final Cell WHITE = new Cell(C_WHITE);
    public static final Cell BLACK = new Cell(C_BLACK);

    private final char status;

    private Cell(char status) {
        this.status = status;
    }

    public static Cell fromChar(char status) {
        if (status == C_FORBIDDEN) {
            return FORBIDDEN;
        } else if (status == C_EMPTY) {
            return EMPTY;
        } else if (status == C_WHITE) {
            return WHITE;
        } else if (status == C_BLACK) {
            return BLACK;
        }
        return null;
    }

    public boolean isForbidden() {
        return this == FORBIDDEN;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }
}