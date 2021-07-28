package main.java.Model;

public class Plot {

    private Farm farm;

    private boolean isFilled;
    private boolean isCleared;
    private FarmWorker worker;
    private Plant plant;

    /**
     * Plot constructor
     * @param farm the farm the plot belongs to.
     * @param plant the plant in the plot
     * @param worker the worker of the plot, if any
     */
    public Plot(Farm farm, Plant plant, FarmWorker worker) {
        this.farm = farm;
        this.plant = plant;
        this.isFilled = (plant != null);
        this.worker = worker;
        this.isCleared = false;
    }

    /**
     * Plot constructor
     * @param farm the farm the plot belongs to.
     */
    public Plot(Farm farm) {
        this(farm, null, null);
    }

    /**
     * Plants a new plant in the plot. If there is already a plant, replaces the current plant
     * and starts growth over.
     * @param plantedTick the game tick where the planting starts
     * @param plantType type of plant being planted
     * @throws IllegalAccessException when plant is null
     */
    public void plant(PlantType plantType, long plantedTick)
            throws IllegalAccessException {
        if (plant != null) {
            throw new IllegalAccessException("Cannot plant in a plot "
                    + "which already has a plant growing");
        }
        plant = new Plant(plantedTick, plantType, Plant.Growth.SEED);
        isFilled = true;
    }

    /**
     * Plants a random plant in the plot at a random stage of growth. If there is already a
     * plant in the plot, it will replace it.
     * @param plantedTick the game tick when the plant was placed.
     */
    public void fillRandomPlant(long plantedTick) {
        isFilled = true;
        // Note: this does not fill with any dead plants because the minus one stops it.
        plant = new Plant(plantedTick, PlantType.randomType(),
                Plant.Growth.values()[(int) (Math.random() * Plant.Growth.values().length - 1)]);
    }

    /**
     * Harvests the plant in the plot if it is exists and has grown fully
     * @return the harvested plant
     * @throws IllegalAccessException thrown if the plant is null or the plant hasn't fully grown
     */
    public PlantType harvest() throws IllegalAccessException {
        if (plant == null) {
            throw new IllegalAccessException("Cannot harvest empty plot");
        } else if (!plant.getGrowth().equals(Plant.Growth.GROWN)) {
            throw new IllegalAccessException("Cannot harvest "
                    + plant + " at stage " + plant.getGrowth());
        }

        PlantType harvested = plant.type();
        if (plant.fertilizerRNG()) {
            int old = harvested.getPrice();
            harvested.setPrice(old * 2);
        }
        plant = null;
        isFilled = false;
        return harvested;
    }

    public void clearDeadPlant() throws IllegalAccessException {
        if (plant == null) {
            throw new IllegalAccessException("Cannot harvest empty plot");
        } else if (!plant.getGrowth().equals(Plant.Growth.DEAD)) {
            throw new IllegalAccessException("Cannot harvest "
                    + plant + " at stage " + plant.getGrowth());
        }
        plant = null;
        isFilled = false;
    }

    public int updateDay() {
        if (plant != null) {
            int waterLevel = plant.updateDaily();
            if (worker != null) {
                isFilled = worker.updateWorker(plant, waterLevel);
                if (!isFilled) {
                    plant = null;
                }

                if (!worker.getIsActive()) {
                    worker = null;
                }
            }
            return waterLevel;
        }
        return 0;
    }

    /**
     * String method for use in visualizing plots
     * @return A string representing the plant in the plot, or empty
     */
    public String toString() {
        if (this.plant == null) {
            return "Empty plot";
        } else if (this.plant.getGrowth() == null) {
            return "Null growth!";
        }
        return this.plant.toString();
    }

    public Plant getPlant() {
        return plant;
    }

    public void setFarmWorker(FarmWorker worker) {
        this.worker = worker;
    }

    public boolean hasFarmWorker() {
        return this.worker != null;
    }

    public boolean getIsCleared() {
        return isCleared;
    }

    public void setIsCleared(boolean isCleared) {
        this.isCleared = isCleared;
    }
}
