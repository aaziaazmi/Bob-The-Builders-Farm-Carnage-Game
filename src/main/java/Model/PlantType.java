package main.java.Model;

public enum PlantType {
    WHEAT("Wheat", 200, 10, Season.SPRING),
    CARROT("Carrot", 300, 15, Season.SUMMER),
    POTATO("Potato", 400, 20, Season.FALL);


    private String name;
    private int price;
    private int ticksToGrow;
    private Season idealSeason;

    private int sellingPrice;
    private int attack;
    private int health;

    PlantType(String name, int price, int ticksToGrow, Season idealSeason) {
        this.name = name;
        this.price = price;
        this.ticksToGrow = ticksToGrow;
        this.idealSeason = idealSeason;
    }

    PlantType(String name, int price) {
        this(name, price, 60, Season.SPRING);
    }

    PlantType(PlantType other, int price) { }

    public void setPrice(int newPrice) {
        this.price = newPrice;
    }

    public String getName() {
        return name;
    }

    public static PlantType randomType() {
        return values()[(int) (Math.random() * values().length)];
    }

    public int getPrice() {
        return price;
    }
}
