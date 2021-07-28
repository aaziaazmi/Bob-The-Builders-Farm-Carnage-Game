package main.java.View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EndScreen {

    private int width = 500;
    private int height = 500;
    private Button newGameButton;
    private Button exitButton;
    private boolean won;

    /**
     * Constructor for WelcomeScreen.
     */
    public EndScreen() {
        this(500, 500, false);
    }

    /**
     * Welcome Screen Constructor with width and height parameters.
     * @param width width of screen
     * @param height height of screen
     */
    public EndScreen(int width, int height, boolean won) {
        this.width = width;
        this.height = height;
        newGameButton = new Button("Start new game");
        exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });

        this.won = won;
    }

    /**
     * returns newGameButton
     * @return newGameButton
     */
    public Button getNewGameButton() {
        return this.newGameButton;
    }

    /**
     * get Welcome Screen scene with buttons and title
     * @return Welcome Screen scene
     */
    public Scene getScene() {
        AnchorPane root = new AnchorPane();

        Scene welcomeScene  = new Scene(root, 500, 500);
        welcomeScene.getStylesheets().add("file:resources/css/WelcomeScreen.css");

        VBox pane = new VBox();
        ImageView title = new ImageView("file:resources/media/Bob_the_Builders_Farm_Carnage.gif");

        pane.getChildren().add(title);
        if (won) {
            pane.getChildren().add(new Label("You reached $2000 and won!"));
        } else {
            pane.getChildren().add(new Label("Game Over!"));
        }
        pane.getChildren().add(newGameButton);
        pane.getChildren().add(exitButton);

        pane.setMargin(title, new Insets(10, 10, 10, 10));
        pane.setMargin(newGameButton, new Insets(10, 10, 10, 10));
        pane.setMargin(exitButton, new Insets(10, 10, 10, 10));

        root.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);

        return welcomeScene;
    }
}