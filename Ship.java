import java.awt.Color;
import java.awt.Graphics;

public class Ship implements Settings{

    private Position gridPosition;
    private int size;
    private boolean isVertical = true;
    private int health;
    private int shipPlacement;
    private int boardType;
    private int topGridOffset;

    public Ship(Position gridPosition, int size, boolean isVertical, int boardType) {
        this.gridPosition = gridPosition;
        this.size = size;
        this.isVertical = isVertical;
        this.health = size;
        this.boardType = boardType;
        getGridTopOffset();
    }

    public Ship(Ship otherShip) {
        this.gridPosition = otherShip.gridPosition;
        this.size = otherShip.size;
        this.isVertical = otherShip.isVertical;
        this.health = size;
        this.boardType = otherShip.boardType;
        getGridTopOffset();
    }

    private void getGridTopOffset() {
        if (boardType == RADAR)
            topGridOffset = 0;
        else
            topGridOffset = GRID_HEIGHT;
    }
    public void setBoardType(int type) {
        boardType = type;
    }

    public void getHit() {
        health--;
        if (health <= 0) {
            shipPlacement = PLACED_DEAD;
        }
            
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setPosition(Position newGridPosition) {
        gridPosition = newGridPosition;
    }

    public Position getPosition() {
        return gridPosition;
    }

    public void turn() {
        isVertical = !isVertical;
    }

    public void setShipPlacementType(int type) {
        shipPlacement = type;
    }

    public int getShipPlacementType() {
        return shipPlacement;
    }

    public void paint(Graphics g) {
        switch (shipPlacement) {
            case PLACED_ALIVE:
                g.setColor(Color.DARK_GRAY);
                break;
            case PLACED_DEAD:
                g.setColor(Color.RED);
                break;
            case RIGHT_PLACEMENT:
                g.setColor(Color.GREEN);
                break;
            case WRONG_PLACEMENT:
                g.setColor(Color.RED);
                break;
            default:
                break;
        }
        if (isVertical) {
            paintVertical(g);
        }
        else {
            paintHorizontal(g);
        }
    }

    private void paintVertical(Graphics g) {
        int shipHeight = (int)(size * TILE_H - TILE_H * 0.4);
        int shipWidth = (int)(TILE_W * 0.8);
        int leftX = (int)((BOARD_OFFSET_X + gridPosition.x + 0.15) * TILE_W);
        int topY = (int)((BOARD_OFFSET_Y * 2 + gridPosition.y + topGridOffset + 0.15) * TILE_H);
        g.fillRect(leftX, topY, shipWidth, shipHeight);
    }

    private void paintHorizontal(Graphics g) {
        int shipWidth = (int)(size * TILE_H - TILE_H * 0.4);
        int shipHeight = (int)(TILE_W * 0.8);
        int leftX = (int)((BOARD_OFFSET_X + gridPosition.x + 0.15) * TILE_W);
        int topY = (int)((BOARD_OFFSET_Y * 2 + gridPosition.y + topGridOffset + 0.15) * TILE_H);
        g.fillRect(leftX, topY, shipWidth, shipHeight);
    }
}
