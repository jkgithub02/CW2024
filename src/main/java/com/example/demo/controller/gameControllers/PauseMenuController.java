package com.example.demo.controller.gameControllers;

import com.example.demo.config.GameConfig;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for managing the pause menu in the game.
 *
 * @see com.example.demo.config.GameConfig
 */
public class PauseMenuController {

    /**
     * The root pane of the pause menu.
     */
    @FXML
    private StackPane pauseRoot;

    /**
     * The overlay rectangle in the pause menu.
     */
    @FXML
    private Rectangle overlay;

    /**
     * The action to perform when the resume button is clicked.
     */
    private Runnable resumeAction;

    /**
     * The action to perform when the restart button is clicked.
     */
    private Runnable restartAction;

    /**
     * The action to perform when the main menu button is clicked.
     */
    private Runnable mainMenuAction;

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
     * Initializes the pause menu by setting the root pane dimensions.
     */
    @FXML
    public void initialize() {
        // Make sure the root pane fills the entire screen
        pauseRoot.setPrefWidth(GameConfig.SCREEN_WIDTH);
        pauseRoot.setPrefHeight(GameConfig.SCREEN_HEIGHT);
    }

    /**
     * Sets the actions for resume, restart, and main menu buttons.
     *
     * @param resumeAction the action to perform when the resume button is clicked.
     * @param restartAction the action to perform when the restart button is clicked.
     * @param mainMenuAction the action to perform when the main menu button is clicked.
     */
    public void setActions(Runnable resumeAction, Runnable restartAction, Runnable mainMenuAction) {
        this.resumeAction = resumeAction;
        this.restartAction = restartAction;
        this.mainMenuAction = mainMenuAction;
    }

    /**
     * Handles the resume action by running the resume action.
     */
    @FXML
    private void handleResume() {
        if (resumeAction != null) {
            resumeAction.run();
        }
    }

    /**
     * Handles the restart action by running the restart action.
     */
    @FXML
    private void handleRestart() {
        if (restartAction != null) {
            restartAction.run();
        }
    }

    /**
     * Handles the main menu action by running the main menu action.
     */
    @FXML
    private void handleMainMenu() {
        if (mainMenuAction != null) {
            mainMenuAction.run();
        }
    }

    /**
     * Gets the root pane of the pause menu.
     *
     * @return the root pane of the pause menu.
     */
    public StackPane getPauseRoot() {
        return pauseRoot;
    }
}