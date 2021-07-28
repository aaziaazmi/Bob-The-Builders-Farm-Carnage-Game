package main.java.View;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.java.Model.*;

public class GameScreen {

    private int width;
    private int height;

    private int plotSize;

    private Farm farm;

    private Button backButton;
    private Button marketButton;
    private Button inventoryButton;

    private Button advanceDayButton;

    private Button rainButton;
    private Button droughtButton;
    private Button locustsButton;

    private int randomEventId;

    private boolean isAllEmpty;

    /**
     * Constructor for Game Screen.
     * @param width width of screen
     * @param height height of screen
     * @param plotSize the plotSize
     * @param farm starting farm
     * @param randomEventId the random event's id pog
     */
    public GameScreen(int width, int height, int plotSize, Farm farm, int randomEventId) {
        this.width = width;
        this.height = height;
        this.farm = farm;
        this.plotSize = plotSize;
        this.randomEventId = randomEventId;

        backButton = new Button("Back");
        marketButton = new Button("Market");
        inventoryButton = new Button("Inventory");

        advanceDayButton = new Button("Day+");

        rainButton = new Button("R");
        droughtButton = new Button("D");
        locustsButton = new Button("L");

        isAllEmpty = false;
    }

    /**
     * Getter for back button.
     * @return back button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Getter for market button.
     * @return market button
     */
    public Button getMarketButton() {
        return marketButton;
    }

    /**
     * Getter for inventory button.
     * @return inventory button
     */
    public Button getInventoryButton() {
        return inventoryButton;
    }

    /**
     * Getter for advance day button.
     * @return advance day button
     */
    public Button getAdvanceDayButton() {
        return advanceDayButton;
    }

    public Button getRainButton() {
        return rainButton;
    }

    public Button getDroughtButton() {
        return droughtButton;
    }

    public Button getLocustsButton() {
        return  locustsButton;
    }

    public void setRandomEventId(int randomEventId) {
        this.randomEventId = randomEventId;
    }

    public int getRandomEventId() {
        return randomEventId;
    }

    /**
     * getter for Game Screen scene with starting money and day.
     * @return Game screen scene
     */
    public Scene getScene() {
        // Top bar including game stats
        Label moneyLabel = new Label("Money: ");
        Label moneyValue = new Label("$" + farm.getPlayer().getPlayerMoney());
        HBox leftBox = new HBox();
        leftBox.getChildren().addAll(moneyLabel, moneyValue);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        Label dateLabel = new Label("Date: ");
        Label dateValue = new Label("Day " + farm.getDay());
        HBox rightBox = new HBox();
        rightBox.getChildren().addAll(dateLabel, dateValue);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        // Fill plots as labels
        HBox rows = new HBox(50);

        rows.getChildren().addAll(leftBox, rightBox); // grabbed these later for top of screen
        rows.setAlignment(Pos.CENTER);

        HBox bottomHbox = new HBox(15);
        bottomHbox.setPadding(new Insets(20, 20, 20, 20));
        bottomHbox.getChildren().addAll(backButton, marketButton, inventoryButton,
                advanceDayButton, rainButton, droughtButton, locustsButton);

        StackPane farm = getFarmGraphics(4, 4);
        farm.setAlignment(Pos.CENTER);

        // Create a border pane
        BorderPane rootPane = new BorderPane();
        // Place nodes in the pane
        //rootPane.setCenter(midTop);
        rootPane.setTop(rows);
        rootPane.setCenter(farm);
        rootPane.setBottom(bottomHbox);

        return new Scene(rootPane, width, height);
    }

    private StackPane getFarmGraphics(int numCols, int numRows) {
        ImageView background = new ImageView("file:resources/media/GrassBackground.jpg");
        background.setFitHeight(this.height * 0.8);
        background.setFitWidth(this.width * 0.95);

        StackPane farmStack = new StackPane(background);
        GridPane farmGrid = new GridPane();
        farmGrid.setPrefHeight(this.height * 0.75);
        farmGrid.setPrefWidth(this.width * 0.90);

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHalignment(HPos.CENTER);
            colConst.setPercentWidth(100.0 / numCols);
            farmGrid.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setValignment(VPos.CENTER);
            rowConst.setPercentHeight(100.0 / numRows);
            farmGrid.getRowConstraints().add(rowConst);
        }

        int k = 0;

