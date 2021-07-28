package main.java.Model;

public enum Difficulty {
    NOOB("Noob", 1000),
    TRYHARD("Try Hard", 750),
    CHAD("Chad", 500),
    ALPHACHAD("ALPHA CHAD", 250);

    private String name;
    private int startingMoney;

    Difficulty(String name, int startingMoney) {
        this.name = name;
        this.startingMoney = startingMoney;
    }

    public String getName() {
        return name;
    }

    public int getStartingMoney() {
        return startingMoney;
    }
}