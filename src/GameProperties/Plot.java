package GameProperties;

import Display.GamePanel;
import Display.Images;
import Entitiy.Player;
import Display.KeyHandler;
import org.intellij.lang.annotations.Flow;

import java.awt.image.BufferedImage;
import java.security.Key;

/**
 * this class is the Tile class where it can be plowed oe planted in
 */
public class Plot extends Tile {
    public Crop Crop=new Crop();
    public boolean isPlowed;
    public String cropName;
    public boolean hasRock;
    public boolean isOccupied;
    public boolean hasCrop;
    BufferedImage[][] cropImageMap;
    KeyHandler kh;
    GamePanel gp;


    /**
     * this constructor sets everything to false and initializes an empty crop
     */
    public Plot(GamePanel gp, KeyHandler kh) {
        this.gp=gp;
        this.kh=kh;
        this.isPlowed = false;
        this.hasCrop = false;
        this.isOccupied = false;
        this.hasRock = false;
        setCropImages();

    }
    public void setCropImages(){
        Images cropImage =new Images(gp);
        cropImage.imagemapSet("/res/Tiles/Crops.png",4);
        cropImageMap =cropImage.tileImg;
    }

    /**
     * plows the current tile
     *
     * @param player the object player
     */
    public void plowTile(Player player) {
        if (!this.hasCrop || !this.isPlowed) {
            this.isPlowed = true;
            player.experience = player.experience + 0.5f;
        }

    }

    /**
     * adds to the water counter if there is a crop
     *
     * @param player the player
     */
    public void waterCrop(Player player) {
        if (this.hasCrop || this.Crop.waterCount < this.Crop.waterMax) {
            this.Crop.waterCount++;
            player.experience = player.experience + 0.5f;
        }
    }

    /**
     * adds to the fertilizer counter if there is a crop
     *
     * @param player the player
     */
    public void fertilizeCrop(Player player) {
        if (this.hasCrop || this.Crop.fertCount <= this.Crop.fertilizerMax) {
            this.Crop.fertCount++;
            player.coins = player.coins - 10;
            player.experience = player.experience + 4.0f;
        }
    }

    /**
     * harvests the crop in this tile and sets it to null after harvest
     *
     * @param player the player
     */
    public void harvestCrop(Player player) {
        if (this.Crop.isHarvestable && this.hasCrop&&!(Crop instanceof FruitTree)) {
            player.coins = player.coins + this.Crop.finalHarvestPrice;
            player.experience = player.experience + (this.Crop.harvestYield * this.Crop.expYield);
            this.Crop = new Crop();
            this.hasCrop = false;
            this.isPlowed = false;
        }
        else {
            player.coins = player.coins + this.Crop.finalHarvestPrice;
            player.experience = player.experience + (this.Crop.harvestYield * this.Crop.expYield);
        }
    }

    /**
     * gets the values of the crop
     *
     * @param currentDay current day
     */
    public void getCropStats(int currentDay) {
        if (this.hasCrop) {
            System.out.printf("""
                            Looking at %s
                            Amount of times watered %d
                            Amount of times fertilized %d
                            Days left until harvest %d
                            """,
                    this.Crop.name, this.Crop.waterCount, this.Crop.fertCount,
                    ((this.Crop.plantDate + this.Crop.harvestTime) - currentDay));
            if (!this.Crop.isWatered) {
                System.out.printf("This Plant needs %d more water\n", this.Crop.waterNeed - Crop.waterCount);
            }
            if (!this.Crop.isFertilized) {
                System.out.printf("This Plant needs %d more Fertilizer\n", Crop.fertilizerNeed - Crop.fertCount);
            }
        } else {
            System.out.println("This Tile doesnt have a crop");
        }
    }

    /**
     * initializes the crop object in this instance with the Crop(cropName,currentDay) constructor
     *
     * @param player     the player object
     * @param currentDay current day
     */
    public void plantCrop(Player player, int currentDay) {
        Crop tempCrop=new Crop();

        int choice = 1;
        switch (kh.cropChoice) {
            case 1 -> {
                cropName = "Turnip";
                tempCrop = new Crop(cropName, currentDay, cropImageMap);
                Crop.cropImg=cropImageMap[1][2];
            }
            case 2 -> {
                cropName = "Carrot";
                tempCrop = new Crop(cropName, currentDay, cropImageMap);
            }
            case 3 -> {
                cropName = "Potato";
                tempCrop = new Crop(cropName, currentDay, cropImageMap);
            }
            case 4 -> {
                cropName = "Rose";
                tempCrop = new Flower(cropName, currentDay, cropImageMap);
            }
            case 5 -> {
                cropName = "Tulip";
                tempCrop = new Flower(cropName, currentDay, cropImageMap);
            }
            case 6 -> {
                cropName = "Sunflower";
                tempCrop = new Flower(cropName, currentDay, cropImageMap);
            }
            case 7 -> {
                cropName = "Mango";
                tempCrop = new FruitTree(cropName, currentDay, cropImageMap);
            }
            case 8 -> {
                cropName = "Apple";
                tempCrop = new FruitTree(cropName, currentDay, cropImageMap);
            }
            default -> System.out.println("Invalid Choice");
        }
        if (!this.isPlowed) {
        } else if (this.hasCrop) {
        } else if (player.coins >= tempCrop.seedPrice) {
            String _class= String.valueOf(tempCrop.getClass());
            switch (_class){
                case "class GameProperties.Flower"->Crop=new Flower(cropName,currentDay,cropImageMap);
                case "class GameProperties.FruitTree"->Crop=new FruitTree(cropName,currentDay,cropImageMap);
                default -> Crop=new Crop(cropName,currentDay,cropImageMap);
            }
            player.coins = player.coins - tempCrop.seedPrice;
            this.hasCrop = true;
            this.isOccupied = true;
        } else {
        }
    }
}
