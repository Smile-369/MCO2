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

    TileManager tm=new TileManager(this,kh);
    Player player=new Player(this,kh,tm);
    int[] menuCorner={(maxScreenRow*3/4-2),maxScreenRow,0, maxScreenColumn*4/5+2};
    TextBox menus= new TextBox(this,kh,tm,menuCorner);
    int[] textCorner={0,4,0, 5};
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
        tm.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        String playerInfo=String.format("%s @>:%d@Lvl:%d",player.name,player.coins,player.level);
        tm.draw(g2);
        player.draw(g2);
        menus.draw(g2,kh.menuPressed,"Controls@1 Plow@2 Water@3 Plant@4 Harvest@5 Fertilize@6 Pickaxe@7 Shovel");
        textBox.draw(g2,kh.menuPressed,playerInfo);

        g2.dispose();
    }


}
