import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitialPositionGameTest {

    private static final String BOARD = "" +
            "·b·b·b·b\n" +
            "b·b·b·b·\n" +
            "·b·b·b·b\n" +
            " · · · ·\n" +
            "· · · · \n" +
            "w·w·w·w·\n" +
            "·w·w·w·w\n" +
            "w·w·w·w·";

    private Board board = new Board(8, 8, BOARD);
    private Game game = new Game(board);

    @Test
    void initial_player_is_white() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void valid_from_initial_position_for_white() {
        for (int x = 0; x < 8; x += 2) {
            assertTrue(board.isWhite(new Position(x, 5)));
            assertTrue(game.isValidFrom(new Position(x, 5)));
        }
    }

    @Test
    void invalid_from_initial_position_for_white() {
        for (int x = 0; x < 8; x += 2) {
            for (int y = 6; y <= 7; y += 1) {
                int xx = x + (1 - y % 2);
                assertTrue(board.isWhite(new Position(xx, y)));
                assertFalse(game.isValidFrom(new Position(xx, y)));
            }
        }
    }

    @Test
    void invalid_from_initial_position_for_black() {
        for (int x = 0; x < 8; x += 2) {
            for (int y = 0; y <= 2; y += 1) {
                int xx = x + (1 - y % 2);
                assertTrue(board.isBlack(new Position(xx, y)));
                assertFalse(game.isValidFrom(new Position(xx, y)));
            }
        }
    }

    @Test
    void invalid_from_initial_for_empty() {
        for (int x = 0; x < 8; x += 2) {
            for (int y = 3; y <= 4; y += 1) {
                int xx = x + (1 - y % 2);
                assertTrue(board.isEmpty(new Position(xx, y)));
                assertFalse(game.isValidFrom(new Position(xx, y)));
            }
        }
    }

    @Test
    void invalid_for_forbidden() {
        for (int x = 0; x < 8; x += 2) {
            for (int y = 0; y < 8; y += 1) {
                int xx = x + y % 2;
                assertTrue(board.isForbidden(new Position(xx, y)));
                assertFalse(game.isValidFrom(new Position(xx, y)));
            }
        }
    }
}