package main.java.Model;

public class Crop extends Item {
    private PlantType plantType;
    private boolean pesticide;

    public Crop(PlantType plantType, int quantity) {
        super(quantity, 2 * plantType.getPrice());
        this.plantType = plantType;
    }

    public Crop(PlantType plantType, int quantity, boolean pesticide) {
        // super(quantity,  (int) ((double) 2 * plantType.getPrice() * 0.85));
        super(quantity, 2 * plantType.getPrice());
        this.plantType = plantType;
        this.pesticide = pesticide;
        if (pesticide) {
            this.setWorth((int) ((double) plantType.getPrice() * 0.85));
        }
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public String toString() {
        if (pesticide) {
            return plantType.toString() + " - Crop with pesticide";
        }
        return plantType.toString() + " - Crop";
    }

    public boolean equals(Crop other) {
        if (other.plantType == this.plantType) {
            return true;
        }
        return false;
    }
}