import java.awt.Color;
import java.awt.Graphics;

public class Cell extends Rect implements Settings{

    private boolean isShown;
    private boolean shipHit;
    private int boardType;

    public Cell(int x, int y, int width, int height, int boardType) {
        super(x, y, width, height);
        this.boardType = boardType;
        reset();
    }

    public void reset() {
        isShown = false;
        shipHit = false;
    }

    public void getShot() {
        isShown = true;
    }

    public void paint(Graphics g) {
        if (!isShown)
            return;
        
        if (boardType == SEA) {
            if (shipHit)
                g.setColor(HIT_COLOR_SEA);
            else
                g.setColor(MISS_COLOR_SEA);
        }
        else if (boardType == RADAR) {
            if (shipHit)
                g.setColor(HIT_COLOR_RADAR);
            else
                g.setColor(MISS_COLOR_RADAR);
        }
        g.fillRect(position.x + PADDING, position.y + PADDING, width - PADDING * 2, height - PADDING * 2);
    }
    
}


