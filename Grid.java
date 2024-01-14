import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Grid extends Rect implements Settings{

    

    private Cell[][] cells = new Cell[GRID_WIDTH][GRID_HEIGHT];
    private ArrayList<Ship> ships;
    private boolean areShipsVisible;
    private boolean shipsDestroyed;
    private int boardType;

    public Grid(Position position, int boardType) {
        super(position, GRID_WIDTH * TILE_W, GRID_HEIGHT * TILE_H);
        ships = new ArrayList<>();
        this.boardType = boardType;
        
        if (boardType == SEA) {
            areShipsVisible = true;
        }
        else if (boardType == RADAR) {
            areShipsVisible = false;
        }

        initGrid();
    }

    private void initGrid() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                cells[x][y] = new Cell(position.x + x * TILE_W, position.y + y * TILE_H,
                 TILE_W, TILE_H, boardType);
            }
        }
    }

    public void reset() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                cells[x][y].reset();
            }
        }
        ships.clear();
        shipsDestroyed = false;
    }

    public Position getGridPosition(Position pixelPosition) {
        Position gridPos = new Position((pixelPosition.x - position.x) / TILE_W,
         (pixelPosition.y - position.y) / TILE_H);

        return gridPos;
    }

    public void getShot(Position targetGridPosition) {
        cells[targetGridPosition.x][targetGridPosition.y].getShot();
    }

    public void paint(Graphics g) {
        for (int ship_index = 0; ship_index < ships.size(); ship_index++) {
            if (areShipsVisible) {
                ships.get(ship_index).paint(g);
            }
        }
        paintGrid(g);
        paintCells(g);
    }
    
    private void paintGrid(Graphics g) {
        g.setColor(Color.BLACK);

        for (int x = 0; x <= GRID_WIDTH; x++) {
            g.drawLine(position.x + x * TILE_W, position.y,
             position.x + x * TILE_W, position.y + height);
        }

        for (int y = 0; y <= GRID_HEIGHT; y++) {
            g.drawLine(position.x, position.y + y * TILE_H,
             position.x + width, position.y + y * TILE_H);
        }
    }

    private void paintCells(Graphics g) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                cells[x][y].paint(g);
            }
        }

    }
}