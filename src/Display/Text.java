package Display;

import GameProperties.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Fonts
 */

public class Text {
    GamePanel gp;
    KeyHandler kh;
    TileManager tm;
    BufferedImage[][] characters;
    BufferedImage[] ascii = new BufferedImage[200];
    Images img;

    Text(GamePanel gp, KeyHandler kh, TileManager tm) {
        this.gp = gp;
        this.kh = kh;
        this.tm = tm;
        img = new Images(gp);
        img.imagemapSet("/res/Fonts/fonts.png", 8);
        characters = img.tileImg;
        setAscii();
    }

    /**
     * sets the sprites into an array of BufferedImages where its index value is its ASCII Value
     */

    public void setAscii() {
        int i;
        for (i = 0; i < 65; i++) {
            ascii[i] = characters[4][17];
        }

        for (i = '0'; i <= 'z'; i++) {
            if (i <= 'C') {
                ascii[i] = characters[1][(i - '0' + 1)];
            } else if (i <= 'W') {
                ascii[i] = characters[2][i - 'D' + 1];
            } else if (i <= 'k') {
                ascii[i] = characters[3][i - 'X' + 1];
            } else {
                ascii[i] = characters[4][i - 'l' + 1];
            }
        }
    }

    /**
     * draws the text
     * @param g
     * @param word
     * @param x
     * @param y
     * @param scale
     */
    public void draw(Graphics2D g, String word, int x, int y, int scale) {
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            g.drawImage(ascii[ch], x + (i * (gp.tileSize * 3 / 4) / scale) - (gp.tileSize / 5), y - gp.tileSize / 4, gp.tileSize / scale, gp.tileSize / scale, null);
        }
    }
}
