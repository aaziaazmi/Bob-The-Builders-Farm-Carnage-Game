package main.java.View;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.Model.*;

public class ConfigScreen {

    private final Farm farm;
    private int width;
    private int height;
    private Button backButton;
    private Button resetDefault;
    private Button saveConfig;
    private Button startButton;
    private Label helperLabel;
    private String gameDifficulty;
    private ChoiceBox<String> difficultySelector;
    private String gameSeason;
    private ChoiceBox<String> seasonSelector;
    private String gameSeed;
    private ChoiceBox<String> seedSelector;
    private String username;
    private TextField usernameSelector;
    private Label usernameLabel;
    private Label difficultyLabel;
    private Label seasonLabel;
    private Label seedLabel;
    private Label nameLabel;
    private TextField nameField;
    private String difficulty;
    private String season;
    private String seed;

    /**
     * Constructor for Config Screen.
     * @param width width of the screen
     * @param height height of the screen
     * @param farm player's farm
     */
    public ConfigScreen(int width, int height, Farm farm) {
        this.width = width;
        this.height = height;
        this.farm = farm;
        backButton = new Button("Back");
        startButton = new Button("Start Game");
        resetDefault = new Button("Reset to Default");
        saveConfig = new Button("Save");
        helperLabel = new Label("Please select game difficulty and initial season");

        //UserName Text Field
        usernameLabel = new Label("Name");
        usernameSelector = new TextField();

        //Difficulty Dropdown
        difficultyLabel = new Label("Game Difficulty");
        difficultySelector = new ChoiceBox<>();

        difficultySelector.getItems().add("Difficulty");
        Difficulty[] difficulties = Difficulty.values();
        for (Difficulty dif : difficulties) {
            difficultySelector.getItems().add(dif.toString());
        }
        //Starting Season Dropdown
        seasonLabel = new Label("Starting Season");
        seasonSelector = new ChoiceBox<>();

        seasonSelector.getItems().add("Season");
        Season[] seasons = Season.values();
        for (Season seas : seasons) {
            seasonSelector.getItems().add(seas.toString());
        }

        //Starting Seed Dropdown
        seedLabel = new Label("Seed Type");
        seedSelector = new ChoiceBox<>();

        seedSelector.getItems().add("Seed");
        PlantType[] seeds = PlantType.values();
        for (PlantType seedy : seeds) {
            seedSelector.getItems().add(seedy.toString());
        }

        difficulty = "Try Hard";
        season = "Spring";
        seed = "Wheat";

        nameField = new TextField();
        nameLabel = new Label("Name:");

        //difficultySelector.getItems().addAll("DIFFICULTY", "NOOB", "TRYHARD", "CHAD", "ALPHACHAD")
        //Starting Season Dropdown
        seasonLabel = new Label("Starting Season");
        seasonSelector = new ChoiceBox<>();
        seasonSelector.getItems().addAll("SEASON", "SPRING", "SUMMER", "FALL", "WINTER", "RANDOM");

        //Initial clear
        this.reset();
        saveConfig.setOnAction(e -> save());
        resetDefault.setOnAction(e -> reset());
    }

    /**
     * Method for resetting configuration settings.
     */
    private void reset() {
        //difficultySelector.setValue("Difficulty");
        difficultySelector.setValue(farm.getDifficulty().toString());
        //seasonSelector.setValue("Season");
        seasonSelector.setValue(farm.getSeason().toString());
        seedSelector.setValue(farm.getSeedType().toString());
        if (farm.getPlayer() != null) {
            usernameSelector.setText(farm.getPlayer().getUsername());
        }

        difficulty = "Try Hard";
        season = "Spring";
        seed = "Wheat";
    }

    /**
     * Method for saving configuration settings.
     */
    private void save() {
        difficulty = difficultySelector.getValue();
        season = seasonSelector.getValue();
        seed = seedSelector.getValue();

        try {
            Player player = farm.getPlayer();
            if (player == null) {
                player = new Player("New", 1, 10);
                farm.setPlayer(player);
            }

            player.setUsername(usernameSelector.getText());

            if (difficulty == null || difficulty.equals("Difficulty") || season == null
                    || season.equals("Season") || seed == null || seed.equals("Seed")) {
                helperLabel.setText("Invalid difficulty/season/seed combination. "
                        + "Please try again.");
            } else {
                farm.setSeason(Season.valueOf(seasonSelector.getValue()));
                farm.setDifficulty(Difficulty.valueOf(difficultySelector.getValue()));
                farm.setSeedType(PlantType.valueOf(seedSelector.getValue()));
                farm.getPlayer().setPlayerMoney(farm.getDifficulty().getStartingMoney());
                farm.getPlayer().resetCap();

                if (farm.getPlayer().getInventory() == null) {
                    farm.getPlayer().setInventory(new Inventory());
                }

                Seed firstSeed = new Seed(PlantType.valueOf(seedSelector.getValue()), 1, 0);
                farm.getPlayer().getInventory().clear();
                farm.getPlayer().getInventory().addItem(firstSeed);
                //season = seasonSelector.getValue();
                //difficulty = difficultySelector.getValue();
                helperLabel.setText("Successfully saved.");
            }

        } catch (java.lang.IllegalArgumentException e) {
            System.out.println(e.getMessage());
            helperLabel.setText("Invalid player name. Please try again.");
        }
    }

    /**
     * getter for Back Button.
     * @return Back Button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Getter for reset Button.
     * @return reset Button
     */
    public Button getResetButton() {
        return resetDefault;
    }

    /**
     * getter for save button.
     * @return save button
     */
    public Button getSaveButton() {
        return saveConfig;
    }

    /**
     * getter for helper label.
     * @return helper label
     */
    public Label getHelperLabel() {
        return helperLabel;
    }

    /**
     * getter for game difficulty.
     * @return game difficulty
     */
    public String getGameDifficulty() {
        return difficulty;
    }

    /**
     * getter for starting season.
     * @return starting season
     */
    public String getStartingSeason() {
        return season;
    }

    /**
     * getter for start button.
     * @return start button
     */
    public Button getStartButton() {
        return startButton;
    }

    //Way more getters

    /**
     * get Config Screen scene with buttons and labels added.
     * @return scene with buttons and labels
     */
    public Scene getScene() {
        HBox centerHBox = new HBox(105);

        HBox hbox = new HBox(15);
        hbox.setPadding(new Insets(20, 20, 20, 20));
        hbox.getChildren().addAll(backButton, resetDefault, saveConfig, startButton);

        HBox hbox2 = new HBox(15);
        hbox2.setAlignment(Pos.CENTER_RIGHT);
        hbox2.getChildren().addAll(startButton);

        centerHBox.getChildren().addAll(hbox, hbox2);

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(usernameLabel, usernameSelector, difficultyLabel,
                difficultySelector, seasonLabel, seasonSelector,
                seedLabel, seedSelector, helperLabel);

        // Create a border pane
        BorderPane pane = new BorderPane();
        // Place nodes in the pane
        pane.setCenter(vbox);
        pane.setTop(new CustomPane("Player Config Screen"));
        pane.setBottom(centerHBox);

        // Create scene to put in stage
        Scene scene = new Scene(pane, width, height);
        return scene;
    }
    class CustomPane extends StackPane {
        /**
         * Constructor for custom pan
         * @param title title of pane
         */
        public CustomPane(String title) {
            getChildren().add(new Label(title));
            setStyle("-fx-border-color: blue");
            setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        }
    }
}


