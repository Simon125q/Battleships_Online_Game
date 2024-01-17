import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, Settings {
    
    private int gamePhase;
    private Grid radarGrid;
    private Grid playerGrid;
    private Ship shipToPlace;
    private int shipToPlaceIndex;
    private BufferedImage backgroundImage;
    private GameFrame game;

    public GamePanel(GameFrame game) {
        this.game = game;
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        getBackgroundImage();
        addMouseListener(this);
        addMouseMotionListener(this);
        radarGrid = new Grid(new Position(BOARD_OFFSET_X * TILE_W, BOARD_OFFSET_Y * TILE_H), RADAR);
        playerGrid = new Grid(new Position(BOARD_OFFSET_X * TILE_W, (BOARD_OFFSET_Y * 2 + GRID_WIDTH) * TILE_H), SEA);
        reset();
    }

    private void getBackgroundImage() {
        String imagePath = "background.png";
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        radarGrid.reset();
        playerGrid.reset();
        gamePhase = SHIP_PLACING;
        shipToPlace = new Ship(playerGrid.getGridPosition(playerGrid.getPosition()), SHIPS_SIZES[0], true, SEA);
        shipToPlaceIndex = 0;
    }

    public void makeShot(Position targePosition) {
        Position targetGridPos = radarGrid.getGridPosition(targePosition);
        game.lastShot = new Position(targetGridPos);
        game.sendData(true, true, false, targetGridPos.x, targetGridPos.y);
        game.myTurn = false;
    }

    public void checkMyShot(Position targetGridPos, boolean isHit) {
        radarGrid.getMarkedShot(targetGridPos, isHit);
        repaint();
    }

    public boolean opponentFired(int targetX, int targetY) {
        boolean isHit = playerGrid.getShot(new Position(targetX, targetY));
        checkIfStillAlive();
        repaint();
        return isHit;
    }

    private void checkIfStillAlive() {
        if (playerGrid.shipsDestroyed) {
            game.sendData(true, false, false, -1, -1);
            game.displayGameLost();
        }
    }

    private void moveShip(Position mousePosition) {
        if (playerGrid.isInside(mousePosition)) {
            Position gridPos = playerGrid.getGridPosition(mousePosition);
            updateShipPosition(gridPos);
        }
    }

    private void updateShipPosition(Position gridPosition) {
        

        if (playerGrid.canPlaceShipAt(gridPosition, shipToPlace.getSize(), shipToPlace.isVertical())) {
            shipToPlace.setShipPlacementType(RIGHT_PLACEMENT);
            shipToPlace.setPosition(gridPosition);
        }
        else {
            shipToPlace.setShipPlacementType(WRONG_PLACEMENT);
        }
    }

    private void placeShip(Position mousePosition) {
        Position gridPosition = playerGrid.getGridPosition(mousePosition);
        updateShipPosition(gridPosition);
        if (shipToPlace.getShipPlacementType() != RIGHT_PLACEMENT) {
            return;
        }
        shipToPlace.setShipPlacementType(PLACED_ALIVE);
        playerGrid.placeShip(shipToPlace, gridPosition);
        checkIfStillShipsToPlace(gridPosition);
    }

    private void checkIfStillShipsToPlace(Position gridPosition) {
        shipToPlaceIndex++;
        if (shipToPlaceIndex < SHIPS_SIZES.length) {
            shipToPlace = new Ship(gridPosition, SHIPS_SIZES[shipToPlaceIndex], true, SEA);
        }
        else {
            gamePhase = SHOOTING;
            game.sendData(true, true, false, -1, -1);
            //radarGrid.deepCopy(playerGrid);
        }
    }

   

    public void paint(Graphics g) {
        super.paint(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        radarGrid.paint(g);
        playerGrid.paint(g);

        if (gamePhase == SHIP_PLACING) {
            shipToPlace.paint(g);
        }
    }

    public void handleKeyInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
            
            case KeyEvent.VK_R:
                reset();
                break;

            case KeyEvent.VK_SPACE:
                if (gamePhase == SHIP_PLACING) {
                    shipToPlace.turn();
                    repaint();
                }

            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position mousePosition = new Position(e.getX() - 1, e.getY() - 1);
        if (gamePhase == SHIP_PLACING && playerGrid.isInside(mousePosition)) {
            placeShip(mousePosition);
        }
        else if (game.myTurn && gamePhase == SHOOTING && game.opponentPlacedShips && radarGrid.isInside(mousePosition)) {
            makeShot(mousePosition);
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gamePhase == SHIP_PLACING) {
            Position mousePosition = new Position(e.getX(), e.getY());
            moveShip(mousePosition);
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

}
