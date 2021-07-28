package main.java.Model;

public class Pesticide extends Item {
    public Pesticide(int worth) {
        super(1, worth);
    }

    @Override
    public String toString() {
        return "Pesticide";
    }
}
