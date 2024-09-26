package binnie.core.craftgui.geometry;

public enum Position {

    TOP(0, -1),
    BOTTOM(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int x;
    private final int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Position opposite() {
        return switch (this) {
            case BOTTOM -> Position.TOP;
            case LEFT -> Position.RIGHT;
            case RIGHT -> Position.LEFT;
            case TOP -> Position.BOTTOM;
        };
    }
}
