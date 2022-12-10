package GameProperties;

import java.awt.image.BufferedImage;

/**
 * base class for any tiles in the game
 */

public class Tile {
    /**
     * planned to implement collision but decided not to
     */
    public boolean hasCollision;
    /**
     * image
     */
    public BufferedImage img;
    /**
     * coordinates of the tile
     */
    public int x, y;

    public Tile() {

    }
}
