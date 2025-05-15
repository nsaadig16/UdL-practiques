import acm.program.GraphicsProgram;
import acm.program.ProgramMenuBar;

import java.awt.event.MouseEvent;

public class Checkers extends GraphicsProgram {

    public static final int APPLICATION_WIDTH = 600;
    public static final int APPLICATION_HEIGHT = 600;

    private static final double OUTER = 0.03;
    private static final double INNER = 0.10;

    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private static final String BOARD = "" +
            "·b·b·b·b\n" +
            "b·b·b·b·\n" +
            "·b·b·b·b\n" +
            " · · · ·\n" +
            "· · · · \n" +
            "w·w·w·w·\n" +
            "·w·w·w·w\n" +
            "w·w·w·w·";

    private Geometry geometry;
    private Display display;
    private Game game;

    private Position selectedFrom;
    private Position highlighted;

    public static void main(String[] args) {
        new Checkers().start(args);
    }

    @Override
    public void run() {
        addMouseListeners();
        runGame();
    }

    private void runGame() {
        geometry = new Geometry(APPLICATION_WIDTH, APPLICATION_HEIGHT, COLUMNS, ROWS, OUTER, INNER);
        display = new Display(geometry, BOARD, getGCanvas());
        Board board = new Board(COLUMNS, ROWS, BOARD);
        game = new Game(board);
        display.initializeDisplay();
        updateTitle();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (game.hasWon()) return;
        Position position = geometry.xyToCell(e.getX(), e.getY());
        if (position.equals(selectedFrom)) return;
        if (selectedFrom != null && game.isValidTo(selectedFrom, position)) {
            display.highlight(position);
            highlighted = position;
        } else if (selectedFrom == null && game.isValidFrom(position)) {
            display.highlight(position);
            highlighted = position;
        } else if (highlighted != null) {
            display.unHighlight(highlighted);
            highlighted = null;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (game.hasWon()) return;
        Position position = geometry.xyToCell(e.getX(), e.getY());
        if (selectedFrom == null && game.isValidFrom(position)) {
            display.unHighlight(position);
            highlighted = null;
            display.select(position);
            selectedFrom = position;
        } else if (position.equals(selectedFrom)) {
            display.unSelect(position);
            selectedFrom = null;
            display.highlight(position);
            highlighted = position;
        } else if (selectedFrom != null && game.isValidTo(selectedFrom, position)) {
            Move move = game.move(selectedFrom, position);
            display.unSelect(selectedFrom);
            selectedFrom = null;
            showMove(move);
            updateTitle();
        }
    }

    private void showMove(Move move) {
        display.move(move.getFrom(), move.getTo());
        if (move.getMiddle() != null)
            display.clear(move.getMiddle());

    }

    private void updateTitle() {
        setTitle(Display.getStatusMessage(game.getCurrentPlayer(), game.hasWon()));
    }

    @Override
    protected ProgramMenuBar createMenuBar() {
        // To remove the menu bar on windows
        return null;
    }
}
