package main.java.Model;

import java.util.Random;

public class Rain extends RandomEvent {

    public Rain(int coolDown) {
        super(coolDown, 3);
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
        System.out.println("it rained in makeEvent");
        Random randomize = new Random();
        int rainAmount = randomize.nextInt(3);
        Plot[] plots = farm.getPlots();
        for (Plot plot : plots) {
            Plant plant = plot.getPlant();
            if (plant != null) {
                for (int i = 1; i <= 1; i++) {
                    plant.waterPlot();
                }
            }
        }
    }

    public void forceRain(Farm farm) {
        makeEventHappen(farm);
    }
}
