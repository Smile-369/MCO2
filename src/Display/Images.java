package Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * this class handles the sprites for the gamem
 */
public class Images {
    public BufferedImage[][] tileImg;
    GamePanel gp;

    public Images() {

    }

    public Images(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * gets an image using Image io
     * @param name the file name
     * @return
     */
    public BufferedImage getImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * gets a sub image from an image
     * @param row location in the image
     * @param column location in the image
     * @param fileName
     * @param scale size of the nxn where n = 64/scale
     * @return
     */
    public BufferedImage getSubImage(int row, int column, String fileName, int scale) {

        BufferedImage image = getImage(fileName);
        int size = gp.tileSize / scale;
        return image.getSubimage((column * size) - size, (row * size) - size, size, size);
    }

    /**
     * sets the images from a spritemap into an array of Buffered images
     * @param Filename
     * @param scale size of the nxn where n = 64/scale
     */
    public void imagemapSet(String Filename, int scale) {
        int tileHeight = getImage(Filename).getHeight() / (gp.tileSize / scale);
        int tileWidth = getImage(Filename).getWidth() / (gp.tileSize / scale);
        tileImg = new BufferedImage[tileHeight + 1][tileWidth + 1];

        for (int i = 1; i <= tileHeight; i++) {
            for (int j = 1; j <= tileWidth; j++) {
                tileImg[i][j] = getSubImage(i, j, Filename, scale);
            }
        }
    }

}
