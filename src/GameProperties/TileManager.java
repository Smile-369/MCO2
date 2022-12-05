package GameProperties;
import Display.GamePanel;
import Display.Images;
import Display.KeyHandler;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


public class TileManager {
    GamePanel gp;

    public List<List<Tile>> tiles= new ArrayList<>();

    BufferedImage[][] tileImg;
    KeyHandler kh= new KeyHandler();

    Images img;

    public TileManager(GamePanel gp,KeyHandler kh){

    this.gp=gp;
    this.kh=kh;
    this.img=new Images(gp);
    img.imagemapSet("/res/Tiles/Tilemap1.png",4);
    this.tileImg=img.tileImg;
    }
    public void initTiles(){


        for(int i =0;i< gp.maxScreenRow;i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < gp.maxScreenColumn; j++) {
                tiles.get(i).add(new Tile());
                if((i>=gp.maxScreenRow/4&&i<gp.maxScreenRow*3/4)&&(j>= gp.maxScreenColumn/4&&j<gp.maxScreenColumn*3/4)){
                    tiles.get(i).set(j,new Plot(gp));
                }
                tiles.get(i).get(j).x=i*gp.tileSize;
                tiles.get(i).get(j).y=j*gp.tileSize;

            }
        }
        setTileImage();

    }
    public void update(){

        setPlots();

    }
    public void setPlots() {
        int[] corners = {tiles.size() / 4 , tiles.size() * 3 / 4 , tiles.get(1).size() / 4 , tiles.get(1).size() * 3 / 4};
        for (int i = corners[0]; i < corners[1]; i++) {
            for (int j = corners[2]; j < corners[3]; j++) {
                if(((Plot) tiles.get(i).get(j)).isPlowed){
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
                } else {
                    tiles.get(i).get(j).img = tileImg[5][5];
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
                tiles.get(i).get(j).img=tileImg[1][column[rnd]];
            }
        }

    }

    public void draw(Graphics2D g)  {
        for(int i = 0;i<tiles.size();i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {

                g.drawImage(tiles.get(i).get(j).img,tiles.get(i).get(j).x,tiles.get(i).get(j).y,gp.tileSize,gp.tileSize,null);
                if(tiles.get(i).get(j) instanceof Plot &&((Plot) tiles.get(i).get(j)).hasCrop){
                    ((Plot)tiles.get(i).get(j)).Crop.draw(g,tiles.get(i).get(j).x,tiles.get(i).get(j).y,gp.tileSize);
                }
            }
        }
    }
}
