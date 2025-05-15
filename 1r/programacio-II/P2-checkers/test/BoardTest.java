import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private static final String BOARD = "" +
            "·b·b·b·b\n" +
            "b·b·b·b·\n" +
            "·b·b·b·b\n" +
            " · · · ·\n" +
            "· · · · \n" +
            "w·w·w·w·\n" +
            "·w·w·w·w\n" +
            "w·w·w·w·";

    private final Board board = new Board(8, 8, BOARD);

    private final Position outOfBounds = new Position(4, 9);
    private final Position forbidden = new Position(0, 2);
    private final Position white = new Position(4, 5);
    private final Position black = new Position(6, 1);
    private final Position empty = new Position(2, 3);

    @Test
    void counters() {
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
    }
    @Test
    void is_forbidden() {
        assertTrue(board.isForbidden(outOfBounds));
        assertTrue(board.isForbidden(forbidden));
        assertFalse(board.isForbidden(white));
        assertFalse(board.isForbidden(black));
        assertFalse(board.isForbidden(empty));
    }

    @Test
    void is_black() {
        assertFalse(board.isBlack(outOfBounds));
        assertFalse(board.isBlack(forbidden));
        assertFalse(board.isBlack(white));
        assertTrue(board.isBlack(black));
        assertFalse(board.isBlack(empty));
    }

    @Test
    void is_white() {
        assertFalse(board.isWhite(outOfBounds));
        assertFalse(board.isWhite(forbidden));
        assertTrue(board.isWhite(white));
        assertFalse(board.isWhite(black));
        assertFalse(board.isWhite(empty));
    }

    @Test
    void is_empty() {
        assertFalse(board.isEmpty(outOfBounds));
        assertFalse(board.isEmpty(forbidden));
        assertFalse(board.isEmpty(white));
        assertFalse(board.isEmpty(black));
        assertTrue(board.isEmpty(empty));
    }

    @Test
    void set_black() {
        board.setBlack(empty);
        assertTrue(board.isBlack(empty));
    }

    @Test
    void set_white() {
        board.setWhite(empty);
        assertTrue(board.isWhite(empty));
    }

    @Test
    void empty_position() {
        board.setEmpty(white);
        assertTrue(board.isEmpty(white));
    }

    @Test
    void round_trip() {
        assertEquals(BOARD, board.toString());
    }
}