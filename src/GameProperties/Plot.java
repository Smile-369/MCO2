package GameProperties;

import Display.GamePanel;
import Display.Images;
import Display.KeyHandler;
import Entitiy.Player;

import java.awt.image.BufferedImage;

/**
 * this class is the Tile class where it can be plowed oe planted in
 */
public class Plot extends Tile {
    public Crop Crop = new Crop();
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
    public Plot() {

    }

    public Plot(GamePanel gp, KeyHandler kh) {
        Crop.isWithered = false;
        this.gp = gp;
        this.kh = kh;
        this.isPlowed = false;
        this.hasCrop = false;
        this.isOccupied = false;
        this.hasRock = false;
        setCropImages();

    }

    public void setCropImages() {
        Images cropImage = new Images(gp);
        cropImage.imagemapSet("/res/Tiles/Crops.png", 4);
        cropImageMap = cropImage.tileImg;
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
            player.message = "Tile Plowed";
        } else {
            player.message = "Tile is already plowed";
        }

    }

    /**
     * adds to the water counter if there is a crop
     *
     * @param player the player
     */
    public void waterCrop(Player player) {
        if (this.hasCrop || this.Crop.waterCount < this.Crop.waterMax + player.farmerType.waterBonusLimit) {
            this.Crop.waterCount++;
            player.experience = player.experience + 0.5f;
            player.message = String.format("%s has been watered", Crop.name);
        }
    }

    /**
     * adds to the fertilizer counter if there is a crop
     *
     * @param player the player
     */
    public void fertilizeCrop(Player player) {
        if (this.hasCrop || this.Crop.fertCount <= this.Crop.fertilizerMax + player.farmerType.ferBonusLimit) {
            this.Crop.fertCount++;
            player.coins = player.coins - 10;
            player.experience = player.experience + 4.0f;
            player.message = String.format("%s has been Fertilized", Crop.name);
        }
    }

    /**
     * harvests the crop in this tile and sets it to null after harvest
     *
     * @param player the player
     */
    public void harvestCrop(Player player) {
        if (this.Crop.isHarvestable && this.hasCrop && !(Crop instanceof FruitTree)) {
            player.coins = player.coins + this.Crop.finalHarvestPrice;
            player.experience = player.experience + (this.Crop.harvestYield * this.Crop.expYield) + player.farmerType.bonusEarning;
            player.message = String.format("You have harvested %d@%ss", Crop.harvestYield, Crop.name);
            this.Crop = new Crop();
            this.hasCrop = false;
            this.isPlowed = false;
        } else if (Crop instanceof FruitTree && Crop.isHarvestable) {
            player.coins = player.coins + this.Crop.finalHarvestPrice;
            player.experience = player.experience + (this.Crop.harvestYield * this.Crop.expYield) + player.farmerType.bonusEarning;
            player.message = String.format("You have harvested %d@%ss", Crop.harvestYield, Crop.name);
        } else if (!Crop.isHarvestable && hasCrop) {
            player.message = String.format("You cannot harvest the@%ss yet", Crop.name);
        } else {
            player.message = "There is no crop";
        }

    }

    public void checkPlantStats(int currentDay) {
        Crop.checkPlantStats(currentDay);
        if (Crop.isHarvestable) {
            switch (Crop.name) {
                case "Turnip" -> Crop.cropImg = cropImageMap[1][2];
                case "Carrot" -> Crop.cropImg = cropImageMap[1][4];
                case "Potato" -> Crop.cropImg = cropImageMap[1][6];
                case "Sunflower" -> Crop.cropImg = cropImageMap[2][2];
                case "Rose" -> Crop.cropImg = cropImageMap[2][4];
                case "Tulip" -> Crop.cropImg = cropImageMap[2][6];
                case "Mango" -> Crop.cropImg = cropImageMap[3][2];
                case "Apple" -> Crop.cropImg = cropImageMap[3][4];
            }
        }
        if (Crop.isWithered) {
            Crop.cropImg = cropImageMap[3][6];
        }
    }

    public void shovel(Player player) {
        if (this.Crop.isWithered && hasCrop) {
            this.Crop = new Crop();
            this.hasCrop = false;
            this.isPlowed = false;
            player.coins -= 7;
            player.experience += 2;
            player.message = "You have removed a withered@crop";
        } else {
            player.message = "There is no withered@crop";
            player.coins -= 7;
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
        switch (kh.cropChoice) {
            case 1 -> {
                cropName = "Turnip";
                tempCrop = new Crop(cropName, currentDay, cropImageMap);
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
            player.message = "Unable to plant Plot has crop in it";
        } else if (player.coins >= tempCrop.seedPrice) {
            String _class = String.valueOf(tempCrop.getClass());
            switch (_class) {
                case "class GameProperties.Flower" -> Crop = new Flower(cropName, currentDay, cropImageMap);
                case "class GameProperties.FruitTree" -> Crop = new FruitTree(cropName, currentDay, cropImageMap);
                default -> Crop = new Crop(cropName, currentDay, cropImageMap);
            }
            player.coins = player.coins - (tempCrop.seedPrice - player.farmerType.seedCostReduc);
            this.hasCrop = true;
            this.isOccupied = true;
            player.message = String.format("%s Planted", Crop.name);

        } else {
        }
    }
}
