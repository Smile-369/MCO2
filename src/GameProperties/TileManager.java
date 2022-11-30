package GameProperties;
import Display.GamePanel;
import Display.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


public class TileManager {
    GamePanel gp;
    public List<List<Tile>> tiles= new ArrayList<>();



    public TileManager(GamePanel gp){
    this.gp=gp;
    }
    public void initTiles(){
        int count = 0;

        for(int i =0;i< gp.maxScreenRow;i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < gp.maxScreenColumn; j++) {
                tiles.get(i).add(new Tile());
                tiles.get(i).get(j).x=i*gp.tileSize;
                tiles.get(i).get(j).y=j*gp.tileSize;

            }
        }
        setTileImage();
        setPlots();

    }
    public void setPlots(){
        Images img= new Images(gp);
        for(int i=tiles.size()/4;i<tiles.size()*3/4;i++){
            for(int j= tiles.get(i).size()/4;j<tiles.get(i).size()*3/4;j++){
                tiles.get(i).set(j,new Plot());
                tiles.get(i).get(j).x=i*gp.tileSize;
                tiles.get(i).get(j).y=j*gp.tileSize;
                if((i==tiles.size()/4&&j==(tiles.get(i).size()/4))) {
                    tiles.get(i).get(j).img=img.getSubImage(4,2,"/res/Tiles/Tilemap1.png",4);
                }
                else if((i==tiles.size()/4&&j==(tiles.get(i).size()*3/4)-1)){
                    tiles.get(i).get(j).img=img.getSubImage(6,2,"/res/Tiles/Tilemap1.png",4);
                }else if((i==(tiles.size()*3/4)-1&& j==(tiles.get(i).size()*3/4)-1)){
                    tiles.get(i).get(j).img=img.getSubImage(6,4,"/res/Tiles/Tilemap1.png",4);
                }else if((i==(tiles.size()*3/4)-1&&j==(tiles.get(i).size()/4))){
                    tiles.get(i).get(j).img=img.getSubImage(4,4,"/res/Tiles/Tilemap1.png",4);
                } else if ((i>tiles.size()/4&&j==(tiles.get(i).size()*3/4)-1)) {
                    tiles.get(i).get(j).img=img.getSubImage(6,3,"/res/Tiles/Tilemap1.png",4);
                } else if ((i==(tiles.size()*3/4)-1&&j>(tiles.get(i).size()/4))) {
                    tiles.get(i).get(j).img=img.getSubImage(5,4,"/res/Tiles/Tilemap1.png",4);
                }else if ((i<(tiles.size()*3/4)-1&&j==(tiles.get(i).size()/4))) {
                    tiles.get(i).get(j).img=img.getSubImage(4,3,"/res/Tiles/Tilemap1.png",4);
                }else if ((i==tiles.size()/4&&j<(tiles.get(i).size()*3/4)-1)) {
                    tiles.get(i).get(j).img=img.getSubImage(5,2,"/res/Tiles/Tilemap1.png",4);
                }else {
                    tiles.get(i).get(j).img=img.getSubImage(5,3,"/res/Tiles/Tilemap1.png",4);
                }

            }
        }
    }
    public void setTileImage(){
        Images img= new Images(gp);
        Integer[] column={1,2,3,5};
        int rnd=0;
        System.out.println();
        for(int i = 0;i<tiles.size();i++){
            for (int j = 0; j< tiles.get(i).size();j++){
                rnd = new Random().nextInt(column.length);
                tiles.get(i).get(j).img=img.getSubImage(1,column[rnd],"/res/Tiles/Tilemap1.png",4);
            }
        }

    }
    public void draw(Graphics2D g)  {
        for(int i = 0;i<tiles.size();i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                g.drawImage(tiles.get(i).get(j).img,tiles.get(i).get(j).x,tiles.get(i).get(j).y,gp.tileSize,gp.tileSize,null);
            }
        }
    }
}
