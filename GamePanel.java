import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, Settings {

    private int gamePhase;
    private Grid radarGrid;
    private Grid playerGrid;
    private Ship shipToPlace;
    private int shipToPlaceIndex;

    public GamePanel() {
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        radarGrid = new Grid(new Position(BOARD_OFFSET_X * TILE_W, BOARD_OFFSET_Y * TILE_H), RADAR);
        playerGrid = new Grid(new Position(BOARD_OFFSET_X * TILE_W, (BOARD_OFFSET_Y * 2 + GRID_WIDTH) * TILE_H), SEA);
        reset();
    }

    private void reset() {
        radarGrid.reset();
        playerGrid.reset();
        gamePhase = SHIP_PLACING;
        shipToPlace = new Ship(playerGrid.getGridPosition(playerGrid.getPosition()), SHIPS_SIZES[0], true);
        shipToPlaceIndex = 0;
    }

    public void makeShot(Position targePosition) {
        Position targetGridPos = radarGrid.getGridPosition(targePosition);

        radarGrid.getShot(targetGridPos);
    }

    public void paint(Graphics g) {
        super.paint(g);
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

            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        if (radarGrid.isInside(mousePosition)) {
            makeShot(mousePosition);
        }

        repaint();
    }
}
