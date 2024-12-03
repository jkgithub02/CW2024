package com.example.demo.managers;

import com.example.demo.controller.PauseMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;

/**
 * Manages the pause functionality of the game, including displaying the pause menu and handling pause/resume actions.
 */
public class PauseManager {
    private final Scene scene;
    private boolean isPaused = false;
    private final Runnable pauseAction;
    private final Runnable resumeAction;
    private final Group root;
    private PauseMenuController pauseMenuController;

    /**
     * Constructs a PauseManager with the specified parameters.
     *
     * @param scene the scene of the game.
     * @param root the root group of the scene.
     * @param pauseAction the action to perform when the game is paused.
     * @param resumeAction the action to perform when the game is resumed.
     * @param mainMenuAction the action to perform when navigating to the main menu.
     * @param restartAction the action to perform when restarting the game.
     */
    public PauseManager(Scene scene, Group root, Runnable pauseAction, Runnable resumeAction,
                        Runnable mainMenuAction, Runnable restartAction) {
        this.scene = scene;
        this.root = root;
        this.pauseAction = pauseAction;
        this.resumeAction = resumeAction;

        initializePauseMenu(resumeAction, restartAction, mainMenuAction);
        initializePauseHandler();
    }

    /**
     * Initializes the pause menu with the specified actions.
     *
     * @param resumeAction the action to perform when resuming the game.
     * @param restartAction the action to perform when restarting the game.
     * @param mainMenuAction the action to perform when navigating to the main menu.
     */
    private void initializePauseMenu(Runnable resumeAction, Runnable restartAction, Runnable mainMenuAction) {
        try {
            URL resource = PauseManager.class.getResource("/PauseMenu.fxml");
            FXMLLoader loader = new FXMLLoader(resource);
            loader.load();
            pauseMenuController = loader.getController();
            pauseMenuController.setActions(
                    () -> resumeGame(),
                    restartAction,
                    mainMenuAction
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the handler for pausing and resuming the game using the ESCAPE key.
     */
    public void initializePauseHandler() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                if (isPaused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            }
        });
    }

    /**
     * Pauses the game and displays the pause menu.
     */
    private void pauseGame() {
        pauseAction.run();
        if (pauseMenuController != null) {
            root.getChildren().add(pauseMenuController.getPauseRoot());
        }
        isPaused = true;
    }

    /**
     * Resumes the game and hides the pause menu.
     */
    private void resumeGame() {
        resumeAction.run();
        if (pauseMenuController != null) {
            root.getChildren().remove(pauseMenuController.getPauseRoot());
        }
        isPaused = false;
    }

    /**
     * Checks if the game is currently paused.
     *
     * @return true if the game is paused, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }
}