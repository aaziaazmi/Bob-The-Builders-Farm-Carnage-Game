package test.java.Model;
import main.java.Model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlant {

    private static final int TIMEOUT = 200;

    private Plant plant1;
    private Plant plant2;
    private Plant plant3;

    @Before
    public void setup() {
        plant1 = new Plant(0, PlantType.WHEAT, Plant.Growth.SEED);
        plant2 = new Plant(0, PlantType.POTATO, Plant.Growth.GROWN);
        plant3 = new Plant(0, PlantType.CARROT, Plant.Growth.DEAD);
    }

    @Test (timeout = TIMEOUT)
    public void testPlantType() {
        assertEquals(PlantType.WHEAT, plant1.type());
        assertEquals(PlantType.POTATO, plant2.type());
        assertEquals(PlantType.CARROT, plant3.type());
    }

    @Test (timeout = TIMEOUT)
    public void testGrowth() {
        assertEquals(Plant.Growth.SEED, plant1.getGrowth());
        assertEquals(Plant.Growth.GROWN, plant2.getGrowth());
        assertEquals(Plant.Growth.DEAD, plant3.getGrowth());
    }

    @Test (timeout = TIMEOUT)
    public void testUpdateDaily() {
        plant1.updateDaily();
        plant2.updateDaily();
        plant3.updateDaily();

        //assertEquals(Plant.Growth.GROWTH1, plant1.getGrowth());
        //assertEquals(Plant.Growth.DEAD, plant2.getGrowth());
        assertEquals(Plant.Growth.DEAD, plant3.getGrowth());
    }

    @Test (timeout = TIMEOUT)
    public void testWaterLevel() {
        plant1.waterPlot();
        plant1.waterPlot();
        plant1.waterPlot();
        plant1.waterPlot();
        plant1.waterPlot();

        assertEquals(5, plant1.getWaterLevel());
        assertEquals(Plant.Growth.DEAD, plant1.getGrowth());
    }

}
