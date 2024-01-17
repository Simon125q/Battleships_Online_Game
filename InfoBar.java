import java.awt.Graphics;

public class InfoBar extends Rect implements Settings {

    private Position position;
    
    private String currMsg = null;
    
    public InfoBar(Position position) {
        super(position, BAR_WIDTH * TILE_W, BAR_HEIGHT * TILE_H);
        this.position = position;
        currMsg = PLACING;
    }

    public void update(String msg) {
        currMsg = msg;
    }

    public void paint(Graphics g) {
        g.setColor(BAR_FONT_COLOR);
        g.setFont(BAR_FONT);
        int msgWidth = g.getFontMetrics().stringWidth(currMsg);
        g.drawString(currMsg, position.x + GRID_WIDTH / 2 * TILE_W - msgWidth / 2, position.y + TILE_H);
    }
    
}
