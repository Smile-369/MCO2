package Display;

import GameProperties.Tile;
import GameProperties.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Menus {
    public List<List<Tile>> menu= new ArrayList<>();

    TileManager tm;
    GamePanel gp;
    KeyHandler kh=new KeyHandler();

    Menus(GamePanel gp, KeyHandler kh,TileManager tm){
        this.gp=gp;
        this.kh=kh;
        this.tm=tm;
    }
    public void initMenus(){
        int k=0;
        for(int i = 0;i<tm.tiles.size();i++){
            if(i>=tm.tiles.size()*3/4&&i<=tm.tiles.size()){
                menu.add(new ArrayList<>());
            }
            int l = 0;
            for (int j = 0; j< tm.tiles.get(i).size();j++){
                if(j>=0&&j<tm.tiles.get(i).size()*3/4&&i>=tm.tiles.size()*3/4&&i<=tm.tiles.size()){
                    menu.get(k).add(new Tile());
                    menu.get(k).get(l).x=tm.tiles.get(i).get(j).x;
                    menu.get(k).get(l).y=tm.tiles.get(i).get(j).y;
                    l++;
                }
            }
            if(i>=tm.tiles.size()*3/4&&i<=tm.tiles.size()){
                k++;
            }

        }
        setMenuImage();
    }
    public void setMenuImage(){
        Images img= new Images(gp);
        for(int i = 0;i<menu.size();i++){
            for (int j = 0; j< menu.get(i).size();j++){
                if(i==0&&j==0){
                    menu.get(i).get(j).img=img.getSubImage(1,1,"/res/Tiles/TextBox.png",8);
                }else if(i==0&&j==menu.get(i).size()-1){
                    menu.get(i).get(j).img=img.getSubImage(3,1,"/res/Tiles/TextBox.png",8);
                }
                else if(i==menu.size()-1&&j==0){
                    menu.get(i).get(j).img=img.getSubImage(1,3,"/res/Tiles/TextBox.png",8);
                }
                else if(i==menu.size()-1&&j==menu.get(i).size()-1){
                    menu.get(i).get(j).img=img.getSubImage(3,3,"/res/Tiles/TextBox.png",8);
                }
                else if(i>0&&j==menu.get(i).size()-1){
                    menu.get(i).get(j).img=img.getSubImage(3,2,"/res/Tiles/TextBox.png",8);
                }
                else if(i<menu.size()-1&&j==0){
                    menu.get(i).get(j).img=img.getSubImage(1,2,"/res/Tiles/TextBox.png",8);
                }
                else if(i==menu.size()-1&&j>0){
                    menu.get(i).get(j).img=img.getSubImage(2,3,"/res/Tiles/TextBox.png",8);
                }
                else if(i==0&&j>0){
                    menu.get(i).get(j).img=img.getSubImage(2,1,"/res/Tiles/TextBox.png",8);
                }
                else{
                    menu.get(i).get(j).img=img.getSubImage(2,2,"/res/Tiles/TextBox.png",8);
                }
            }
        }
    }


    public void draw(Graphics2D g)  {
        boolean hold = false;

        if (tm.checkPlotInteraction()){
            for(int i = 0;i<menu.size();i++){
                for (int j = 0; j < menu.get(i).size(); j++) {
                    g.drawImage(menu.get(i).get(j).img,menu.get(i).get(j).x,menu.get(i).get(j).y,gp.tileSize,gp.tileSize,null);
                }
            }

        }
    }
}
