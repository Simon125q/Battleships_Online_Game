public class Position {
    public static final Position UP = new Position(0, 1);
    public static final Position DOWN = new Position(0, 1);
    public static final Position RIGHT = new Position(0, 1);
    public static final Position LEFT = new Position(0, 1);

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position positionToCopy) {
        this.x = positionToCopy.x;
        this.y = positionToCopy.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
