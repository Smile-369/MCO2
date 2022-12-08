package GameProperties;


import java.awt.image.BufferedImage;

public class FruitTree extends Crop {
    public FruitTree(String name, int plantDate, BufferedImage[][] cropImages) {
        this.name = name;
        this.cropImageMap = cropImages;
        this.waterCount = 0;
        this.fertCount = 0;
        this.isHarvestable = false;
        this.isWatered = false;
        this.hasBonus = false;
        this.isFertilized = false;
        this.isWithered = false;
        this.plantDate = plantDate;
        this.harvestTime = 10;
        this.waterMax = 14;
        this.waterNeed = 7;
        switch (name) {
            case "Apple" -> {
                this.seedPrice = 200;
                this.sellingCost = 5;
                this.expYield = 25;
                this.productionMin = 10;
                this.productionMax = 15;
                this.fertilizerNeed = 5;
                this.fertilizerMax = 10;
                this.cropImg = cropImageMap[3][1];
            }
            case "Mango" -> {
                this.seedPrice = 100;
                this.sellingCost = 8;
                this.expYield = 25;
                this.productionMin = 5;
                this.productionMax = 15;
                this.fertilizerNeed = 4;
                this.fertilizerMax = 8;
                this.cropImg = cropImageMap[3][1];
            }
        }
        this.harvestYield = getRandomNumber(productionMin, productionMax + 1);
        this.harvestTotal = this.harvestYield * this.sellingCost;
    }

}
