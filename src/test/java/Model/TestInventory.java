package test.java.Model;
import main.java.Model.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestInventory {

    private static final int TIMEOUT = 200;
    private Inventory inventory;
    private Item[] expected;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;
    private Item testItem4;

    @Before
    public void setup() {
        inventory = new Inventory();
        expected = new Item[10];

        testItem1 = new Crop(PlantType.WHEAT, 1);
        testItem2 = new Crop(PlantType.CARROT, 2);
        testItem3 = new Seed(PlantType.POTATO, 4, 0);
        testItem4 = new Seed(PlantType.WHEAT, 2, 0);
    }

    @Test(timeout = TIMEOUT)
    public void testDefualtConstructor() {
        assertArrayEquals(expected, inventory.getItems());
        assertEquals(0, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        inventory = new Inventory(5);
        expected = new Item[5];

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(0, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        inventory.addItem(testItem1);
        expected[0] = testItem1;

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(1, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        inventory.setItem(2, testItem1);
        expected[2] = testItem1;

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(1, inventory.getSize());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexOutOfBounds() {
        inventory.setItem(10, testItem1);
    }

    @Test(timeout = TIMEOUT)
    public void testAddSeedAndCrop() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem3);
        expected[0] = testItem1;
        expected[1] = testItem3;

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(2, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testAddSemiFull() {
        inventory.setItem(0, testItem1);
        inventory.setItem(1, testItem3);
        inventory.setItem(3, testItem4);

        expected[0] = testItem1;
        expected[1] = testItem3;
        expected[3] = testItem4;

        // [testItem1, testItem3, __, testItem4, ... ]

        inventory.addItem(testItem2);
        expected[2] = testItem2;

        // [testItem1, testItem3, testItem2, testItem4, ... ]

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(4, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveIndex0() {
        inventory.addItem(testItem1);
        inventory.removeItem(0);

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(0, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithSet() {
        inventory.addItem(testItem1);
        inventory.setItem(0, null);

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(0, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithSetNothing() {
        inventory.addItem(testItem1);
        inventory.setItem(1, null);

        expected[0] = testItem1;

        assertArrayEquals(expected, inventory.getItems());
        assertEquals(1, inventory.getSize());
    }

    @Test(timeout = TIMEOUT)
    public void testSetHandItem() {
        inventory.addItem(testItem1);
        inventory.setHandItem(testItem1);

        assertEquals(testItem1, inventory.getHandItem());
    }
}