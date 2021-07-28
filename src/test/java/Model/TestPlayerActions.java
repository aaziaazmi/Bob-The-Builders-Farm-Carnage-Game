package test.java.Model;

import main.java.Model.Farm;
import main.java.Model.Player;
import main.java.Model.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPlayerActions {

    private Farm farm;
    private Plot[] plots;
    private Player player;

    @Before
    public void setup() {
        player = new Player("Bob", 0);
        farm = new Farm();
        farm.setPlayer(player);
        plots = farm.getPlots();
    }

    @Test
    public void testWaterLimit() {
        for (int i = 0; i < 15; i++) {
            farm.water();
        }
        assertTrue(!farm.water());
    }

    @Test
    public void testHarvestLimit() {
        for (int i = 0; i < 5; i++) {
            farm.harvest();
        }
        assertTrue(!farm.harvest());
    }

    @Test
    public void testNoLoseAtFirst() {
        farm.incrementDay();
        farm.incrementDay();
        farm.incrementDay();
    }

    @Test
    public void testPurchasePlot() {
        farm.incPlotCount();
        farm.incPlotCount();
        farm.incPlotCount();
        farm.setPlots(farm.generatePlots(true));
        assertTrue(farm.getPlots()[10].getIsCleared());
    }

    @Test
    public void testNotAvaliablePlot() {
        assertTrue(!farm.getPlots()[10].getIsCleared());
    }
}
