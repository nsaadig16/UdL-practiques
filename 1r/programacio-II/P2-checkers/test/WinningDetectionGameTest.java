import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WinningDetectionGameTest {

    @Test
    void white_captures_last_black() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " ·b· · ·\n" +
                "·w· · · \n" +
                " ·w· · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(3, 2);
        Move actual = game.move(from, to);
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        assertTrue(game.hasWon());
    }

    @Test
    void white_leaves_black_with_no_moves() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                "b· · · ·\n" +
                "·w· · · \n" +
                " ·w· · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(7, 6);
        Position to = new Position(6, 5);
        Move actual = game.move(from, to);
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        assertTrue(game.hasWon());
    }

    @Test
    void white_moves_to_first_row() {
        String BOARD = "" +
                "· · · · \n" +
                " ·w· · ·\n" +
                "· · · · \n" +
                " ·b· · ·\n" +
                "·w· · · \n" +
                " ·w· · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(2, 1);
        Position to = new Position(3, 0);
        Move actual = game.move(from, to);
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        assertTrue(game.hasWon());
    }

    @Test
    void black_captures_last_white() {
        String BOARD = "" +
                "· · · · \n" +
                " · ·b· ·\n" +
                "· · · · \n" +
                " ·b· · ·\n" +
                "·w· · · \n" +
                " · · ·b·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(2, 3);
        Position to = new Position(0, 5);
        Move actual = game.move(from, to);
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        assertTrue(game.hasWon());
    }

    @Test
    void black_moves_to_first_row() {
        String BOARD = "" +
                "· · · · \n" +
                " ·w· · ·\n" +
                "· · · · \n" +
                " ·b· · ·\n" +
                "·w· · · \n" +
                " ·w· · ·\n" +
                "· ·b· ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 6);
        Position to = new Position(2, 7);
        Move actual = game.move(from, to);
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        assertTrue(game.hasWon());
    }

    @Test
    void black_leaves_white_with_no_moves() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · ·b·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(6, 5);
        Position to = new Position(7, 6);
        Move actual = game.move(from, to);
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        assertTrue(game.hasWon());
    }
}
