package Entitiy;

import Display.GamePanel;
import Display.Images;
import Display.KeyHandler;
import GameProperties.Plot;
import GameProperties.Rock;
import GameProperties.Tile;
import GameProperties.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * the player class which inherits from entity that can freely act in the space based on the keys pressed
 */

public class Player extends Entity {
    public boolean tileInteracted;
    public int coins;
    public int level=1;
    public float experience;
    public int currentDay ;
    public FarmerType farmerType = new FarmerType(this, "Farmer");
    public String message = "";
    GamePanel gp;
    KeyHandler kh;
    BufferedImage[][] playerImg;
    BufferedImage[][] cropImageMap;
    BufferedImage preview;
    BufferedImage animated;
    int direction = 1;
    int[] imageCount = {0, 0, 0, 0};
    Images img;
    TileManager tm;
    int counter = 0;
    private volatile long start;

    public Player() {

    }

    /**
     * This constructor gets the values from of the keyhandler tile manage and the game panel
     * @param gp GamePanel Class
     * @param kh KeyHandler Class
     * @param tm TileManager Class
     */
    public Player(GamePanel gp, KeyHandler kh, TileManager tm) {
        this.kh = kh;
        this.gp = gp;
        img = new Images(gp);
        img.imagemapSet("/res/player/Player1.png", 2);
        playerImg = img.tileImg;
        this.tm = tm;
        setDefaultValues();
        setCropImages();
    }

    /**
     * Initializes the starting values of the player
     *
     *
     */
    public void setDefaultValues() {
        this.currentDay=1;
        this.x = (gp.screenWidth / 2);
        this.y = (gp.screenHeight / 2);
        this.speed = 4;
        this.coins = 100;
        this.level = 0;
        this.experience = 0;
    }

    /**
     * sets the crop imagemap to be used by previews when planting tiles
     */
    private void setCropImages() {
        Images cropImage = new Images(gp);
        cropImage.imagemapSet("/res/Tiles/Crops.png", 4);
        cropImageMap = cropImage.tileImg;
    }


    /**
     * gets the column in which the animation sprite occurs
     * @param number
     * @return
     */
    private int getColumn(int number) {
        int column = 0;
        if (number < 15) {
            column = 1;
        } else if (number <= 30) {
            column = 3;
        }
        return column;

    }

    /**
     * updates the player sprite movement, and the day
     */
    public void update() {
        plotInteraction();
        for (List<Tile> tile : tm.tiles) {
            for (Tile value : tile) {
                if (value instanceof Plot) {
                    ((Plot) value).checkPlantStats(currentDay);
                }
            }
        }
        checkLevel();
        if (!kh.menuPressed) {
            movement();
        }
        if (kh.menuPressed && kh.choice == 8) {
            nexDay();

        }

    }

    /**
     * changes the day
     */
    private void nexDay() {
        currentDay++;
        kh.choice = 0;
    }

    /**
     * movement of the player
     */
    private void movement() {
        for (int i = 0; i < 4; i++) {
            if (imageCount[i] > 30) {
                imageCount[i] = 1;
            }
        }
        animated = playerImg[direction][2];

        if (kh.upPressed) {
            y -= speed;
            direction = 3;
            animated = playerImg[direction][getColumn(imageCount[0])];
            imageCount[0]++;

        }
        if (kh.downPressed) {
            y += speed;
            direction = 1;
            animated = playerImg[direction][getColumn(imageCount[1])];
            imageCount[1]++;
        }
        if (kh.leftPressed) {
            x -= speed;
            direction = 4;
            animated = playerImg[direction][getColumn(imageCount[2])];
            imageCount[2]++;
        }
        if (kh.rightPressed) {
            x += speed;
            direction = 2;
            animated = playerImg[direction][getColumn(imageCount[3])];
            imageCount[3]++;
        }
        if (y >= gp.screenHeight - gp.tileSize) {
            y = gp.screenHeight - gp.tileSize;
        }
        if (y <= 0) {
            y = 0;
        }
        if (x >= gp.screenWidth - gp.tileSize) {
            x = gp.screenWidth - gp.tileSize;
        }
        if (x <= 0) {
            x = 0;
        }
    }

    /**
     * timed boolean for when a text popup occurs when an action happens
     * @param duration
     * @param condition
     * @return
     */

    public boolean timedBoolean(int duration,boolean condition) {
        if (tileInteracted) {
            start = System.nanoTime();
            return true;
        }
        return System.nanoTime() - start < TimeUnit.MILLISECONDS.toNanos(duration);
    }

