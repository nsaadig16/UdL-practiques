import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void forbidden_cell() {
        Cell forbidden = Cell.FORBIDDEN;
        assertTrue(forbidden.isForbidden());
        assertFalse(forbidden.isEmpty());
        assertFalse(forbidden.isBlack());
        assertFalse(forbidden.isWhite());
    }

    @Test
    void empty_cell() {
        Cell empty = Cell.EMPTY;
        assertTrue(empty.isEmpty());
        assertFalse(empty.isForbidden());
        assertFalse(empty.isBlack());
        assertFalse(empty.isWhite());
    }

    @Test
    void crossed_cell() {
        Cell cross = Cell.BLACK;
        assertTrue(cross.isBlack());
        assertFalse(cross.isWhite());
        assertFalse(cross.isForbidden());
        assertFalse(cross.isEmpty());
    }

    @Test
    void nought_cell() {
        Cell nought = Cell.WHITE;
        assertTrue(nought.isWhite());
        assertFalse(nought.isBlack());
        assertFalse(nought.isForbidden());
        assertFalse(nought.isEmpty());
    }

    @Test
    void cell_from_char() {
        Cell forbidden = Cell.fromChar('·');
        assertTrue(forbidden.isForbidden());
        Cell empty = Cell.fromChar(' ');
        assertTrue(empty.isEmpty());
        Cell cross = Cell.fromChar('b');
        assertTrue(cross.isBlack());
        Cell nought = Cell.fromChar('w');
        assertTrue(nought.isWhite());
        Cell invalid = Cell.fromChar('?');
        assertNull(invalid);
    }

    @Test
    void cell_to_string() {
        assertEquals("·", Cell.FORBIDDEN.toString());
        assertEquals("w", Cell.WHITE.toString());
        assertEquals("b", Cell.BLACK.toString());
        assertEquals(" ", Cell.EMPTY.toString());
    }
}