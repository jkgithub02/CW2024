package com.example.demo.managers.gameManagers;

import com.example.demo.actors.planes.UserPlane;
import com.example.demo.view.LevelView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 * Handles the initialization of the game.
 *
 * @see com.example.demo.actors.planes.UserPlane
 * @see com.example.demo.view.LevelView
 * @see javafx.scene.Group
 * @see javafx.scene.Scene
 * @see javafx.scene.image.ImageView
 */
public class GameInitializer {

    /**
     * The root group of the scene.
     */
    private final Group root;

    /**
     * The scene of the game.
     */
    private final Scene scene;

    /**
     * The background image view.
     */
    private final ImageView background;

    /**
     * The user's plane.
     */
    private final UserPlane user;

    /**
     * The view for the level.
     */
    private final LevelView levelView;

    /**
     * The handler for pausing the game.
     */
    private final PauseManager pauseHandler;

    /**
     * Constructs a GameInitializer with the specified parameters.
     *
     * @param root the root group of the scene.
     * @param scene the scene of the game.
     * @param background the background image view.
     * @param user the user's plane.
     * @param levelView the view for the level.
     * @param pauseHandler the handler for pausing the game.
     */
    public GameInitializer(Group root, Scene scene, ImageView background, UserPlane user, LevelView levelView, PauseManager pauseHandler) {
        this.root = root;
        this.scene = scene;
        this.background = background;
        this.user = user;
        this.levelView = levelView;
        this.pauseHandler = pauseHandler;
    }

    /**
     * Initializes the game.
     */
    public void initializeGame() {
        initializeBackground();
        pauseHandler.initializePauseHandler();
        levelView.showHeartDisplay();
        levelView.showKillCountDisplay();
        levelView.showBulletDisplay();
    }

    /**
     * Initializes the background for the game.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(scene.getHeight());
        background.setFitWidth(scene.getWidth());
        root.getChildren().add(background);
    }
}