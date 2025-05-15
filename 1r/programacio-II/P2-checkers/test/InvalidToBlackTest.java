import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidToBlackTest {
    @Test
    void black_is_not_valid_move_SE_not_empty() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(4, 3);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
        assertTrue(game.isValidFrom(from));
    }

    @Test
    void black_is_not_valid_move_SW_not_empty() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(2, 3);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_left() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(2, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_right() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(4, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_up() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(3, 3);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_down() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(3, 1);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_NW() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(2, 1);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_NE() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(4, 1);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_valid_move_capture_SE() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(5, 4);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
    }

    @Test
    void black_is_valid_move_capture_SW() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(1, 4);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_SE_SE() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " · · · ·\n" +
                "· · ·w· \n" +
                " · · · ·\n" +
                "·w· · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(5, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_SW_SW() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " · · · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "·w· · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(1, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_S_S() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(3, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_N_N() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(3, 0);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_W_W() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_E_E() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·w·w· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(3, 2);
        Position to = new Position(5, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_capture_SW() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " · ·b· ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· ·w· ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(4, 3);
        Position to = new Position(2, 1);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void black_is_not_valid_move_capture_SE() {
        String BOARD = "" +
                "·b· · · \n" +
                " · · · ·\n" +
                "· ·b· · \n" +
                " ·b· · ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· ·w· ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(2, 3);
        Position to = new Position(4, 1);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

}