package test.java.Model;
import main.java.Model.PlantType;
import main.java.Model.Seed;
import org.junit.Test;

public class TestSeed {

    private static final int TIMEOUT = 200;
    private Seed seed;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testSeedQuantityTooLow() {
        seed = new Seed(PlantType.POTATO, -1, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullSeedType() {
        seed = new Seed(null, 0, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testSetQuantityNull() {
        seed = new Seed(PlantType.POTATO, 0, 0);
        seed.setQuantity(-1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testSetSeedTypeNull() {
        seed = new Seed(PlantType.POTATO, 0,  0);
        seed.setSeedType(null);
    }

    @Test(timeout = TIMEOUT)
    public void testCorrectInput() {
        seed = new Seed(PlantType.POTATO, 0, 0);
    }
}