package Display;

import Entitiy.Player;
import GameProperties.Tile;
import GameProperties.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    KeyHandler kh =new KeyHandler();
    public int originalTileSize = 16;
    public int scale=4;
    public int tileSize = originalTileSize*scale;
    public int maxScreenRow = 20;
    public int maxScreenColumn = 10;
    public int screenWidth = maxScreenRow * tileSize;
    public int screenHeight = maxScreenColumn * tileSize;



    public int FPS=60;
    Thread gameThread;
    Player player=new Player(this,kh);
    TileManager tm=new TileManager(this,kh);
    int[] menuCorner={(maxScreenRow*3/4),maxScreenRow,0, maxScreenColumn*3/4};
    TextBox menus= new TextBox(this,kh,tm,menuCorner);
    int[] textCorner={(maxScreenRow/5),maxScreenRow*4/5,maxScreenColumn*2/3, maxScreenColumn};
    TextBox textBox =new TextBox(this,kh,tm,textCorner);
    Text txt= new Text(this,kh,tm);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.setFocusable(true);

    }


    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        tm.initTiles();
        menus.initTextBox();
        textBox.initTextBox();
        double drawInterval=1000000000/FPS;
        double nxtDrawTime=System.nanoTime()+drawInterval;
        while (gameThread!=null){
                update();
                repaint();
            try{
                double rmngTime=nxtDrawTime-System.nanoTime();
                rmngTime =rmngTime/1000000;
                if(rmngTime<0){
                    rmngTime=0;
                }

                Thread.sleep((long)rmngTime);
                nxtDrawTime+=drawInterval;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;

        tm.draw(g2);
        player.draw(g2);
        menus.draw(g2,kh.menuPressed,"");
        textBox.draw(g2,true,"The quick Brown @Fox Jumped");

        g2.dispose();
    }


}
