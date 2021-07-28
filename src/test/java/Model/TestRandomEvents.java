package test.java.Model;

import main.java.Model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRandomEvents {

    private Farm farm;
    private Plot[] plots;

    @Before
    public void setup() {
        farm = new Farm();
        plots = farm.getPlots();
    }

    @Test
    public void testRain() {
        Plant plant1 = plots[0].getPlant();
        farm.forceRain();
        assertEquals(plant1.getWaterLevel(), 3);
    }

    @Test
    public void testDrought() {
        Plant plant1 = plots[0].getPlant();
        farm.forceDrought();
        assertEquals(1, plant1.getWaterLevel());
    }

    @Test
    public void testLocusts() {
        farm.forceLocusts();
        boolean dead = false;
        for (Plot p : plots) {
            if (p.getPlant() != null) {
                if (p.getPlant().getGrowth() == Plant.Growth.DEAD) {
                    dead = true;
                }
            }
        }
        assertEquals(true, dead);
    }

    @Test
    public void testCoolDown() {
        Plant plant1 = plots[0].getPlant();
        farm.incrementDay();
        assertEquals(1, plant1.getWaterLevel());
    }

    @Test
    public void testRainAndDrought() {
        Plant plant1 = plots[0].getPlant();
        farm.forceRain();
        farm.forceDrought();
        assertEquals(2, plant1.getWaterLevel());
    }
}
