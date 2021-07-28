package main.java.Model;

public class Fertilizer extends Item {
    public Fertilizer(int worth) {
        super(1, worth);
    }

    @Override
    public String toString() {
        return "Fertilizer";
    }
}
