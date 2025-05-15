import acm.graphics.GDimension;
import acm.graphics.GPoint;

public class Geometry {

    private final int windowWidth;
    private final int windowHeight;
    private final int numCols;
    private final int numRows;
    private final double boardPadding;
    private final double cellPadding;

    public Geometry(int windowWidth, int windowHeight, int numCols, int numRows, double boardPadding, double cellPadding) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.numCols = numCols;
        this.numRows = numRows;
        this.boardPadding = boardPadding;
        this.cellPadding = cellPadding;
    }

    public int getRows() {
        return this.numRows;
    }

    public int getColumns() {
        return this.numCols;
    }

    public GDimension boardDimension() {
        double width = windowWidth * (1.0 - boardPadding * 2.0);
        double height = windowHeight * (1.0 - boardPadding * 2.0);
        return new GDimension(width, height);
    }

    public GPoint boardTopLeft() {
        double x = (windowWidth * boardPadding);
        double y = (windowHeight * boardPadding);
        return new GPoint(x, y);
    }

    public GDimension cellDimension() {
        double height = ((boardDimension().getHeight()) / getRows());
        double width = ((boardDimension().getWidth()) / getColumns());
        return new GDimension(width, height);
    }

    public GPoint cellTopLeft(int x, int y) {
        double cellX = boardTopLeft().getX() + (x * cellDimension().getWidth());
        double cellY = boardTopLeft().getY() + (y * cellDimension().getHeight());
        return new GPoint(cellX, cellY);
    }

    public GDimension tokenDimension() {
        double width = cellDimension().getWidth() * (1.0 - cellPadding * 2.0);
        double height = cellDimension().getHeight() * (1.0 - cellPadding * 2.0);
        return new GDimension(width, height);
    }

    public GPoint tokenTopLeft(int x, int y) {
        double tokenX = cellTopLeft(x, y).getX() + (cellPadding * cellDimension().getWidth());
        double tokenY = cellTopLeft(x, y).getY() + (cellPadding * cellDimension().getHeight());
        return new GPoint(tokenX, tokenY);
    }

    public GPoint centerAt(int x, int y) {
        double centerX = cellTopLeft(x, y).getX() + (cellDimension().getWidth() / 2.0);
        double centerY = cellTopLeft(x, y).getY() + (cellDimension().getHeight() / 2.0);
        return new GPoint(centerX, centerY);
    }

    public Position xyToCell(double x, double y) {
        GPoint boardTopLeft = boardTopLeft();
        GDimension cellDimension = cellDimension();
        return new Position(
                (int) ((x - boardTopLeft.getX()) / cellDimension.getWidth()),
                (int) ((y - boardTopLeft.getY()) / cellDimension.getHeight()))
        ;
    }
}
