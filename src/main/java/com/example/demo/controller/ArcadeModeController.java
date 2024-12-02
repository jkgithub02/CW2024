package com.example.demo.controller;

import com.example.demo.managers.SoundManager;
import javafx.stage.Stage;

/**
 * Controller class for managing the arcade mode in the game.
 */
public class ArcadeModeController {
    private final Stage stage;
    private final SoundManager soundManager;

    /**
     * Constructs an ArcadeModeController with the specified stage.
     *
     * @param stage the primary stage of the application.
     */
    public ArcadeModeController(Stage stage) {
        this.stage = stage;
        this.soundManager = SoundManager.getInstance();
    }

    /**
     * Launches the arcade mode by initializing and starting the arcade level.
     *
     * @throws Exception if an error occurs during the launch of the arcade mode.
     */
    public void launchArcadeMode() throws Exception {
        // Create and start arcade level using your existing level infrastructure
        String arcadeLevelClass = "com.example.demo.levels.ArcadeLevel";
        String arcadeLevelName = "Arcade Mode";

        // Use the existing GameController to handle the level
        GameController gameController = new GameController(stage);

        // Launch the arcade level using the existing game infrastructure
        gameController.goToLevel(arcadeLevelClass, arcadeLevelName);
    }
}