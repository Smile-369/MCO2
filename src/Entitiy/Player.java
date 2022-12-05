package Entitiy;

import Display.GamePanel;
import Display.Images;
import Display.KeyHandler;
import GameProperties.Plot;
import GameProperties.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler kh = new KeyHandler();
    BufferedImage[][] playerImg;
    BufferedImage[][] cropImageMap;
    BufferedImage preview;
    BufferedImage animated;
    int direction=1;
    public int coins;
    public int level;
    public float experience;
    public String name;
    public int currentDay=1;
    int[] imageCount= {0,0,0,0};
    Images img;
    TileManager tm;
    public Player() {

    }
    public Player(GamePanel gp, KeyHandler kh,TileManager tm){
        this.kh=kh;
        this.gp=gp;
        setDefaultValues();
        img= new Images(gp);
        img.imagemapSet("/res/player/Player1.png",2);
        playerImg=img.tileImg;
        this.tm=tm;
        setCropImages();
    }
    public void setDefaultValues(){
        this.x=(gp.screenWidth/2);
        this.y=(gp.screenHeight/2);
        this.speed=4;
        this.coins = 10000;
        this.level = 0;
        this.experience = 0;

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
    public void setCropImages(){
        Images cropImage =new Images(gp);
        cropImage.imagemapSet("/res/Tiles/Crops.png",4);
        cropImageMap =cropImage.tileImg;
    }
    public void setName(String name){
        this.name=name;
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
    public void update(){
        plotInteraction();
        if(!kh.menuPressed){
            movement();
        }

    }


    public void movement(){
        for(int i = 0 ; i<4;i++){
            if(imageCount[i]>30){
                imageCount[i]=1;
            }
        }
        animated = playerImg[direction][2];

        if(kh.upPressed){
            y-=speed;
            direction=3;
            animated = playerImg[direction][getColumn(imageCount[0])];
            imageCount[0]++;

        }
        if(kh.downPressed){
            y+=speed;
            direction=1;
            animated = playerImg[direction][getColumn(imageCount[1])];
            imageCount[1]++;
        }
        if(kh.leftPressed){
            x-=speed;
            direction=4;
            animated = playerImg[direction][getColumn(imageCount[2])];
            imageCount[2]++;
        }
        if(kh.rightPressed){
            x+=speed;
            direction=2;
            animated = playerImg[direction][getColumn(imageCount[3])];
            imageCount[3]++;
        }
        if(y>= gp.screenHeight- gp.tileSize){
            y= gp.screenHeight- gp.tileSize;
        }
        if(y<=0){
            y=0;
        }
        if(x>= gp.screenWidth- gp.tileSize){
            x= gp.screenWidth-gp.tileSize;
        }
        if(x<=0){
            x=0;
        }
    }
    public void plotInteraction(){
        int choice=1;
        choice=kh.choice;
        for(int i = 0;i<tm.tiles.size();i++) {
            for (int j = 0; j < tm.tiles.get(i).size(); j++) {
                if(x+32>=tm.tiles.get(i).get(j).x&&x+32<tm.tiles.get(i).get(j).x+64&&y+32>=tm.tiles.get(i).get(j).y&&y+32<tm.tiles.get(i).get(j).y+64
                        &&tm.tiles.get(i).get(j) instanceof Plot&&kh.cPressed){
                    switch (choice){
                        case 1 ->{
                            ((Plot)tm.tiles.get(i).get(j)).plowTile(this);
                        }
                        case 2->{
                            ((Plot) tm.tiles.get(i).get(j)).waterCrop(this);
                        }
                        case 3->{
                            ((Plot) tm.tiles.get(i).get(j)).plantCrop(this,1);
                        }
                        case 4->{
                            ((Plot) tm.tiles.get(i).get(j)).harvestCrop(this);
                        }
                        case 5->{
                            ((Plot) tm.tiles.get(i).get(j)).fertilizeCrop(this);
                        }
                        case 6->{

                        }
                        case 7->{

                        }


                    }


                }
            }
        }
    }


    public void draw(Graphics2D g)  {

        switch (kh.cropChoice){
            case 1->preview=cropImageMap[1][3];
            case 2->preview=cropImageMap[1][5];
            case 3->preview=cropImageMap[1][7];
            case 4->preview=cropImageMap[2][5];
            case 5->preview=cropImageMap[2][7];
            case 6->preview=cropImageMap[2][3];
            case 7->preview=cropImageMap[3][3];
            case 8->preview=cropImageMap[3][5];
        }
        if(kh.choice==3){
            switch (direction){
                case 1-> g.drawImage(preview, x, y+64, gp.tileSize, gp.tileSize, null);
            }
            switch (direction){
                case 2-> g.drawImage(preview, x+64, y, gp.tileSize, gp.tileSize, null);
            }
            switch (direction){
                case 3-> g.drawImage(preview, x, y-64, gp.tileSize, gp.tileSize, null);
            }
            switch (direction){
                case 4-> g.drawImage(preview, x-64, y, gp.tileSize, gp.tileSize, null);
            }

        }

        g.drawImage(animated,x,y,gp.tileSize,gp.tileSize,null);


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


}
