import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class InvalidFromTest {

    @Test
    void white_is_not_valid_move_NE_and_NW_1() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "·b· ·b· \n" +
                " ·b·b· ·\n" +
                "· ·w· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · ·w· ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(1, 4);
        assertFalse(game.isValidFrom(from));
    }

    @Test
    void white_is_not_valid_move_NE_and_NW_2() {
        String BOARD = "" +
                "· · · · \n" +
                " ·b· · ·\n" +
                "·b· · · \n" +
                "w· · · ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · ·w· ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(0, 3);
        assertFalse(game.isValidFrom(from));
    }

    @Test
    void white_is_not_valid_move_NE_and_NW_3() {
        String BOARD = "" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · ·b· \n" +
                " · · ·b·\n" +
                "· · · ·w\n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · ·w· ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.WHITE);
        Position from = new Position(7, 4);
        assertFalse(game.isValidFrom(from));
    }

    @Test
    void black_is_not_valid_move_SE_and_SW_1() {
        String BOARD = "" +
                "· ·b· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · ·b· ·\n" +
                "· ·w·w· \n" +
                " ·w· ·w·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(4, 3);
        assertFalse(game.isValidFrom(from));
    }

    @Test
    void black_is_not_valid_move_SE_and_SW_2() {
        String BOARD = "" +
                "· ·b· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                "b· · · ·\n" +
                "·w· · · \n" +
                " ·w· · ·\n" +
                "· · · · \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(0, 3);
        assertFalse(game.isValidFrom(from));
    }

    @Test
    void black_is_not_valid_move_SE_and_SW_3() {
        String BOARD = "" +
                "· ·b· · \n" +
                " · · · ·\n" +
                "· · · · \n" +
                " · · · ·\n" +
                "· · · ·b\n" +
                " · · ·w·\n" +
                "· · ·w· \n" +
                " · · · ·";
        Board board = new Board(8, 8, BOARD);
        Game game = new Game(board);
        game.setPlayerForTest(Player.BLACK);
        Position from = new Position(7, 4);
        assertFalse(game.isValidFrom(from));
    }
}