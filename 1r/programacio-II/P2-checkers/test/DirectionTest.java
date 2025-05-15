import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void nw() {
        Position from = new Position(4, 3);
        assertEquals(new Position(3, 2), Direction.NW.apply(from));
    }

    @Test
    void ne() {
        Position from = new Position(4, 3);
        assertEquals(new Position(5, 2), Direction.NE.apply(from));
    }

    @Test
    void sw() {
        Position from = new Position(4, 3);
        assertEquals(new Position(3, 4), Direction.SW.apply(from));
    }

    @Test
    void se() {
        Position from = new Position(4, 3);
        assertEquals(new Position(5, 4), Direction.SE.apply(from));
    }

}