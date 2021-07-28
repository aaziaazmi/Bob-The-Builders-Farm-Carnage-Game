package main.java.Model;

public class Item {
    private int quantity;
    private int worth;

    public Item(int quantity, int worth) {
        if (quantity < 0) {
            throw new java.lang.IllegalArgumentException("Item quantity must be 0 or greater.");
        }
        this.worth = worth;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new java.lang.IllegalArgumentException("Item quantity must be 0 or greater.");
        }

        this.quantity = quantity;
    }

    public void setWorth(int worth) {
        if (worth < 0) {
            throw new java.lang.IllegalArgumentException("Item worth must be 0 or greater.");
        }

        this.worth = worth;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getWorth() {
        return worth; }
}
