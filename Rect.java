
public class Rect {
    protected Position position;
    protected int width;
    protected int height;

    public Rect(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Rect(int x, int y, int width, int height) {
        this.position = new Position(x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isInside(Position checkPosition) {
        if (position.x <= checkPosition.x && position.x + width >= checkPosition.x &&
            position.y + height >= checkPosition.y && position.y <= checkPosition.y) {
            return true;
        }
        else
            return false;
    }
}
