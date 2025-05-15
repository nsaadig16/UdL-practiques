import acm.graphics.*;

import java.awt.*;
import java.util.StringTokenizer;

public class Display {

    private final String board;
    private final GCanvas gCanvas;
    private final Geometry geometry;

    public Display(Geometry geometry, String board, GCanvas gCanvas) {
        this.board = board;
        this.gCanvas = gCanvas;
        this.geometry = geometry;
    }

    public static String getStatusMessage(Player player, boolean hasWon) {
        if (hasWon) {
            return String.format("Player %s has won", player);
        } else {
            return String.format("%s's turn", player);
        }
    }

    public void initializeDisplay() {
        gCanvas.setBackground(Palette.BACKGROUND);
        // We assume the string corresponds to a valid map of given dimensions
        StringTokenizer st = new StringTokenizer(board, "\n");
        for (int y = 0; y < geometry.getRows(); y++) {
            initializeRow(y, st.nextToken());
        }
    }

    private void initializeRow(int y, String row) {
        for (int x = 0; x < geometry.getColumns(); x++) {
            initializePosition(row.charAt(x), new Position(x, y));
        }
    }

    private void initializePosition(char status, Position pos) {
        if (status == 'w') {
            paintPlayable(pos);
            paintWhite(pos);
        } else if (status == 'b') {
            paintPlayable(pos);
            paintBlack(pos);
        } else if (status == ' ') {
            paintPlayable(pos);
        } else if (status == '·') {
            paintForbidden(pos);
        }
    }

    private void paintForbidden(Position pos) {
        GRect rect = createRect(pos);
        rect.setColor(Palette.FORBIDDEN);
        gCanvas.add(rect);
    }

    private void paintPlayable(Position pos) {
        GRect rect = createRect(pos);
        rect.setColor(Palette.PLAYABLE);
        gCanvas.add(rect);
    }

    private void paintWhite(Position pos) {
        GOval oval = createOval(pos);
        oval.setColor(Palette.WHITE);
        gCanvas.add(oval);
    }

    private void paintBlack(Position pos) {
        GOval oval = createOval(pos);
        oval.setColor(Palette.BLACK);
        gCanvas.add(oval);
    }

    private GRect createRect(Position pos) {
        GPoint topLeft = geometry.cellTopLeft(pos.getX(), pos.getY());
        GDimension dimension = geometry.cellDimension();
        GRect rect = createRect(topLeft, dimension);
        rect.setFilled(true);
        return rect;
    }

    private GRect createRect(GPoint topLeft, GDimension dimension) {
        return new GRect(
                topLeft.getX(),
                topLeft.getY(),
                dimension.getWidth(),
                dimension.getHeight());
    }

    private GOval createOval(Position pos) {
        GPoint topLeft = geometry.tokenTopLeft(pos.getX(), pos.getY());
        GDimension dimension = geometry.tokenDimension();
        GOval oval = createOval(topLeft, dimension);
        oval.setFilled(true);
        return oval;
    }

    private GOval createOval(GPoint topLeft, GDimension dimension) {
        return new GOval(
                topLeft.getX(),
                topLeft.getY(),
                dimension.getWidth(),
                dimension.getHeight());
    }

    public void highlight(Position position) {
        GPoint center = geometry.centerAt(position.getX(), position.getY());
        GObject gObject = gCanvas.getElementAt(center);
        if (!(gObject instanceof GRect)) {
            gObject.sendToBack();
            gObject = gCanvas.getElementAt(center);
            gObject.sendToBack();
        }
        gObject.setColor(Palette.HIGHLIGHTED_PLAYABLE);

    }

    public void unHighlight(Position position) {
        GPoint center = geometry.centerAt(position.getX(), position.getY());
        GObject gObject = gCanvas.getElementAt(center);
        if (!(gObject instanceof GRect)) {
            gObject.sendToBack();
            gObject = gCanvas.getElementAt(center);
            gObject.sendToBack();
        }
        gObject.setColor(Palette.PLAYABLE);
    }

    public void select(Position position) {
        GPoint center = geometry.centerAt(position.getX(), position.getY());
        GObject gObject = gCanvas.getElementAt(center);
        if (!(gObject instanceof GRect)) {
            gObject.sendToBack();
            gObject = gCanvas.getElementAt(center);
            gObject.sendToBack();
        }
        gObject.setColor(Palette.SELECTED_PLAYABLE);
    }

    public void unSelect(Position position) {
        GPoint center = geometry.centerAt(position.getX(), position.getY());
        GObject gObject = gCanvas.getElementAt(center);
        if (!(gObject instanceof GRect)) {
            gObject.sendToBack();
            gObject = gCanvas.getElementAt(center);
            gObject.sendToBack();
        }
        gObject.setColor(Palette.PLAYABLE);
    }

    public void move(Position from, Position to) {
        GPoint center = geometry.centerAt(from.getX(), from.getY());
        GObject gObject = gCanvas.getElementAt(center);
        assert gObject instanceof GOval;
        GPoint newTopLeft = geometry.tokenTopLeft(to.getX(), to.getY());
        gObject.setLocation(newTopLeft);
        // I don't understand why I've needed to do this
        gCanvas.remove(gObject);
        gCanvas.add(gObject);
    }

    public void clear(Position middle) {
        GPoint center = geometry.centerAt(middle.getX(), middle.getY());
        GObject gObject = gCanvas.getElementAt(center);
        gCanvas.remove(gObject);
    }
}

