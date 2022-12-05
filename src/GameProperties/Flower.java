package GameProperties;

import java.awt.image.BufferedImage;

public class Flower extends Crop{
    public Flower(String name, int plantDate, BufferedImage[][] cropImageMap){
        this.cropImageMap =cropImageMap;
        this.name = name;
        this.waterCount = 0;
        this.fertCount = 0;
        this.isHarvestable = false;
        this.isWatered = false;
        this.hasBonus = false;
        this.isFertilized = false;
        this.isWithered = false;
        this.plantDate = plantDate;
        switch (name) {
            case "Rose" -> {
                this.seedPrice = 5;
                this.sellingCost = 6;
                this.expYield = 5;
                this.productionMin = 1;
                this.productionMax = 2;
                this.harvestTime = 2;
                this.fertilizerNeed = 0;
                this.fertilizerMax = 1;
                this.waterMax = 2;
                this.waterNeed = 1;
                this.cropImg=cropImageMap[2][4];
            }
            case "Tulip" -> {
                this.seedPrice = 10;
                this.sellingCost = 9;
                this.expYield = 7.5f;
                this.productionMin = 1;
                this.productionMax = 2;
                this.harvestTime = 3;
                this.fertilizerNeed = 0;
                this.fertilizerMax = 1;
                this.waterMax = 2;
                this.waterNeed = 1;
                this.cropImg=cropImageMap[2][6];
            }
            case "Sunflower" -> {
                this.seedPrice = 20;
                this.sellingCost = 3;
                this.expYield = 12.5f;
                this.productionMin = 1;
                this.productionMax = 10;
                this.harvestTime = 5;
                this.fertilizerNeed = 1;
                this.fertilizerMax = 2;
                this.waterMax = 4;
                this.waterNeed = 3;
                this.cropImg=cropImageMap[2][2];
            }
        }
        this.harvestYield = getRandomNumber(productionMin, productionMax + 1);
        this.harvestTotal = this.harvestYield * this.sellingCost;
    }

    @Override
    public void generateHarvestPrice() {
        super.generateHarvestPrice();
        this.finalHarvestPrice=(int)(finalHarvestPrice*1.1f);
    }
}
