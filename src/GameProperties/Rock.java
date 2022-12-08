package GameProperties;

import Display.Images;


public class Rock extends Tile {
    TileManager tm;
    int hasRock;
    Images images = new Images();

    public Rock() {
        super();
        hasCollision = true;
        img = images.getImage("/res/Tiles/Rock.png");
    }
}
