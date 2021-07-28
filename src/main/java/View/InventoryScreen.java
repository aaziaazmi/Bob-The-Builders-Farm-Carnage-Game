package main.java.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.Model.Farm;
import main.java.Model.Inventory;
import main.java.Model.Item;

public class InventoryScreen {

    private int width;
    private int height;

    private Farm farm;
    private Inventory inventory;

    private Button backButton;
    private Button hand;
    private GridPane inventoryGrid;

    private boolean select;

    /**
     * Constructor for Inventory Screen.
     * @param width width of screen
     * @param height height of screen
     * @param farm starting farm
     */
    public InventoryScreen(int width, int height, Farm farm) {
        this.width = width;
        this.height = height;
        this.farm = farm;
        this.inventory = farm.getPlayer().getInventory();
        this.select = false;

        backButton = new Button("Back");
        if (this.inventory.getHandItem() == null) {
            hand = new Button("no item in hand");
        } else {
            hand = new Button(this.inventory.getHandItem().toString());
        }

        inventoryGrid = new GridPane();

        int inventorySide = getSquareSize(inventory.getLength());

        Item[] inventoryArray = inventory.getItems();

        for (int i = 0; i < inventorySide; i++) {
            for (int j = 0; j < inventorySide; j++) {
                String str = "empty";
                if (i * (inventorySide) + j < inventoryArray.length
                        && inventoryArray[i * (inventorySide) + j] != null) {
                    str = inventoryArray[i * (inventorySide) + j].getQuantity() + " "
                            + inventoryArray[i * (inventorySide) + j].toString();
                }
                Button newButton = new Button(str);
                newButton.setOnAction(e -> buttonHandlerInventory(newButton, inventorySide));
                inventoryGrid.add(newButton, i, j, 1, 1);
            }
        }
    }

    private void buttonHandlerInventory(Button button, int inventorySide) {
        if (!select) {
            return;
        }
        int i = GridPane.getColumnIndex(button);
        int j = GridPane.getRowIndex(button);

        inventory.setHandItem(inventory.getItem(i * (inventorySide) + j));
        inventory.setHandIndex(i * (inventorySide) + j);
        hand.setText(inventory.getHandItem().toString());
        select = false;
    }

    private int getSquareSize(int size) {
        int square = 1;

        while (square * square < size) {
            square++;
        }

        return square;
    }

    /**
     * Getter for back button.
     * @return back button
     */
    public Button getBackButton() {
        return backButton;
    }

    public GridPane getInventoryGrid() {
        return inventoryGrid;
    }

    /**
     * getter for Inventory Screen scene.
     * @return Game screen scene
     */
    public Scene getScene() {

        Label border = new Label("---------".repeat(getSquareSize(inventory.getLength())));
        Label underHand = new Label("Click on the hand button above to chose a new item to hold.");
        VBox topVBox = new VBox();

        hand.setOnMouseClicked(e -> {
            hand.setText("click on a new item to hold it");

            select = true;
        });



        topVBox.getChildren().addAll(inventoryGrid, border, hand, underHand);

        HBox bottomHBox = new HBox();
        Label playerMoney = new Label("     Money: " + farm.getPlayer().getPlayerMoney());
        bottomHBox.getChildren().addAll(backButton, playerMoney);

        // Create a border pane
        BorderPane rootPane = new BorderPane();
        // Place nodes in the pane
        rootPane.setTop(topVBox);
        rootPane.setBottom(bottomHBox);


        Scene scene = new Scene(rootPane, width, height);
        return scene;
    }
}
