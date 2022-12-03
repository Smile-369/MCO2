package Display;

import GameProperties.Tile;
import GameProperties.TileManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TextBox {
    public List<List<Tile>> textBoxTiles= new ArrayList<>();

    TileManager tm;
    GamePanel gp;
    Images img;
    BufferedImage[][] textBox;
    KeyHandler kh=new KeyHandler();
    Text txt;
    int[] corner;
    public int[] textCords=new int[2];
    TextBox(GamePanel gp, KeyHandler kh,TileManager tm,int[] corner){
        this.gp=gp;
        this.kh=kh;
        this.tm=tm;
        this.corner=corner;
        img=new Images(gp);
        img.imagemapSet("/res/Tiles/TextBox.png",8);
        textBox=img.tileImg;
        txt=new Text(gp, kh,tm);
    }
    public void initTextBox(){

        for(int i = 0 ; i < tm.tiles.size();i++){
            textBoxTiles.add(new ArrayList<>());
            for(int j = 0 ; j<tm.tiles.get(i).size();j++){
                textBoxTiles.get(i).add(new Tile());
                textBoxTiles.get(i).get(j).x=tm.tiles.get(i).get(j).x;
                textBoxTiles.get(i).get(j).y=tm.tiles.get(i).get(j).y;


            }
        }
        setTextBoxImage(corner);
    }
    public void setTextBoxImage(int[]corner){


        for(int i = corner[0];i<corner[1];i++){
            for (int j = corner[2]; j< corner[3];j++){
                if(i==corner[0]&&j==corner[2]){
                    textBoxTiles.get(i).get(j).img=textBox[1][1];
                    this.textCords[0]=textBoxTiles.get(i).get(j).x;
                    this.textCords[1]=textBoxTiles.get(i).get(j).y;

                }else if(i==corner[0]&&j==corner[3]-1){
                    textBoxTiles.get(i).get(j).img=textBox[3][1];

                }
                else if(i==corner[1]-1&&j==corner[2]){
                    textBoxTiles.get(i).get(j).img=textBox[1][3];
                }
                else if(i==corner[1]-1&&j==corner[3]-1){
                    textBoxTiles.get(i).get(j).img=textBox[3][3];
                }
                else if(i>corner[0]&&j==corner[3]-1){
                    textBoxTiles.get(i).get(j).img=textBox[3][2];
                }
                else if(i<corner[1]-1&&j==corner[2]){
                    textBoxTiles.get(i).get(j).img=textBox[1][2];
                }
                else if(i==corner[1]-1&&j>corner[2]){
                    textBoxTiles.get(i).get(j).img=textBox[2][3];
                }
                else if(i==corner[0]&&j>corner[2]){
                    textBoxTiles.get(i).get(j).img=textBox[2][1];
                }
                else{
                    textBoxTiles.get(i).get(j).img=textBox[2][2];
                }
            }
        }
    }
    String[] splitLine(String input){
        String[] output=input.split("@");

        return output;
    }

    public void draw(Graphics2D g, boolean condition,String Text)  {
        int i= 0 ;
        String [] drawn=splitLine(Text);
        if (condition){
            for(i = 0;i<textBoxTiles.size();i++){
                for (int j = 0; j < textBoxTiles.get(i).size(); j++) {
                    g.drawImage(textBoxTiles.get(i).get(j).img,textBoxTiles.get(i).get(j).x,textBoxTiles.get(i).get(j).y,gp.tileSize,gp.tileSize,null);
                }
            }
            for(i = 0; i<drawn.length;i++){
                txt.draw(g, drawn[i], textCords[0] + gp.tileSize, textCords[1] + gp.tileSize*(i+1), 2);
            }

        }

    }
}
