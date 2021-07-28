package main.java.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.Controller.GameController;
import main.java.Model.Farm;
import main.java.Model.Item;
import main.java.Model.Market;

public class MarketScreen {

    private int width;
    private int height;

    private Farm farm;
    private Market market;

    private GameController controller;

    private Button backButton;
    private Button plotButton;
    private GridPane inventoryGrid;
    private GridPane shopGrid;
    private int shopSide;

    /**
     * Constructor for Game Screen.
     * @param width width of screen
     * @param height height of screen
     * @param shopSize number of tiles in the shop grid
     * @param farm starting farm
     * @param market the market
     * @param controller the controller
     */
    public MarketScreen(int width, int height, int shopSize,
                        Farm farm, Market market, GameController controller) {
        this.width = width;
        this.height = height;
        this.farm = farm;
        this.market = market;
        this.controller = controller;

        backButton = new Button("Back");

        plotButton = new Button("Extra Plot - $100");

        shopSide = getSquareSize(shopSize);

        InventoryScreen screen = new InventoryScreen(width, height, farm);

        // inventoryGrid = screen.getInventoryGrid();

        shopGrid = new GridPane();
        Item[] shopArray = market.getInventory().getItems();

        for (int i = 0; i < shopSide; i++) {
            for (int j = 0; j < shopSide; j++) {
                String str = "empty";
                if (i * (shopSide) + j < shopArray.length
                        && shopArray[i * (shopSide - 1) + j] != null) {
                    str = "$" + shopArray[i * (shopSide) + j].getWorth() + " "
                            + shopArray[i * (shopSide) + j].toString();
                }
                Button newButton = new Button(str);
                newButton.setOnAction(e -> buttonHandlerShop(newButton, shopSide));
                shopGrid.add(newButton, i, j, 1, 1);
            }
        }

        inventoryGrid = new GridPane();
        int inventorySide = getSquareSize(farm.getPlayer().getInventory().getLength());
        Item[] inventoryArray = farm.getPlayer().getInventory().getItems();

        for (int i = 0; i < inventorySide; i++) {
            for (int j = 0; j < inventorySide; j++) {
                String str = "empty";
                if (i * (inventorySide) + j < inventoryArray.length
                        && inventoryArray[i * (inventorySide) + j] != null) {
                    str = "$" + inventoryArray[i * (inventorySide) + j].getWorth() + ", "
                            + inventoryArray[i * (inventorySide) + j].getQuantity() + " "
                            + inventoryArray[i * (inventorySide) + j].toString();
                }
                Button newButton = new Button(str);
                newButton.setOnAction(e -> buttonHandlerInventory(newButton, inventorySide));
                inventoryGrid.add(newButton, i, j, 1, 1);
            }
        }
    }

    private void buttonHandlerShop(Button button, int shopSide) {
        int i = GridPane.getColumnIndex(button);
        int j = GridPane.getRowIndex(button);

        System.out.println(market.buyItem(i * (shopSide) + j, farm.getPlayer()));

        controller.callMarketRefresh();
    }

    private void buttonHandlerInventory(Button button, int inventorySide) {
        int i = GridPane.getColumnIndex(button);
        int j = GridPane.getRowIndex(button);

        System.out.println(market.sellItem(i * (inventorySide) + j, farm.getPlayer()));

        controller.callMarketRefresh();
    }

    /**
     * Getter for back button.
     * @return back button
     */
    public Button getBackButton() {
        return backButton;
    }

    // public GridPane getGrid(){ return grid; }

    private int getSquareSize(int size) {
        int square = 1;

        while (square * square < size) {
            square++;
        }

        return square;
    }

    public Button getPlotButton() {
        return plotButton;
    }

    /**
     * getter for Game Screen scene with starting money and day.
     * @return Game screen scene
     */
    public Scene getScene() {
        
        String mid = "";

        for (int i = 0; i < Math.max(shopSide, inventoryGrid.getColumnCount()) * 2; i++) {
            mid += " --- \n";
        }

        Label shopLabel = new Label("Shop:");

        Label border = new Label("---------".repeat(shopSide));

        VBox shopBox = new VBox();
        shopBox.getChildren().addAll(shopLabel, shopGrid, border, plotButton);

        Label midLabel = new Label(mid);

        Label inventoryLabel = new Label("Inventory:");

        VBox inventoryBox = new VBox();
        inventoryBox.getChildren().addAll(inventoryLabel, inventoryGrid);

        HBox topHBox = new HBox();
        topHBox.getChildren().addAll(shopBox, midLabel, inventoryBox);
        topHBox.setAlignment(Pos.CENTER);

        Label playerMoney = new Label("     Money: " + farm.getPlayer().getPlayerMoney());

        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().addAll(backButton, playerMoney);

        // Create a border pane
        BorderPane rootPane = new BorderPane();
        // Place nodes in the pane
        rootPane.setTop(topHBox);
        rootPane.setBottom(bottomHBox);

        Scene scene = new Scene(rootPane, width, height);
        return scene;
    }
}
