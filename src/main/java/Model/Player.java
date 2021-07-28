package main.java.Model;

public class Player {
    
    private String username;
    private int playerMoney;
    private Inventory inventory;
    private int harvestCap;
    private int waterCap;

    public Player(String username, int playerMoney, int inventory) {
        if (username == null) {
            throw new java.lang.IllegalArgumentException("Username cannot be null");
        }
        if (username.equals("")) {
            throw new java.lang.IllegalArgumentException("Username cannot be blank");
        }
        this.username = username;
        this.playerMoney = playerMoney;
        this.inventory = new Inventory(inventory);
        this.resetCap();
    }

    public Player(String username, int playerMoney) {
        this(username, playerMoney, 6);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new java.lang.IllegalArgumentException("Username cannot be null");
        }
        if (username.equals("")) {
            throw new java.lang.IllegalArgumentException("Username cannot be blank");
        }
        this.username = username;
    }

    //Sets harvest and water cap to appropriate starting values
    public void resetCap() {
        System.out.println("Starting money is " + playerMoney);
        this.harvestCap = this.playerMoney == 0 ? 3 : (this.playerMoney / 250);
        this.waterCap = harvestCap * 5;
        System.out.println("harvestCap: " + harvestCap + "\nwaterCap: " + waterCap);
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int money) {
        playerMoney = money;
    }

    public int getHarvestCap() {
        return harvestCap;
    }

    public void setHarvestCap(int cap) {
        this.harvestCap = cap;
    }

    public int getWaterCap() {
        return waterCap;
    }

    public void setWaterCap(int cap) {
        this.waterCap = cap;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
