package main.java.View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeScreen {

    private int width = 500;
    private int height = 500;
    private static Button startButton;
    private static Button optionsButton;
    private static Button exitButton;

    /**
     * Constructor for WelcomeScreen.
     */
    public WelcomeScreen() {
        this(500, 500);
    }

    /**
     * Welcome Screen Constructor with width and height parameters.
     * @param width width of screen
     * @param height height of screen
     */
    public WelcomeScreen(int width, int height) {
        this.width = width;
        this.height = height;
        startButton = new Button("Start Game");
        optionsButton = new Button("Options");
        exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * getter for Start Button.
     * @return Start Button
     */
    public Button getStartButton() {
        return startButton;
    }

    /**
     * getter for Options button.
     * @return Options button
     */
    public Button getOptionsButton() {
        return optionsButton;
    }

    /**
     * getter for exit button.
     * @return exit button
     */
    public Button getExitButton() {
        return exitButton;
    }

    /**
     * get Welcome Screen scene with buttons and title
     * @return Welcome Screen scene
     */
    public static Scene getScene() {
        AnchorPane root = new AnchorPane();

        Scene welcomeScene  = new Scene(root, 500, 500);
        welcomeScene.getStylesheets().add("file:resources/css/WelcomeScreen.css");

        VBox pane = new VBox();
        ImageView title = new ImageView("file:resources/media/Bob_the_Builders_Farm_Carnage.gif");

        pane.getChildren().add(title);
        pane.getChildren().add(startButton);
        pane.getChildren().add(optionsButton);
        pane.getChildren().add(exitButton);
        pane.setMargin(title, new Insets(10, 10, 10, 10));
        pane.setMargin(startButton, new Insets(10, 10, 10, 10));
        pane.setMargin(optionsButton, new Insets(10, 10, 10, 10));
        pane.setMargin(exitButton, new Insets(10, 10, 10, 10));

        root.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);

        return welcomeScene;
    }
}