        isAllEmpty = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                farmGrid.add(getPlotPane(k), i, j);
                k++;
            }
        }

        farmStack.setMargin(farmGrid, new Insets(30, 30, 30, 30));
        farmStack.getChildren().add(farmGrid);
        if (isRaining()) {
            ImageView image = new ImageView("file:resources/media/rain.gif");
            image.setFitWidth(this.height * 0.95);
            image.setFitHeight(this.width * 0.8);
            farmStack.getChildren().add(image);
        }
        if (isDrought()) {
            ImageView image = new ImageView("file:resources/media/drought.gif");
            image.setFitWidth(this.height * 0.9);
            image.setFitHeight(this.width * 0.8);
            image.setOpacity(0.5);
            farmStack.getChildren().add(image);
        }
        if (isLocust()) {
            ImageView image = new ImageView("file:resources/media/bees.gif");
            image.setFitWidth(this.height * 0.95);
            image.setFitHeight(this.width * 0.8);
            image.setOpacity(1);
            farmStack.getChildren().add(image);
        }

        return farmStack;
    }

    private HBox getPlotPane(int k) {
        //System.out.println(k);

        Plot[] plots = farm.getPlots();

        StackPane plotStack = freshPlot();
        VBox verticalPane = new VBox();
        HBox horizontalPane = new HBox();

        verticalPane.getChildren().add(plotStack);
        horizontalPane.getChildren().add(verticalPane);

        plotStack.setOnMouseClicked(mouseEvent -> {
            try {
                //redeclare variable as final for lambda expression
                if (plots[k].getIsCleared()) {
                    if (plots[k].getPlant() == null) {

                        if (farm.getPlayer().getInventory().getHandItem() != null) {
                            plots[k].plant(((Seed) farm.getPlayer().getInventory().getHandItem())
                                    .getSeedType(), 0);
                            farm.getPlayer().getInventory()
                                    .removeItem(farm.getPlayer().getInventory().getHandIndex());
                            addWaterGraphics(verticalPane, plotStack, k);
                            addFertilizerGraphics(horizontalPane, plotStack, k);
                            addPesticideGraphics(verticalPane, plotStack, k);
                        }

                    } else {
                        if (plots[k].getPlant().getGrowth().equals(Plant.Growth.DEAD)) {
                            plots[k].clearDeadPlant();
                            removePlantGraphics(horizontalPane, verticalPane, plotStack);
                        } else if (plots[k].getPlant().getGrowth().equals(Plant.Growth.GROWN)) {
                            if (farm.harvest()) {
                                boolean pesticide = plots[k].getPlant().getPesticide();
                                PlantType p = plots[k].harvest();
                                if (pesticide) {
                                    farm.getPlayer().getInventory().addItem(new Crop(p, 1, true));
                                } else {
                                    farm.getPlayer().getInventory().addItem(new Crop(p, 1, false));
                                }
                                removePlantGraphics(horizontalPane, verticalPane, plotStack);

                            } else {
                                Popup.display("You have hit daily harvest limit :(");
                            }
                        } else if (farm.getPlayer().getInventory().getHandItem() instanceof FarmWorker
                                && !plots[k].hasFarmWorker()) {
                            plots[k].setFarmWorker(
                                    (FarmWorker) farm.getPlayer().getInventory().getHandItem());
                            addFarmWorkerGraphics(plotStack, k);
                            farm.getPlayer().getInventory().removeItem(
                                    farm.getPlayer().getInventory().getHandIndex());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        if (plots[k].getIsCleared()) {
            if (plots[k].getPlant() != null) {
                addWaterGraphics(verticalPane, plotStack, k);
                addFertilizerGraphics(horizontalPane, plotStack, k);
                addPesticideGraphics(verticalPane, plotStack, k);
                if (plots[k].hasFarmWorker()) {
                    addFarmWorkerGraphics(plotStack, k);
                }

                isAllEmpty = true;
            }
        } else {
            addUnclearPlotGraphics(plotStack, k);
        }

        verticalPane.setAlignment(Pos.BASELINE_CENTER);
        horizontalPane.setAlignment(Pos.CENTER);

        return horizontalPane;
    }

    private void addUnclearPlotGraphics(StackPane plotStack, int k) {
        plotStack.getChildren().add(getPlotLabel(k));
        Button debris = new Button();
        debris.setPadding(Insets.EMPTY);
        ImageView debrisImg = new ImageView("file:resources/media/plot.png");
        debrisImg.setFitHeight(40);
        debrisImg.setFitWidth(35);
        debris.setGraphic(debrisImg);

        plotStack.getChildren().add(debris);
    }

    private void addWaterGraphics(VBox verticalPane, StackPane plotStack, int k) {
        plotStack.getChildren().add(getPlotLabel(k));
        Plot[] plots = farm.getPlots();
        Button water = new Button();
        water.setPadding(Insets.EMPTY);
        water.setGraphic(getWaterImage(plots[k].getPlant().getWaterLevel()));
        water.setOnMouseClicked(mouseEvent -> {
            if (farm.water()) {
                plots[k].getPlant().waterPlot();
                plotStack.getChildren().clear();
                plotStack.getChildren().add(freshPlot());
                plotStack.getChildren().add(getPlotLabel(k));
                water.setGraphic(getWaterImage(plots[k].getPlant().getWaterLevel()));
            } else {
                Popup.display("You have hit watering limit for a day :(");
            }

        });
        verticalPane.getChildren().add(water);
    }

    private void addFertilizerGraphics(HBox horizontalPane,
                                       StackPane plotStack, int k) {
        plotStack.getChildren().add(getPlotLabel(k));
        Plot[] plots = farm.getPlots();
        Button fertilizer = new Button();
        fertilizer.setPadding(Insets.EMPTY);
        fertilizer.setGraphic(getFertilizerImage(plots[k].getPlant().getFertilizerLevel()));
        fertilizer.setOnMouseClicked(mouseEvent -> {
            if (farm.getPlayer().getInventory().getHandItem() instanceof Fertilizer) {
                plots[k].getPlant().fertilizePlot();
                plotStack.getChildren().clear();
                plotStack.getChildren().add(freshPlot());
                plotStack.getChildren().add(getPlotLabel(k));
                fertilizer.setGraphic(getFertilizerImage(
                        plots[k].getPlant().getFertilizerLevel()));

                farm.getPlayer().getInventory().removeItem(
                        farm.getPlayer().getInventory().getHandIndex());
            }
        });
        horizontalPane.getChildren().addAll(fertilizer);
    }

    private void addPesticideGraphics(VBox verticalPane, StackPane plotStack, int k) {
        plotStack.getChildren().add(getPlotLabel(k));
        Plot[] plots = farm.getPlots();
        Button pesticide = new Button("Pesticide: " + plots[k].getPlant().getPesticide());
        pesticide.setPadding(Insets.EMPTY);
        pesticide.setOnMouseClicked(mouseEvent -> {
            if (farm.getPlayer().getInventory().getHandItem() instanceof Pesticide) {
                System.out.println("pesticide attempted!!!");
                plots[k].getPlant().usePesticide();
                pesticide.setText("Pesticide: " + plots[k].getPlant().getPesticide());
                /*
                plotStack.getChildren().clear();
                plotStack.getChildren().add(freshPlot());
                plotStack.getChildren().add(getPlotLabel(k));
                pesticide.setGraphic(getFertilizerImage(plots[k].getPlant().getFertilizerLevel()));
                 */

                farm.getPlayer().getInventory().removeItem(
                        farm.getPlayer().getInventory().getHandIndex());
            }
        });
        verticalPane.getChildren().addAll(pesticide);
    }

    private void addFarmWorkerGraphics(StackPane plotStack, int k) {
        plotStack.getChildren().add(getPlotLabel(k));
        Button dude = new Button();
        dude.setPadding(Insets.EMPTY);
        ImageView dudeImg = new ImageView("file:resources/media/farmer pickles.png");
        dudeImg.setFitHeight(40);
        dudeImg.setFitWidth(35);
        dude.setGraphic(dudeImg);
        /*
        dude.setOnMouseClicked(mouseEvent -> {
            plots[k].getPlant().waterPlot();
            plotStack.getChildren().clear();
            plotStack.getChildren().add(freshPlot());
            plotStack.getChildren().add(getPlotLabel(k));
        });
         */

        plotStack.getChildren().add(dude);
    }

    private void removePlantGraphics(HBox horizontalPane, VBox verticalPane, StackPane plotStack) {
        plotStack.getChildren().clear();
        plotStack.getChildren().add(freshPlot());
        verticalPane.getChildren().clear();
        verticalPane.getChildren().add(plotStack);
        horizontalPane.getChildren().clear();
        horizontalPane.getChildren().add(verticalPane);
    }

    private Label getPlotLabel(int k) {
        Plot[] plots = farm.getPlots();
        if (plots[k].getPlant() == null) {
            return new Label("");
        }
        return new Label(plots[k].toString() + "\nWATER: "
                + plots[k].getPlant().getWaterLevel() + "/4");
    }

    private StackPane freshPlot() {
        Rectangle rect = new Rectangle(plotSize / 2, plotSize / 2);
        rect.setFill(Color.BROWN);

        return new StackPane(rect);
    }

    private ImageView getWaterImage(int water) {
        if (water == 5) {
            water = 4;
        }
        String imageName = "file:resources/media/water" + String.valueOf(water) + ".jpg";
        ImageView image = new ImageView(imageName);
        image.setFitHeight(25);
        image.setFitWidth(70);
        return image;
    }

    private ImageView getFertilizerImage(int level) {
        if (level > 10) {
            level = 10;
        } else if (level < 0) {
            level = 0;
        }
        String imageName = "file:resources/media/FertilizerImages/fertilizer_level_"
                + String.valueOf(level) + ".png";
        ImageView image = new ImageView(imageName);
        image.setFitHeight(80);
        image.setFitWidth(20);
        return image;
    }

    private boolean isRaining() {
        return randomEventId == 1 || randomEventId == 3 || randomEventId == 5 || randomEventId == 7;
    }

    private boolean isDrought() {
        return randomEventId == 2 || randomEventId == 3 || randomEventId == 6 || randomEventId == 7;
    }

    private boolean isLocust() {
        return randomEventId == 4 || randomEventId == 5 || randomEventId == 6 || randomEventId == 7;
    }

    public boolean getIsAllEmpty() {
        return isAllEmpty;
    }

}