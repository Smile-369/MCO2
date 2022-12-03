package GameProperties;
import Display.Images;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import  Entitiy.*;
/**
 * this class is the Tile class where it can be plowed oe planted in
 */
public class Plot extends Tile {
    public Crop Crop;
    public boolean isPlowed;
    public String cropName;
    public boolean hasRock;
    public boolean isOccupied;
    public boolean hasCrop;
    Images img;

    Scanner Sc = new Scanner(System.in);

    /**
     * this constructor sets everything to false and initializes an empty crop
     */
    public Plot() {
        this.Crop = new Crop();
        this.isPlowed = false;
        this.hasCrop = false;
        this.isOccupied = false;
        this.hasRock = false;

    }

    /**
     * plows the current tile
     *
     * @param player the object player
     * @param row    the row value
     * @param column the column value
     */
    public void plowTile(Player player, int row, int column) {
        if (!this.hasCrop || !this.isPlowed) {
            System.out.printf("You have plowed the tile in row %d column %d\n", row, column);
            this.isPlowed = true;

            player.experience = player.experience + 0.5f;
        } else {
            System.out.println("This tile has been plowed");
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
            System.out.println("This Crop isn't harvestable");
        } else {
            System.out.println("This Tile is Empty");
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
        Crop tempCrop = new Crop();
        System.out.println("Choose Which Crop to Plant");
        System.out.println("1. Turnip 5 Objectcoins");
        System.out.println("2. Carrot 10 Objectcoins");
        System.out.println("3. Potato 20 Objectcoins");
        int Choice = Sc.nextInt();
        switch (Choice) {
            case 1 -> {
                cropName = "Turnip";
                tempCrop = new Crop(cropName, currentDay);
            }
            case 2 -> {
                cropName = "Carrot";
                tempCrop = new Crop(cropName, currentDay);
            }
            case 3 -> {
                cropName = "Potato";
                tempCrop = new Crop(cropName, currentDay);
            }
            default -> System.out.println("Invalid Choice");
        }
        if (!this.isPlowed) {
            System.out.println("Unable to plant in an un-plowed tile");
        } else if (this.hasCrop) {
            System.out.println("Unable to plant in an occupied tile");
        } else if (player.coins >= tempCrop.seedPrice) {
            Crop = new Crop(cropName, currentDay);
            player.coins = player.coins - Crop.seedPrice;
            this.hasCrop = true;
            this.isOccupied = true;
        } else {
            System.out.println("You dont have enough coins");
        }
    }
}
