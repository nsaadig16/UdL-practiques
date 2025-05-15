import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FirstBlackMoveGameTest {

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
    void white_does_first_move() {
        board = new Board(8, 8, BOARD);
        game = new Game(board);
        game.move(new Position(0, 5), new Position(1, 4));
    }

    @Test
    void first_black_move1() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(0, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void first_black_move2() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(1, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(2, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void first_black_move3() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(3, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(2, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void first_black_move4() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(3, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(4, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void first_black_move5() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(5, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(4, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void first_black_move6() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(5, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(6, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void first_black_move7() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        Position from = new Position(7, 2);
        assertTrue(game.isValidFrom(from));
        Position to = new Position(6, 3);
        assertTrue(game.isValidTo(from, to));
        Move move = new Move(from, null, to);
        assertEquals(move, game.move(from, to));
        assertTrue(board.isEmpty(from));
        assertTrue(board.isBlack(to));
        assertEquals(12, board.getNumBlacks());
        assertEquals(12, board.getNumWhites());
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }
}
