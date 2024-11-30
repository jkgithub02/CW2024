package com.example.demo.controller;

import com.example.demo.config.GameConfig;
import com.example.demo.levels.LevelOne;
import com.example.demo.managers.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller class for managing the win screen in the game.
 */
public class WinScreenController {
    @FXML
    private StackPane winRoot;
    private Stage stage;
    private Class<?> baseLevelClass = LevelOne.class;

    /**
     * Sets the stage for this controller.
     *
     * @param stage the primary stage of the application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
     * Initializes the win screen by setting the root pane dimensions.
     */
    @FXML
    public void initialize() {
        // Make sure the root pane fills the entire screen
        winRoot.setPrefWidth(GameConfig.SCREEN_WIDTH);
        winRoot.setPrefHeight(GameConfig.SCREEN_HEIGHT);
    }

    /**
     * Handles the restart action by restarting the base level.
     */
    @FXML
    private void handleRestart() {
        NavigationManager navigationManager = new NavigationManager(
                winRoot.getScene()
        );
        navigationManager.restartLevel(baseLevelClass);
    }

    /**
     * Handles the main menu action by navigating to the main menu.
     */
    @FXML
    private void handleMainMenu() {
        NavigationManager navigationManager = new NavigationManager(
                winRoot.getScene()
        );
        navigationManager.goToMainMenu();
    }
}