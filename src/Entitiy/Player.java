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


public class Player extends Entity {
    public boolean tileInteracted;
    public int coins;
    public int level;
    public float experience;
    public String name;
    public int currentDay = 1;
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
     * Initializes the starting values of the player and sets the player's name based on input
     *
     * @param name name of the player
     */
    public Player(String name) {
        this.coins = 100;
        this.level = 0;
        this.experience = 0;
        this.name = name;
        setDefaultValues();

    }

    public void setDefaultValues() {
        this.x = (gp.screenWidth / 2);
        this.y = (gp.screenHeight / 2);
        this.speed = 4;
        this.coins = 10000;
        this.level = 0;
        this.experience = 0;
    }

    public void setCropImages() {
        Images cropImage = new Images(gp);
        cropImage.imagemapSet("/res/Tiles/Crops.png", 4);
        cropImageMap = cropImage.tileImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColumn(int number) {
        int column = 0;
        if (number < 15) {
            column = 1;
        } else if (number <= 30) {
            column = 3;
        }
        return column;

    }

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

    public void nexDay() {
        currentDay++;
        kh.choice = 0;
    }

    public void movement() {
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

    public boolean timedBoolean(int duration) {
        if (tileInteracted) {
            start = System.nanoTime();
            return true;
        }
        return System.nanoTime() - start < TimeUnit.MILLISECONDS.toNanos(duration);
    }

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
                            }
                        }
                        case 2 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).waterCrop(this);
                                tileInteracted = true;
                            }
                        }
                        case 3 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).plantCrop(this, currentDay);
                                tileInteracted = true;
                            }
                        }
                        case 4 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).harvestCrop(this);
                                tileInteracted = true;
                            }
                        }
                        case 5 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).fertilizeCrop(this);
                                tileInteracted = true;
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
                                message = "fuck";
                                counter++;
                                tileInteracted = true;
                            }
                        }
                        case 7 -> {
                            if (tm.tiles.get(i).get(j) instanceof Plot) {
                                ((Plot) tm.tiles.get(i).get(j)).shovel(this);
                                tileInteracted = true;
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
     * checks and calculates the level of the player based the experience value
     */
    public void checkLevel() {
        int originalLevel = this.level;
        char temp = String.format("%03d", (int) this.experience).charAt(0);
        this.level = Integer.parseInt(String.valueOf(temp));
        if (originalLevel != this.level) {
            System.out.println("Congratulations your level changed from " + originalLevel + " to " + this.level);
        }
    }

    public void setFarmerType() {
        if (level >= 5 && farmerType.level < 1) {
            farmerType = new FarmerType(this, "Registered");
            coins -= 200;
        }
        if (level >= 1 && farmerType.level < 2) {
            farmerType = new FarmerType(this, "Distinguished");
            coins -= 300;
        }
        if (level >= 1 && farmerType.level < 3) {
            farmerType = new FarmerType(this, "Legendary");
            coins -= 400;
        }
    }


}
