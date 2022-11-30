package Entitiy;
import Display.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler kh = new KeyHandler();
    BufferedImage animated=null;
    int direction=1;
    public int coins;
    public int level;
    public float experience;
    public String name;
    int[] imageCount= {0,0,0,0};
    public Player() {

    }
    public Player(GamePanel gp, KeyHandler kh){
        this.kh=kh;
        this.gp=gp;
        setDefaultValues();



    }
    public void setDefaultValues(){
        this.x=(gp.screenWidth/2);
        this.y=(gp.screenHeight/2);
        this.speed=4;
        this.coins = 100;
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
    public void setName(String name){
        this.name=name;
    }
    public int getColumn(int number) {
        int column = 0;
        if (number < 15) {
            column = 1;
        } else if (number >= 15 && number <= 30) {
            column = 3;
        }
            return column;

    }
    public void update(){
        Images img= new Images(gp);


        for(int i = 0 ; i<4;i++){
            if(imageCount[i]>30){
                imageCount[i]=1;
            }
        }
        if(x==x&&y==y){
            animated = img.getSubImage(direction,2,"/res/player/Player1.png",2 );

        }
        if(kh.upPressed==true){
            y-=speed;
            direction=3;
            animated = img.getSubImage(direction,getColumn(imageCount[0]),"/res/player/Player1.png",2 );
            imageCount[0]++;

        }
        if(kh.downPressed==true){
            y+=speed;
            direction=1;
            animated = img.getSubImage(direction, getColumn(imageCount[1]),"/res/player/Player1.png",2);
            imageCount[1]++;
        }
        if(kh.leftPressed==true){
            x-=speed;
            direction=4;
            animated = img.getSubImage(direction, getColumn(imageCount[2]),"/res/player/Player1.png",2);
            imageCount[2]++;
        }
        if(kh.rightPressed==true){
            x+=speed;
            direction=2;
            animated = img.getSubImage(direction, getColumn(imageCount[3]),"/res/player/Player1.png",2);
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


    public void draw(Graphics2D g)  {

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
