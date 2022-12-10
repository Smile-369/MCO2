package GameProperties;

import Display.GamePanel;
import Display.Images;
import Display.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages and creates a 2D list for tiles
 */
public class TileManager {
    public List<List<Tile>> tiles = new ArrayList<>();
    GamePanel gp;
    BufferedImage[][] tileImg;
    KeyHandler kh;

    Images img;

    public TileManager(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;
        this.img = new Images(gp);
        img.imagemapSet("/res/Tiles/Tilemap1.png", 4);
        this.tileImg = img.tileImg;
    }

    /**
     * initializes the tiles
     */

    public void initTiles() {
        for (int i = 0; i < gp.maxScreenRow; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < gp.maxScreenColumn; j++) {
                tiles.get(i).add(new Tile());
                if ((i >= gp.maxScreenRow / 4 && i < gp.maxScreenRow * 3 / 4) && (j >= gp.maxScreenColumn / 4 && j < gp.maxScreenColumn * 3 / 4)) {
                    tiles.get(i).set(j, new Plot(gp, kh));
                }
                tiles.get(i).get(j).x = i * gp.tileSize;
                tiles.get(i).get(j).y = j * gp.tileSize;
            }
        }
        setRocks();
        setTileImage();


    }

    /**
     * updates the Plot in case there are changes to its Crops and sprites
     */

    public void update() {
        setPlots();
    }

    /**
     * Sets which tiles are Rocks in the space for plots based on a text file
     */
    public void setRocks() {
        int[] corners = {tiles.size() / 4, tiles.size() * 3 / 4, tiles.get(1).size() / 4, tiles.get(1).size() * 3 / 4};
        int k = 0;
        String[] tokens;
        String delimeter = " ";
        try {

            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "//src/GameProperties/input.txt"));
            for (int i = corners[2]; i < corners[3]; i++) {
                String line = br.readLine();
                tokens = line.split(delimeter);
                for (int j = corners[0]; j < corners[1]; j++) {
                    if (Integer.parseInt(tokens[k]) == 1) {
                        tiles.get(j).set(i, new Rock());
                        tiles.get(j).get(i).x = j * gp.tileSize;
                        tiles.get(j).get(i).y = i * gp.tileSize;
                    }

                    k++;
                }

                k = 0;
            }
            br.close();
        } catch (Exception ignored) {

        }

    }

    /**
     * sets the sprites of the plots
     */
    public void setPlots() {
        int[] corners = {tiles.size() / 4, tiles.size() * 3 / 4, tiles.get(1).size() / 4, tiles.get(1).size() * 3 / 4};
        for (int i = corners[0]; i < corners[1]; i++) {
            for (int j = corners[2]; j < corners[3]; j++) {
                if (tiles.get(i).get(j) instanceof Plot && ((Plot) tiles.get(i).get(j)).isPlowed) {
                    if ((i == corners[0] && j == (corners[2]))) {
                        tiles.get(i).get(j).img = img.tileImg[4][2];
                    } else if ((i == corners[0] && j == (corners[3]) - 1)) {
                        tiles.get(i).get(j).img = tileImg[6][2];
                    } else if ((i == (corners[1]) - 1 && j == (corners[3]) - 1)) {
                        tiles.get(i).get(j).img = tileImg[6][4];
                    } else if ((i == (corners[1]) - 1 && j == (corners[2]))) {
                        tiles.get(i).get(j).img = tileImg[4][4];
                    } else if ((i > corners[0] && j == (corners[3]) - 1)) {
                        tiles.get(i).get(j).img = tileImg[6][3];
                    } else if ((i == (corners[1]) - 1 && j > (corners[2]))) {
                        tiles.get(i).get(j).img = tileImg[5][4];
                    } else if ((i < (corners[1]) - 1 && j == (corners[2]))) {
                        tiles.get(i).get(j).img = tileImg[4][3];
                    } else if ((i == corners[0] && j < (corners[3]) - 1)) {
                        tiles.get(i).get(j).img = tileImg[5][2];
                    } else {
                        tiles.get(i).get(j).img = tileImg[5][3];
                    }
                } else if (tiles.get(i).get(j) instanceof Plot) {
                    tiles.get(i).get(j).img = tileImg[5][5];
                } else {
                    tiles.get(i).get(j).img = img.getImage("/res/Tiles/Rock.png");
                }


            }
        }
    }

    /**
     * sets the images for the non plot Tiles outside of the plot space
     */
    public void setTileImage() {
        Integer[] column = {1, 2, 3, 5};
        int rnd;
        System.out.println();
        for (List<Tile> tile : tiles) {
            for (Tile value : tile) {
                rnd = new Random().nextInt(column.length);
                value.img = tileImg[1][column[rnd]];
            }
        }

    }

    /**
     * draws all the Tiles
     * @param g
     */
    public void draw(Graphics2D g) {
        for (List<Tile> tile : tiles) {
            for (Tile value : tile) {
                g.drawImage(value.img, value.x, value.y, gp.tileSize, gp.tileSize, null);
                if (value instanceof Plot && ((Plot) value).hasCrop) {
                    ((Plot) value).Crop.draw(g, value.x, value.y, gp.tileSize);
                }
            }
        }
    }
}