    /**
     * interactions with the plot
     */
    public void plotInteraction() {
        for (int i = 0; i < tm.tiles.size(); i++) {
            for (int j = 0; j < tm.tiles.get(i).size(); j++) {
                if (x + 32 >= tm.tiles.get(i).get(j).x && x + 32 < tm.tiles.get(i).get(j).x + 64 && y + 32 >= tm.tiles.get(i).get(j).y && y + 32 < tm.tiles.get(i).get(j).y + 64
                        && kh.cPressed && counter == 0 && (tm.tiles.get(i).get(j) instanceof Plot || tm.tiles.get(i).get(j) instanceof Rock)) {
                    switch (kh.choice) {
                        case 1 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).plowTile(this);
                                tileInteracted = true;
                            }else {
                                message="unable to interact with a@rock";
                            }
                        }
                        case 2 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).waterCrop(this);
                                tileInteracted = true;
                            }else {
                                message="unable to interact with a@rock";
                            }
                        }
                        case 3 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).plantCrop(this, currentDay,new Tile[]{tm.tiles.get(i+1).get(j),tm.tiles.get(i-1).get(j),tm.tiles.get(i).get(j+1),tm.tiles.get(i).get(j-1)});
                                tileInteracted = true;
                            }else {
                                message="unable to interact with a@rock";
                            }
                        }
                        case 4 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).harvestCrop(this);
                                tileInteracted = true;
                            }else {
                                message="unable to interact with a@rock";
                            }
                        }
                        case 5 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).fertilizeCrop(this);
                                tileInteracted = true;
                            }else {
                                message="unable to interact with a@rock";
                            }
                        }
                        case 6 -> {
                            if (tm.tiles.get(i).get(j) instanceof Rock) {
                                tm.tiles.get(i).set(j, new Tile());
                                tm.tiles.get(i).set(j, new Plot(gp, kh));
                                coins -= 50;
                                experience += 15;
                                tm.tiles.get(i).get(j).x = i * gp.tileSize;
                                tm.tiles.get(i).get(j).y = j * gp.tileSize;
                                message = "You have successfully@removed a Rock";
                                counter++;
                                tileInteracted = true;
                            }else {
                                message="There is no Rock to@be removed here";
                            }
                        }
                        case 7 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).shovel(this);
                                tileInteracted = true;
                            }else {
                                message="unable to interact with a@rock";
                            }
                        }
                    }

                    counter++;
                }
                if (!kh.cPressed) {
                    counter = 0;
                    tileInteracted = false;
                }

            }
        }
    }

    /**
     * draws the player
     * @param g
     */
    public void draw(Graphics2D g) {


        switch (kh.cropChoice) {
            case 1 -> preview = cropImageMap[1][3];
            case 2 -> preview = cropImageMap[1][5];
            case 3 -> preview = cropImageMap[1][7];
            case 4 -> preview = cropImageMap[2][5];
            case 5 -> preview = cropImageMap[2][7];
            case 6 -> preview = cropImageMap[2][3];
            case 7 -> preview = cropImageMap[3][3];
            case 8 -> preview = cropImageMap[3][5];
        }
        if (kh.choice == 3) {
            switch (direction) {
                case 1 -> g.drawImage(preview, x, y + 64, gp.tileSize, gp.tileSize, null);

                case 2 -> g.drawImage(preview, x + 64, y, gp.tileSize, gp.tileSize, null);

                case 3 -> g.drawImage(preview, x, y - 64, gp.tileSize, gp.tileSize, null);

                case 4 -> g.drawImage(preview, x - 64, y, gp.tileSize, gp.tileSize, null);
            }

        }

        g.drawImage(animated, x, y, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Checks level of the player
     * @return true if level changed
     * @return true if player has enough to change farmerTypes
     */
    public boolean checkLevel() {
        int originalLevel = this.level;
        char temp = String.format("%03d", (int) this.experience).charAt(0);
        this.level = Integer.parseInt(String.valueOf(temp));
        if(setFarmerType()){
            message=String.format("You are now a %s",farmerType.title);
            return setFarmerType();
        }
        if (originalLevel != this.level) {
            message="Level Up";
            return true;
        }
        return false;
    }

    /**
     * changes the players farmer type when coditions are set
     * @return true when the player changes farmer type
     */

    public boolean setFarmerType() {
        if (level >= 5 && farmerType.level < 1&&coins>250) {
            farmerType = new FarmerType(this, "Registered");
            coins -= 200;
            return true;
        }
        if (level >= 10 && farmerType.level < 2&&coins>350) {
            farmerType = new FarmerType(this, "Distinguished");
            coins -= 300;
            return true;
        }
        if (level >= 15 && farmerType.level < 3&&coins>450) {
            farmerType = new FarmerType(this, "Legendary");
            coins -= 400;
            return true;
        }
        return false;
    }


}
