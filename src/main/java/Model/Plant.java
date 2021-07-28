package main.java.Model;

public class Plant {

    public enum Growth { SEED, GROWTH1, GROWTH2, GROWTH3, GROWN, DEAD }

    private long plantedTick;
    private PlantType plant;
    private Growth growth;
    private int[] stageLens;
    private int currStageDay;
    private int waterLevel;
    private int fertilizerLevel;
    private boolean pesticide;

    public Plant(long plantedTick, PlantType plant, Growth growth) {
        this.plantedTick = plantedTick;
        this.plant = plant;
        this.growth = growth;
        this.waterLevel = 2;
        this.fertilizerLevel = 0;
        this.pesticide = false;

        // Prepare each growth stage's length
        this.stageLens = new int[growth.values().length];
        for (int i = 0; i < growth.values().length - 2; i++) {
            // Each stage is at least 2 days, and at most 6 currently
            this.stageLens[i] = 2 + (int) (Math.random() * 5);
        }
        // GROWN crops keep for 3 days by default
        this.stageLens[growth.values().length - 2] = 3;
        // DEAD crops never change state!
        this.stageLens[growth.values().length - 1] = -1;
        this.currStageDay = 1;

        //System.out.println("New plant " + plant + " made! Growth Stages:");
        //for (int i : stageLens) System.out.println("    :" + i);
    }

    public void makeSeed() {
        return;
    }

    public Growth getGrowth() {
        return growth;
    }

    public PlantType type() {
        return this.plant;
    }

    public void changePlantType() {
        //Handling pesticides
        if (plant.equals(PlantType.WHEAT)) {
            System.out.println("Wheat plant has pesticide");
        } else if (plant.equals(PlantType.POTATO)) {
            System.out.println("Potato is now pesticided...");
        } else {
            System.out.println("Carrot pesticided!");
        }
    }

    public boolean getPesticide() {
        return pesticide;
    }

    public void usePesticide() {
        if (!pesticide) {
            this.changePlantType();
        }
        this.pesticide = true;
    }

    public int getFertilizerLevel() {
        return this.fertilizerLevel;
    }

    public int getWaterLevel() {
        return this.waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int updateDaily() {
        // If water level or fertilizer level already zero, don't decrement.
        waterLevel = waterLevel == 0 ? waterLevel : waterLevel - 1;
        fertilizerLevel = fertilizerLevel == 0 ? fertilizerLevel : fertilizerLevel - 1;

        this.updateGrowthState();
        this.checkWaterLevel();
        return waterLevel;
    }

    // ideally the plot would hold the water level and fertilizer level, not the crop.
    public int waterPlot() {
        waterLevel = waterLevel == 5 ? waterLevel : waterLevel + 1;
        this.checkWaterLevel();
        return waterLevel;
    }

    // ideally the plot would hold the water level and fertilizer level, not the crop.
    public int drainPlotWater() {
        waterLevel = waterLevel == 0 ? waterLevel : waterLevel - 1;
        this.checkWaterLevel();
        return waterLevel;
    }

    public void locustAttack() {
        this.growth = pesticide ? this.growth : Growth.DEAD;
    }

    public int fertilizePlot() {
        fertilizerLevel++;
        return fertilizerLevel;
    }

    public boolean fertilizerRNG() {
        // Rolls a d10, weights with fertilizer level for
        // chance to skip growth stages / yield extra crop
        return (int) (Math.random() * 5) + 1 <= fertilizerLevel;
    }

    private void updateGrowthState() {
        // Advance one growth stage if stage length passed,
        // unless plant's dead. Possible boost with fertilizer
        if (currStageDay >= stageLens[growth.ordinal()] || fertilizerRNG()) {
            if (!growth.equals(Growth.DEAD)) {
                this.growth = Growth.values()[(growth.ordinal() + 1) % Growth.values().length];
                this.currStageDay = 1;
            }
        } else {    // Increase current stage's day count if not passed yet
            this.currStageDay++;
        }
    }

    private void checkWaterLevel() {
        // Kill the plant if the water level is inappropriate
        if (waterLevel < 1 || waterLevel > 4) {
            this.growth = Growth.DEAD;
        }
    }

    @Override
    public String toString() {
        /* if (pesticide) {
            return plant + " " + growth + ", pesticide";
        }
         */
        return plant + " " + growth;
    }

    public int updateDay() {
        return 0;
    }
}
