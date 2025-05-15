import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CapturesGameTest {

    @Test
    void white_captures_nw() {
        String BOARD = "" +
                "·b·b·b·b\n" +
                "b·b·b·b·\n" +
                "· ·b· ·b\n" +
                " ·b· ·b·\n" +
                "·w·w· · \n" +
                " ·w· ·w·\n" +
                "·w·w·w·w\n" +
                "w·w·w·w·";
        Board board = new Board(8, 8, BOARD);
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position middle = new Position(2, 3);
        Position to = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
        Move expected = new Move(from, middle, to);
        Move actual = game.move(from, to);
        assertEquals(expected, actual);
        assertTrue(actual.isCapture());
        assertTrue(board.isWhite(to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isEmpty(middle));
        assertEquals(11, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
    }

    @Test
    void black_captures_sw() {
        String BOARD = "" +
                "·b·b·b·b\n" +
                "b·b·b·b·\n" +
                "·w·b· ·b\n" +
                " · · ·b·\n" +
                "·w· · · \n" +
                " ·w· ·w·\n" +
                "·w·w·w·w\n" +
                "w·w·w·w·";
        Board board = new Board(8, 8, BOARD);
        assertEquals(11, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(2, 1);
        Position middle = new Position(1, 2);
        Position to = new Position(0, 3);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
        Move expected = new Move(from, middle, to);
        Move actual = game.move(from, to);
        assertEquals(expected, actual);
        assertTrue(actual.isCapture());
        assertTrue(board.isBlack(to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isEmpty(middle));
        assertEquals(11, board.getNumBlacks());
        assertEquals(11, board.getNumWhites());
    }

    @Test
    void white_captures_ne() {
        String BOARD = "" +
                "·b·b·b·b\n" +
                "b·b·b·b·\n" +
                "· · · ·b\n" +
                " ·b·b·b·\n" +
                "·w·w· ·w\n" +
                " ·w· · ·\n" +
                "·w·w·w·w\n" +
                "w·w·w·w·";
        Board board = new Board(8, 8, BOARD);
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position middle = new Position(2, 3);
        Position to = new Position(3, 2);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
        Move expected = new Move(from, middle, to);
        Move actual = game.move(from, to);
        assertEquals(expected, actual);
        assertTrue(actual.isCapture());
        assertTrue(board.isWhite(to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isEmpty(middle));
        assertEquals(11, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
    }

    @Test
    void black_captures_se() {
        String BOARD = "" +
                "·b·b·b·b\n" +
                "b·b·b·b·\n" +
                "·w· · ·b\n" +
                " ·b· ·b·\n" +
                "·w·w· ·w\n" +
                " · · · ·\n" +
                "·w·w·w·w\n" +
                "w·w·w·w·";
        Board board = new Board(8, 8, BOARD);
        assertEquals(11, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(2, 3);
        Position middle = new Position(3, 4);
        Position to = new Position(4, 5);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
        Move expected = new Move(from, middle, to);
        Move actual = game.move(from, to);
        assertEquals(expected, actual);
        assertTrue(actual.isCapture());
        assertTrue(board.isBlack(to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isEmpty(middle));
        assertEquals(11, board.getNumBlacks());
        assertEquals(11, board.getNumWhites());
    }
}
