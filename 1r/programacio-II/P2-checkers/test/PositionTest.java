import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void getter_and_constructor() {
        Position pos = new Position(4, 3);
        assertEquals(4, pos.getX());
        assertEquals(3, pos.getY());
    }

    @Test
    void diagonal() {
        Position p00 = new Position(0, 0);
        Position p01 = new Position(0, 1);
        Position p22 = new Position(2, 2);
        Position p12 = new Position(1, 2);
        assertTrue(p00.sameDiagonalAs(p22));
        assertTrue(p22.sameDiagonalAs(p00));
        assertTrue(p01.sameDiagonalAs(p12));
        assertTrue(p12.sameDiagonalAs(p01));
        assertFalse(p00.sameDiagonalAs(p01));
        assertFalse(p01.sameDiagonalAs(p00));
        assertFalse(p12.sameDiagonalAs(p22));
        assertFalse(p22.sameDiagonalAs(p12));
    }

    @Test
    void contra_diagonal() {
        Position p10 = new Position(1, 0);
        Position p01 = new Position(0, 1);
        Position p22 = new Position(2, 2);
        Position p13 = new Position(1, 3);
        assertTrue(p10.sameDiagonalAs(p01));
        assertTrue(p01.sameDiagonalAs(p10));
        assertTrue(p22.sameDiagonalAs(p13));
        assertTrue(p13.sameDiagonalAs(p22));
        assertFalse(p10.sameDiagonalAs(p22));
        assertFalse(p22.sameDiagonalAs(p10));
        assertFalse(p13.sameDiagonalAs(p01));
        assertFalse(p01.sameDiagonalAs(p13));
    }

    @Test
    void distance() {
        Position pos1 = new Position(3, -2);
        Position pos2 = new Position(6, 4);
        assertEquals(9, Position.distance(pos1, pos2));
        assertEquals(9, Position.distance(pos2, pos1));
    }

    @Test
    void middle() {
        Position pos1 = new Position(3, -2);
        Position pos2 = new Position(6, 4);
        assertEquals(new Position(4, 1), Position.middle(pos1, pos2));
        assertEquals(new Position(4, 1), Position.middle(pos2, pos1));
    }
}