// Test for webhook

package main.java.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.Model.Farm;
import main.java.Model.Inventory;
import main.java.Model.Market;
import main.java.View.*;

public class GameController extends Application {

    private Stage mainWindow;
    private Farm farm;
    private Market market;
    private SoundController soundControl;
    private static boolean mute = false;
    private final int width = 600;
    private final int height = 600;

    @Override
    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        primaryStage.setTitle("Farm View");
        farm = new Farm();
        soundControl = new SoundController("./resources/media/Bobs Farm Carnage.mp3");
        welcomeView();
    }

    private void welcomeView() {
        WelcomeScreen welcomeScreen = new WelcomeScreen(width, height);

        if (!mute) {
            soundControl.playSound("./resources/media/Bobs Farm Carnage.mp3");
        }

        Button startButton = welcomeScreen.getStartButton();
        Button optionButton = welcomeScreen.getOptionsButton();

        startButton.setOnAction(e -> gameView());
        optionButton.setOnAction(e -> configView());

        setMainWindow(welcomeScreen.getScene());
    }

    private void configView() {
        ConfigScreen configScreen = new ConfigScreen(width, height, farm);

        soundControl.stop();

        Button backButton = configScreen.getBackButton();
        Button startButton = configScreen.getStartButton();

        backButton.setOnAction(e -> welcomeView());
        startButton.setOnAction(e -> gameView());

        setMainWindow(configScreen.getScene());
    }

    private void gameView() {
        GameScreen gameScreen = new GameScreen(width, height, 100, farm, 0);

        if (!mute) {
            soundControl.playSound("./resources/media/Terraria_Team_69.mp3");
        }

        Button backButton = gameScreen.getBackButton();
        backButton.setOnAction(e -> welcomeView());

        Button marketButton = gameScreen.getMarketButton();
        marketButton.setOnAction(e -> marketView());

        Button inventoryButton = gameScreen.getInventoryButton();
        inventoryButton.setOnAction(e -> inventoryView());

        Button advanceDayButton = gameScreen.getAdvanceDayButton();
        advanceDayButton.setOnAction(e -> {
            gameScreen.setRandomEventId(farm.incrementDay());

            if (!gameScreen.getIsAllEmpty() && farm.getPlayer().getPlayerMoney() == 0) {
                endView(false);
            } else if (farm.getPlayer().getPlayerMoney() >= 2000) {
                endView(true);
            } else {
                setMainWindow(gameScreen.getScene());
            }
        });


        Button rainButton = gameScreen.getRainButton();
        rainButton.setOnAction(e -> {
            int randomEventId = gameScreen.getRandomEventId();
            gameScreen.setRandomEventId(farm.forceRain());
            setMainWindow(gameScreen.getScene());
        });

        Button droughtButton = gameScreen.getDroughtButton();
        droughtButton.setOnAction(e -> {
            int randomEventId = gameScreen.getRandomEventId();
            gameScreen.setRandomEventId(farm.forceDrought());
            setMainWindow(gameScreen.getScene());
        });

        Button locustsButton = gameScreen.getLocustsButton();
        locustsButton.setOnAction(e -> {
            int randomEventId = gameScreen.getRandomEventId();
            gameScreen.setRandomEventId(gameScreen.getRandomEventId() + farm.forceLocusts());
            setMainWindow(gameScreen.getScene());
        });

        setMainWindow(gameScreen.getScene());
    }

    private void marketView() {
        market = new Market(farm.getDifficulty(), 0, new Inventory(9), farm);

        MarketScreen marketScreen = new MarketScreen(width, height, 6, farm, market, this);

        Button backButton = marketScreen.getBackButton();

        backButton.setOnAction(e -> gameView());

        Button plotButton = marketScreen.getPlotButton();
        plotButton.setOnAction(e -> {
            if (farm.getPlotCount() < 16) {
                if (farm.getPlayer().getPlayerMoney() > 100) {
                    farm.getPlots()[farm.getPlotCount()].setIsCleared(true);
                    farm.incPlotCount();
                    farm.getPlayer().setPlayerMoney(farm.getPlayer().getPlayerMoney() - 100);
                    marketView();
                }
            }
        });

        setMainWindow(marketScreen.getScene());
    }

    private void inventoryView() {
        InventoryScreen inventoryScreen = new InventoryScreen(width, height, farm);

        Button backButton = inventoryScreen.getBackButton();

        backButton.setOnAction(e -> gameView());

        setMainWindow(inventoryScreen.getScene());
    }

    private void endView(boolean won) {
        EndScreen endScreen = new EndScreen(width, height, won);

        Button newGameButton = endScreen.getNewGameButton();

        newGameButton.setOnAction(e -> start(mainWindow));

        setMainWindow(endScreen.getScene());
    }

    private void setMainWindow(Scene scene) {
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public void callMarketRefresh() {
        marketView();
    }

    /**
     * Launches application
     * @param args input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
