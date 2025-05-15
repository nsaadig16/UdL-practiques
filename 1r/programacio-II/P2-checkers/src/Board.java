import java.util.StringTokenizer;

// Only a rectangle of cells. Does not know the game rules.

public class Board {

    private final int width;
    private final int height;
    private final Cell[][] cells;
    private int numBlacks;
    private int numWhites;

    public Board(int width, int height, String board) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        StringTokenizer rows = new StringTokenizer(board,"\n");
        for(int y = 0; y < height; y++) {
            String row = rows.nextToken();
            for (int x = 0; x < width; x++) {
                cells[y][x] = Cell.fromChar(row.charAt(x));
                countCell(cells[y][x]);
            }
        }
    }

    private void countCell(Cell cell){  //We add to the counters a white or a black piece
        if (cell.isWhite()) {
            numWhites += 1;
        } else if (cell.isBlack()) {
            numBlacks += 1;
        }
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getNumBlacks() {
        return this.numBlacks;
    }

    public int getNumWhites() {
        return this.numWhites;
    }

    public boolean isForbidden(Position pos) {
        return  !isInRange(pos) ||
                cells[pos.getY()][pos.getX()].isForbidden()
        ;
    }

    private boolean isInRange(Position position){
        return  position.getX() < this.width  &&
                position.getY() < this.height &&
                position.getX() >= 0 &&
                position.getY() >= 0
        ;
    }

    public boolean isBlack(Position pos) {
        return  isInRange(pos) &&
                cells[pos.getY()][pos.getX()].isBlack()
        ;
    }

    public boolean isWhite(Position pos) {
        return  isInRange(pos) &&
                cells[pos.getY()][pos.getX()].isWhite()
        ;
    }

    public boolean isEmpty(Position pos) {
        return  isInRange(pos) &&
                cells[pos.getY()][pos.getX()].isEmpty()
        ;
    }

    public void setBlack(Position pos) {
        this.numBlacks += 1;
        cells[pos.getY()][pos.getX()] = Cell.BLACK;
    }

    public void setWhite(Position pos) {
        this.numWhites += 1;
        cells[pos.getY()][pos.getX()] = Cell.WHITE;
    }

    public void setEmpty(Position pos) {    //We remove a piece, so we decrease the counter
        if(isWhite(pos)){
            this.numWhites -= 1;
        }else if(isBlack(pos)){
            this.numBlacks -= 1;
        }
        cells[pos.getY()][pos.getX()] = Cell.EMPTY;
    }

    // For testing and debugging

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(cells[y][x].toString());
            }
            if (y != height - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
