package main.java.Model;

public class Inventory {

    private Item[] items;
    private int size;

    private Item handItem;
    private int handIndex;

    /**
     * Creates a new inventory of size 10
     */
    public Inventory() {
        this(10);
    }

    /**
     * Creates a new inventory
     * @param capacity capacity of the inventory - default is 10
     */
    public Inventory(int capacity) {
        items = new Item[capacity];
    }

    /**
     * Returns the item stored in index of the inventory
     * @param index index to access
     * @return item from index
     */
    public Item getItem(int index) {
        if (index < 0 || index >= items.length) {
            throw new IndexOutOfBoundsException("Index must be within range of 0 to "
                    + (items.length - 1));
        }
        return items[index];
    }

    /**
     * Sets an index in inventory
     * @param index index to set
     * @param item item to place in inventory
     */
    public void setItem(int index, Item item) {
        if (!(item instanceof Item) && item != null) {
            throw new IllegalArgumentException("Can only insert Items.");
        }
        if (index < 0 || index >= items.length) {
            throw new IndexOutOfBoundsException("Index must be within range of 0 to "
                    + (items.length - 1));
        }
        if (items[index] == null && item != null) {
            size++;
        } else if (items[index] != null && item == null) {
            size--;
        }
        items[index] = item;
    }

    /**
     * Adds an item to the next available inventory space
     * @param item item to add
     * @return True if the item was added, false if there was no space
     */
    public boolean addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can only insert Items.");
        }
        if (!(item instanceof Item)) {
            throw new IllegalArgumentException("Can only insert Items.");
        }
        if ((size + 1) > items.length) {
            return false;
        }

        int firstNull = -1;

        for (int i = 0; i < items.length; i++) {

            if (items[i] == null) {
                if (firstNull == -1) {
                    firstNull = i;
                }
            } else if (items[i].toString().equals(item.toString())) {
                //replace with ItemType in the future
                items[i].setQuantity(items[i].getQuantity() + item.getQuantity());
                return true;
            }
        }

        if (firstNull == -1) {
            return false;
        }

        items[firstNull] = item;
        size++;
        return true;
    }

    /**
     * Removes an item from inventory (sets it to null)
     * @param index index to remove
     * @return the removed item (null if nothing is removed)
     */
    public Item removeItem(int index) {
        if (index < 0 || index >= items.length) {
            throw new IndexOutOfBoundsException("Index must be within range of 0 to "
                    + (items.length - 1));
        }

        if (items[index] == null) {
            return null;
        }
        Item temp = items[index];
        
        int quantity = items[index].getQuantity();
        if (quantity > 1) {
            items[index].setQuantity(quantity - 1);
        } else {
            if (index == handIndex) {
                handItem = null;
            }
            items[index] = null;
            size--;
        }

        return temp;
    }

    /**
     * Changes the inventory size. Accounts for shifting the items. In shifting
     * inventory becomes a contiguous block of items.
     * Must be >= size of the inventory
     * @param capacity the new capacity
     */
    public void setCapacity(int capacity) {
        if (capacity < size) {
            throw new IllegalArgumentException("Capacity must be at least equal to current size: "
                    + size);
        }

        Item[] newItems = new Item[capacity];

        for (int i = 0, j = 0; i < size; j++) {
            if (items[j] != null) {
                newItems[i] = items[j];
                i++;
            }
        }

        items = newItems;
    }

    /**
     * Get the capacity of the inventory
     * @return int of capacity
     */
    public int getCapacity() {
        return items.length;
    }

    /**
     * Clears the inventory
     */
    public void clear() {
        items = new Item[items.length];
        size = 0;
    }

    /**
     * USE WITH CAUTION
     * Gets all items
     * @return backing array of inventory
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * USE WITH CAUTION
     * Sets the backing array to a new array of items
     * @param items the new inventory array
     */
    public void setItems(Item[] items) {
        if (items.length != this.items.length) {
            throw new IllegalArgumentException("Array size must stay the same.");
        }

        this.items = items;
    }

    /**
     * Get the number of items in the inventory
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Return the space left in inventory.
     * @return remaining space in inventory.
     */
    public int getSpaceLeft() {
        return items.length - size;
    }

    public int getLength() {
        return items.length; }

    public Item getHandItem() {
        return handItem;
    }
    public void setHandItem(Item handItem) {
        this.handItem = handItem;
    }

    public int getHandIndex() {
        return handIndex;
    }
    public void setHandIndex(int handIndex) {
        this.handIndex = handIndex;
    }
}
