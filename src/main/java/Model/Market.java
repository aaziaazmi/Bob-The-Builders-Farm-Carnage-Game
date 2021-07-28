package main.java.Model;

/**
 * Market class for Farm Simulator.
 */
public class Market {
    private boolean isOpen;
    private int favor;
    private Inventory inventory;
    private Farm farm;

    /**
     * Constructor for Market.
     * @param dif difficulty of the game.
     * @param favor how much market NPCs like player.
     * @param inventory inventory of Market.
     * @param farm the farm the market works with
     */
    public Market(Difficulty dif, int favor, Inventory inventory, Farm farm) {
        int edgeCases = favor > 0 ? 20 : -20;
        this.favor = favor > 20 || favor < -20 ? edgeCases : favor;
        this.inventory = inventory;
        this.farm = farm;
        this.defaultInventory();
    }

    private void defaultInventory() {
        this.inventory.clear();
        this.inventory.addItem(new Seed(PlantType.POTATO, 1, 0));
        this.inventory.addItem(new Seed(PlantType.CARROT, 1, 0));
        this.inventory.addItem(new Seed(PlantType.WHEAT, 1, 0));
        this.inventory.addItem(new FarmWorker(25
                + (1000 - farm.getDifficulty().getStartingMoney()) / 10, 1, 1, farm));
        this.inventory.addItem(new FarmWorker(50
                + (1000 - farm.getDifficulty().getStartingMoney()) / 10, 2, 1, farm));
        this.inventory.addItem(new Pesticide(20
                + (1000 - farm.getDifficulty().getStartingMoney()) / 10));
        this.inventory.addItem(new Fertilizer(25
                + (1000 - farm.getDifficulty().getStartingMoney()) / 10));
        this.inventory.addItem(new Tractor(100
                + (1000 - farm.getDifficulty().getStartingMoney()) / 10));
        this.inventory.addItem(new Irrigation(100
                + (1000 - farm.getDifficulty().getStartingMoney()) / 10));
    }

    public int getSellPrice(Item item) {
        return item.getWorth() + favor * 5;
    }

    public int getBuyPrice(Item item) {
        return item.getWorth() - favor * 5;
    }

    public boolean buyItem(int index, Player player) {
        int cropPrice = this.getBuyPrice(inventory.getItem(index));

        if (inventory.getItem(index) == null) {
            System.out.println("Item not in stock");
            return false;
        }

        if (cropPrice <= player.getPlayerMoney()) {
            if (player.getInventory().getSpaceLeft() >= 1) {
                //If the item is irrigation or a tractor, it is auto consumed
                if (inventory.getItem(index) instanceof Irrigation) {
                    player.setWaterCap(player.getWaterCap() + 5);
                    System.out.println("Water cap: " + player.getWaterCap());
                    player.setPlayerMoney(player.getPlayerMoney() - cropPrice);
                    return true;
                } else if (inventory.getItem(index) instanceof Tractor) {
                    player.setHarvestCap(player.getHarvestCap() + 2);
                    System.out.println("Harvest cap: " + player.getHarvestCap());
                    player.setPlayerMoney(player.getPlayerMoney() - cropPrice);
                    return true;
                }
                //add items
                player.getInventory().addItem(inventory.getItem(index));
                player.setPlayerMoney(player.getPlayerMoney() - cropPrice);
                return true;
            } else {
                System.out.println("Insufficient space");
            }
        } else {
            System.out.println("Insufficient funds");
        }
        return false;
    }

    public boolean sellItem(int index, Player player) {
        if (player.getInventory().getItem(index) != null) {
            int cropPrice = this.getBuyPrice(player.getInventory().getItem(index));
            player.setPlayerMoney(player.getPlayerMoney() + cropPrice);
            player.getInventory().removeItem(index);
            return true;
        } else {
            System.out.println("Invalid item");
            return false;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
