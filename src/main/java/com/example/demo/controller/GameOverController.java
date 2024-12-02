package com.example.demo.controller;

import com.example.demo.config.GameConfig;
import com.example.demo.managers.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller class for handling the game over screen.
 */
public class GameOverController {
    @FXML
    private StackPane gameOverRoot;
    private Stage stage;
    private Class<?> currentLevelClass;

    /**
     * Sets the stage for this controller.
     *
     * @param stage the primary stage of the application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the current level class.
     *
     * @param levelClass the class of the current level.
     */
    public void setCurrentLevelClass(Class<?> levelClass) {
        this.currentLevelClass = levelClass;
    }

    /**
     * Gets the screen width from the game configuration.
     *
     * @return the screen width.
     */
    public double getScreenWidth() {
        return GameConfig.SCREEN_WIDTH;
    }

    /**
     * Gets the screen height from the game configuration.
     *
     * @return the screen height.
     */
    public double getScreenHeight() {
        return GameConfig.SCREEN_HEIGHT;
    }

    /**
     * Initializes the game over screen by setting the root pane dimensions.
     */
    @FXML
    public void initialize() {
        // Make sure the root pane fills the entire screen
        gameOverRoot.setPrefWidth(GameConfig.SCREEN_WIDTH);
        gameOverRoot.setPrefHeight(GameConfig.SCREEN_HEIGHT);
    }

    /**
     * Handles the restart action by restarting the current level.
     */
    @FXML
    private void handleRestart() {
        NavigationManager navigationManager = new NavigationManager(
                gameOverRoot.getScene()
        );
        navigationManager.restartLevel(currentLevelClass);
    }

    /**
     * Handles the main menu action by navigating to the main menu.
     */
    @FXML
    private void handleMainMenu() {
        NavigationManager navigationManager = new NavigationManager(
                gameOverRoot.getScene()
        );
        navigationManager.goToMainMenu();
    }
}