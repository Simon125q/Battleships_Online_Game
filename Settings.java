import java.awt.Color;

public interface Settings {
    
    public static final int TILE_W = 32;
    public static final int TILE_H = 32;
    public static final int TILE_ROWS = 25;
    public static final int TILE_COLS = 14;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 10;
    public static final int WINDOW_WIDTH = TILE_COLS * TILE_W;
    public static final int WINDOW_HEIGHT = TILE_ROWS * TILE_H;

    public static final Color BACKGROUND_COLOR = new Color(23, 56, 74);

    // Boards types
    public static final int SEA = 1;
    public static final int RADAR = 2;

    public static final int BOARD_OFFSET_X = 2;
    public static final int BOARD_OFFSET_Y = 2;

    public static final int[] SHIPS_SIZES = {5,4,3,3,2};
    
    public final Color HIT_COLOR_SEA = new Color(220, 23, 23, 180);
    public final Color MISS_COLOR_SEA = new Color(30, 30, 97, 180);
    public final Color HIT_COLOR_RADAR = new Color(220, 23, 23, 180);
    public final Color MISS_COLOR_RADAR = new Color(30, 30, 97, 180);

    public final int PADDING = 3;

    // game phases
    public static final int SHIP_PLACING = 1;
    public static final int SHOOTING = 2;
    public static final int GAME_ENDED = 3;

    // ship placement options
    public final int RIGHT_PLACEMENT = 1;
    public final int WRONG_PLACEMENT = 2;
    public final int PLACED_ALIVE = 3;
    public final int PLACED_DEAD = 4;
}
