package Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {
    GamePanel gp;
    public BufferedImage[][] tileImg;
    public Images(){

    }
    public Images(GamePanel gp){
        this.gp=gp;
    }
    public BufferedImage getImage(String name){
        BufferedImage image=null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(name));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage getSubImage( int row, int column,String fileName,int scale){

        BufferedImage image = getImage(fileName);
        int size=gp.tileSize/scale;
        return image.getSubimage((column*size)-size,(row*size)-size,size,size);
    }
    public void imagemapSet(String Filename,int scale){
        int tileHeight=getImage(Filename).getHeight()/(gp.tileSize/scale);
        int tileWidth=getImage(Filename).getWidth()/(gp.tileSize/scale);
        tileImg=new BufferedImage[tileHeight+1][tileWidth+1];

        for(int i = 1 ; i<= tileHeight;i++){
            for( int j = 1 ; j<=tileWidth ; j++){
                tileImg[i][j]=getSubImage(i,j,Filename,scale);
            }
        }
    }

}
