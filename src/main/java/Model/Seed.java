package main.java.Model;

public class Seed extends Item {
    private PlantType plantType;

    public Seed(PlantType seedType, int quantity, int worth) {
        super(quantity, worth);
        if (seedType == null) {
            throw new java.lang.IllegalArgumentException("Seed Type cannot be null.");
        }
        this.plantType = seedType;
        this.setWorth(plantType.getPrice());
    }

    public void setSeedType(PlantType plantType) {
        if (plantType == null) {
            throw new java.lang.IllegalArgumentException("Seed Type cannot be null.");
        }

        this.plantType = plantType;
    }

    public PlantType getSeedType() {
        return this.plantType;
    }

    public String toString() {
        return "" + plantType.getName() + " Seed";
    }

    public boolean equals(Seed other) {
        if (other.plantType == this.plantType) {
            return true;
        }
        return false;
    }
}
