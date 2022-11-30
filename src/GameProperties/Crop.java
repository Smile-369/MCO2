package GameProperties;

import java.util.Objects;

public class Crop {
    public int seedPrice;
    public float expYield;
    public int productionMax;
    public int productionMin;
    public int harvestYield;
    public int waterNeed;
    public int fertilizerNeed;
    public int waterMax;
    public int fertilizerMax;
    public int waterCount;
    public int fertCount;
    public int harvestTime;
    public int plantDate;
    public int sellingCost;
    public boolean isHarvestable;
    public boolean isWatered;
    public boolean hasBonus;
    public boolean isFertilized;
    public boolean isWithered;
    public int harvestTotal;
    public int finalHarvestPrice;
    public String type;
    public String name;

    /**
     * this constructor sets everything to null and sets the booleans to false
     */
    public Crop() {
        this.isHarvestable = false;
        this.isWatered = false;
        this.hasBonus = false;
        this.isFertilized = false;
        this.isWithered = false;
    }

    /**
     * sets the plant to withered
     *
     * @param withered if called with the string "withered" it sets the following values
     */
    public Crop(String withered) {
        if (Objects.equals(withered, "withered")) {
            this.name = "withered";
            this.isHarvestable = false;
            this.isWithered = true;
        }

    }

    /**
     * this constructor sets the values of crop depending on the values stated for each plant
     *
     * @param name      the name of the crop used for determining the values
     * @param plantDate the day which the crop is initialized
     */
    public Crop(String name, int plantDate) {
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
            case "Turnip" -> {
                this.seedPrice = 5;
                this.sellingCost = 6;
                this.expYield = 5;
                this.type = "Root Crop";
                this.productionMin = 1;
                this.productionMax = 2;
                this.harvestTime = 2;
                this.fertilizerNeed = 0;
                this.fertilizerMax = 1;
                this.waterMax = 2;
                this.waterNeed = 1;
            }
            case "Carrot" -> {
                this.seedPrice = 10;
                this.sellingCost = 9;
                this.expYield = 7.5f;
                this.type = "Root Crop";
                this.productionMin = 1;
                this.productionMax = 2;
                this.harvestTime = 3;
                this.fertilizerNeed = 0;
                this.fertilizerMax = 1;
                this.waterMax = 2;
                this.waterNeed = 1;
            }
            case "Potato" -> {
                this.seedPrice = 20;
                this.sellingCost = 3;
                this.expYield = 12.5f;
                this.type = "Root Crop";
                this.productionMin = 1;
                this.productionMax = 10;
                this.harvestTime = 5;
                this.fertilizerNeed = 1;
                this.fertilizerMax = 2;
                this.waterMax = 4;
                this.waterNeed = 3;
            }
        }
        this.harvestYield = getRandomNumber(productionMin, productionMax + 1);
        this.harvestTotal = this.harvestYield * this.sellingCost;
    }

    /**
     * this constructor generates the final harvest price
     */
    public void generateHarvestPrice() {
        float waterBonus, fertilizerBonus;
        if (this.isWatered) {
            waterBonus = ((float) harvestTotal * 0.2f * ((float) waterCount - 1));
        } else {
            waterBonus = 0;
        }
        if (this.isFertilized) {
            fertilizerBonus = (float) harvestTotal * 0.5f * (float) fertCount;
        } else {
            fertilizerBonus = 0;
        }
        this.finalHarvestPrice = (int) (harvestTotal + waterBonus + fertilizerBonus);

    }

    /**
     * returns a random integer within a range of numbers
     *
     * @param min the minimum valve of the for the range of random number
     * @param max the maximum value of the random number
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * checks the crops values, and calls the generateHarvestPrice method
     *
     * @param currentDay the current day value
     */
    public void checkPlantStats(int currentDay) {
        generateHarvestPrice();
        if (waterCount >= waterNeed) {
            this.isWatered = true;
        }
        if (fertCount >= fertilizerNeed) {
            this.isFertilized = true;
        }
        if (this.harvestTime + this.plantDate == currentDay && this.isWatered && this.isFertilized) {
            this.isHarvestable = true;
        }
        if (this.harvestTime + this.plantDate < currentDay || ((!isWatered || !isFertilized) && this.harvestTime + this.plantDate == currentDay)) {
            this.isHarvestable = false;
            this.isWithered = true;
        }
    }
}