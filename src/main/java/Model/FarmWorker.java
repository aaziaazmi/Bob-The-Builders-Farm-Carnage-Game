package main.java.Model;

public class FarmWorker extends Item {
    private int tier;
    private Farm farm;
    private boolean isActive;

    public FarmWorker(int pay, int tier, int quantity, Farm farm) {
        super(quantity, pay);
        this.tier = tier;
        this.farm = farm;
        isActive = true;
    }

    public int getTier() {
        return this.tier;
    }

    public boolean updateWorker(Plant plant, int waterLevel) {
        if (tier == 2) {
            if (waterLevel == 1) {
                plant.waterPlot();
            }
        }

        if (farm.getPlayer().getPlayerMoney() - this.getWorth() < 0) {
            isActive = false;
        } else {
            farm.getPlayer().setPlayerMoney(farm.getPlayer().getPlayerMoney() - this.getWorth());
        }

        if (plant.getGrowth() == Plant.Growth.GROWN) {
            System.out.println(farm);
            farm.getPlayer().setPlayerMoney(
                    farm.getPlayer().getPlayerMoney() + plant.type().getPrice());
            return false;
        }

        return true;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    @Override
    public String toString() {
        return "Farm Worker, Tier " + tier;
    }
}