import acm.graphics.GDimension;
import acm.graphics.GPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeometryTest {

    Geometry geometry = new Geometry(
            800,
            600,
            8,
            6,
            0.05,
            0.10);

    @Test
    void getters() {
        assertEquals(8, geometry.getColumns());
        assertEquals(6, geometry.getRows());
    }

    @Test
    void board_dimension() {
        assertEquals(
                new GDimension(720.0, 540.0),
                geometry.boardDimension());
    }

    @Test
    void board_top_left() {
        assertEquals(
                new GPoint(40.0, 30.0),
                geometry.boardTopLeft());
    }

    @Test
    void cell_dimension() {
        assertEquals(
                new GDimension(90.0, 90.0),
                geometry.cellDimension());
    }

    @Test
    void cell_top_left() {
        assertEquals(
                new GPoint(220.0, 120.0),
                geometry.cellTopLeft(2, 1));
    }

    @Test
    void token_dimension() {
        assertEquals(
                new GDimension(72.0, 72.0),
                geometry.tokenDimension());
    }

    @Test
    void token_top_left() {
        assertEquals(
                new GPoint(229, 129),
                geometry.tokenTopLeft(2, 1));
    }

    @Test
    void xy_to_cell() {
        assertEquals(
                new Position(2, 0),
                geometry.xyToCell(300.0, 110.0));
    }

    @Test
    void center_at() {
        assertEquals(
                new GPoint(175, 255.0),
                geometry.centerAt(1, 2));
    }
}