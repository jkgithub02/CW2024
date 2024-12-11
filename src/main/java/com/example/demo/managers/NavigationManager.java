package com.example.demo.managers;

import com.example.demo.config.GameConfig;
import com.example.demo.controller.gameControllers.GameController;
import com.example.demo.controller.gameControllers.GameOverController;
import com.example.demo.controller.MainMenuController;
import com.example.demo.controller.gameControllers.WinScreenController;
import com.example.demo.levels.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;

/**
 * Manages navigation between different screens and levels in the game.
 */
public class NavigationManager {
    /**
     * The current scene of the game.
     */
    private final Scene currentScene;

    /**
     * The width of the game screen.
     */
    private final double screenWidth = GameConfig.SCREEN_WIDTH;

    /**
     * The height of the game screen.
     */
    private final double screenHeight = GameConfig.SCREEN_HEIGHT;

    /**
     * Constructs a NavigationManager with the specified current scene.
     *
     * @param currentScene the current scene of the game.
     */
    public NavigationManager(Scene currentScene) {
        this.currentScene = currentScene;
    }

    /**
     * Restarts the specified level.
     *
     * @param currentLevelClass the class of the current level to restart.
     */
    public void restartLevel(Class<?> currentLevelClass) {
        try {
            Constructor<?> constructor = currentLevelClass.getConstructor();
            LevelParent newLevel = (LevelParent) constructor.newInstance();

            // Get stage from current scene
            Stage stage = (Stage) currentScene.getWindow();

            newLevel.nextLevelProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    String[] levelInfo = newValue.split(",");
                    GameController gameController = new GameController(stage);
                    gameController.goToLevel(levelInfo[0], levelInfo[1]);
                } catch (Exception e) {
                    showErrorAlert(e);
                }
            });

            Scene newScene = newLevel.initializeScene();
            stage.setScene(newScene);
            newLevel.startGame();
        } catch (Exception e) {
            showErrorAlert(e);
        }
    }

    /**
     * Navigates to the main menu screen.
     */
    public void goToMainMenu() {
        loadScreen("MenuScreen.fxml", MainMenuController.class);
    }

    /**
     * Displays the win screen.
     */
    public void showWinScreen() {
        loadScreen("WinScreen.fxml", WinScreenController.class);
    }

    /**
     * Displays the game over screen and sets the current level class.
     *
     * @param levelClass the class of the current level.
     */
    public void showGameOverScreen(Class<?> levelClass) {
        GameOverController controller = loadScreen("GameOver.fxml", GameOverController.class);
        if (controller != null) {
            controller.setCurrentLevelClass(levelClass);
        }
    }

    /**
     * Loads the specified FXML screen and sets the controller.
     *
     * @param fxmlName the name of the FXML file to load.
     * @param controllerType the class of the controller.
     * @param <T> the type of the controller.
     * @return the controller of the loaded screen.
     */
    private <T> T loadScreen(String fxmlName, Class<T> controllerType) {
        try {
            URL fxmlLocation = getClass().getClassLoader().getResource(fxmlName);
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            root.prefWidth(screenWidth);
            root.prefHeight(screenHeight);
            Scene scene = new Scene(root, screenWidth, screenHeight);

            Stage stage = (Stage) currentScene.getWindow();
            T controller = loader.getController();
            if (controller != null) {
                if (controller instanceof MainMenuController) {
                    ((MainMenuController) controller).setStage(stage);
                } else if (controller instanceof GameOverController) {
                    ((GameOverController) controller).setStage(stage);
                } else if (controller instanceof WinScreenController) {
                    ((WinScreenController) controller).setStage(stage);
                }
            }
            stage.setScene(scene);
            return controller;
        } catch (IOException e) {
            showErrorAlert(e);
            return null;
        }
    }

    /**
     * Displays an error alert with the specified exception message.
     *
     * @param e the exception to display.
     */
    private void showErrorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.show();
        e.printStackTrace();
    }
}