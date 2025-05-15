import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FirstWhiteMoveGameTest {

    private static final String BOARD = "" +
            "·b·b·b·b\n" +
            "b·b·b·b·\n" +
            "·b·b·b·b\n" +
            " · · · ·\n" +
            "· · · · \n" +
            "w·w·w·w·\n" +
            "·w·w·w·w\n" +
            "w·w·w·w·";

    private Board board;
    private Game game;

    @BeforeEach
    void setUp() {
        board = new Board(8, 8, BOARD);
        game = new Game(board);
    }

    @Test
    void first_white_move1() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(0, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(1, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void first_white_move2() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(2, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(1, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void first_white_move3() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(2, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(3, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void first_white_move4() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(4, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(3, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void first_white_move5() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(4, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(5, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void first_white_move6() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(6, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(5, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void first_white_move7() {
        assertEquals(Player.WHITE, game.getCurrentPlayer());
        Position from = new Position(6, 5);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(7, 4);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isWhite(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }
}
