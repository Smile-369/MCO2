package GameProperties;
import Display.GamePanel;
import Display.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.util.Scanner;
import  Entitiy.*;
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
    BufferedImage[][] cropImgs;
    GamePanel gp;


    Scanner Sc = new Scanner(System.in);

    /**
     * this constructor sets everything to false and initializes an empty crop
     */
    public Plot(GamePanel gp) {
        this.gp=gp;
        this.isPlowed = false;
        this.hasCrop = false;
        this.isOccupied = false;
        this.hasRock = false;
        setCropImages();
    }
    public void setCropImages(){
        Images cropImage =new Images(gp);
        cropImage.imagemapSet("/res/Tiles/Crops.png",4);
        cropImgs=cropImage.tileImg;
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
        } else {
        }

    }

    /**
     * adds to the water counter if there is a crop
     *
     * @param player the player
     * @param row    the row value
     * @param column the column value
     */
    public void waterCrop(Player player, int row, int column) {
        if (this.hasCrop || this.Crop.waterCount < this.Crop.waterMax) {
            System.out.printf("You have watered the %s in row %d column %d\n", this.Crop.name, row, column);
            this.Crop.waterCount++;
            player.experience = player.experience + 0.5f;
        } else {
            System.out.println("You can not water this tile");
        }
    }

    /**
     * adds to the fertilizer counter if there is a crop
     *
     * @param player the player
     * @param row    the row value
     * @param column the column value
     */
    public void fertilizeCrop(Player player, int row, int column) {
        if (this.hasCrop || this.Crop.fertCount <= this.Crop.fertilizerMax) {
            System.out.printf("You have fertilized the %s in row %d column %d\n", this.Crop.name, row, column);
            this.Crop.fertCount++;
            player.coins = player.coins - 10;
            player.experience = player.experience + 4.0f;
        } else {
            System.out.println("You can not fertilize this crop");
        }
    }

    /**
     * harvests the crop in this tile and sets it to null after harvest
     *
     * @param player the player
     * @param row    the row value
     * @param column the column value
     */
    public void harvestCrop(Player player, int row, int column) {
        if (this.Crop.isHarvestable && this.hasCrop) {
            player.coins = player.coins + this.Crop.finalHarvestPrice;
            player.experience = player.experience + (this.Crop.harvestYield * this.Crop.expYield);
            this.Crop = new Crop();
            this.hasCrop = false;
            this.isPlowed = false;
        } else if (!this.Crop.isHarvestable && this.hasCrop) {
        } else {
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

        int Choice = 2;
        switch (Choice) {
            case 1 -> {
                cropName = "Turnip";
                tempCrop = new Crop(cropName, currentDay,cropImgs);
            }
            case 2 -> {
                cropName = "Carrot";
                tempCrop = new Crop(cropName, currentDay,cropImgs);
            }
            case 3 -> {
                cropName = "Potato";
                tempCrop = new Crop(cropName, currentDay,cropImgs);
            }
            default -> System.out.println("Invalid Choice");
        }
        if (!this.isPlowed) {
        } else if (this.hasCrop) {
        } else if (player.coins >= tempCrop.seedPrice) {
            Crop = new Crop(cropName, currentDay,cropImgs);
            player.coins = player.coins - Crop.seedPrice;
            this.hasCrop = true;
            this.isOccupied = true;
        } else {
        }
    }
}
