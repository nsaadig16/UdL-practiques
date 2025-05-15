import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidToWhiteTest {
    @Test
    void white_is_not_valid_move_NE_not_empty() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(2, 3);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_NW_not_empty() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(0, 3);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_left() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(0, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_right() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(2, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_up() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(1, 3);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_down() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_SW() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(0, 5);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_SE() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                "b·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(2, 5);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_valid_move_capture_NE() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " ·b· · ·\n" +
                "·w· · · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        Position to = new Position(3, 2);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
    }

    @Test
    void white_is_valid_move_capture_NW() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " ·b· · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        assertTrue(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_NE_NE() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(5, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_NW_NW() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_N_N() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(3, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_S_S() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(3, 5);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_W_W() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(1, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_E_E() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(5, 4);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_capture_NW() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " ·w· · ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

    @Test
    void white_is_not_valid_move_capture_NE() {
        String BOARD = "" +
                "· · · · \n" +
                " ·b· · ·\n" +
                "· · · · \n" +
                " · ·w· ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · ·w\n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(3, 4);
        Position to = new Position(5, 2);
        assertTrue(game.isValidFrom(from));
        assertFalse(game.isValidTo(from, to));
    }

}