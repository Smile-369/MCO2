package Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {
    GamePanel gp;
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
        Images img=new Images();
        BufferedImage image = img.getImage(fileName);
        int size=gp.tileSize/scale;
        return image.getSubimage((column*size)-size,(row*size)-size,size,size);
    }
}
