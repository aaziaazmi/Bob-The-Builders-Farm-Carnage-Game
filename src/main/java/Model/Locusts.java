package main.java.Model;

import java.util.Random;

public class Locusts extends RandomEvent {
    //Use this variable to count until cool down is completed
    public Locusts(int coolDown) {
        super(coolDown);
    }
    /**
     * Function called during daily update that triggers random event based on cool down
     * (no idea why I re-implemented it)
     * @param farm farm on which update takes place
     * @return true if random event happened, false otherwise
     */
    public boolean updateDay(Farm farm) {
        if (getRelativeDay() == getCoolDown()) {
            Random randomize = new Random();
            if (randomize.nextInt(3) == 1) {
                makeEventHappen(farm);
                setRelativeDay(1);
                return true;
            }
            return false;
        }
        setRelativeDay(getRelativeDay() + 1);
        return false;
    }
    //Random Event
    private void makeEventHappen(Farm farm) {
        System.out.println("locuses attacked");
        Random randomize = new Random();
        Plot[] plots = farm.getPlots();
        for (Plot plot : plots) {
            Plant plant = plot.getPlant();
            if (plant != null) {
                int bound = 4;
                if (farm.getDifficulty() == Difficulty.NOOB) {
                    bound = 5;
                } else if (farm.getDifficulty() == Difficulty.CHAD) {
                    bound = 3;
                } else if (farm.getDifficulty() == Difficulty.ALPHACHAD) {
                    bound = 2;
                }
                int toKill = randomize.nextInt(bound);
                if (toKill == 1) {
                    plant.locustAttack();
                }
            }
        }
    }

    public void forceLocusts(Farm farm) {
        makeEventHappen(farm);
    }
}