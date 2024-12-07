package com.example.demo.managers;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.config.GameState;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Set;

/**
 * Manages user input and updates the state of the user plane and projectiles.
 */
public class InputManager {
    private final Set<KeyCode> pressedKeys;
    private final UserPlane user;
    private final ImageView background;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;
    private GameState gameState = GameState.ACTIVE;

    /**
     * Constructs an InputManager with the specified parameters.
     *
     * @param pressedKeys the set of currently pressed keys.
     * @param user the user's plane.
     * @param background the background image view.
     * @param root the root group of the scene.
     * @param userProjectiles the list of user projectiles.
     */
    public InputManager(Set<KeyCode> pressedKeys, UserPlane user, ImageView background, Group root, List<ActiveActorDestructible> userProjectiles) {
        this.pressedKeys = pressedKeys;
        this.user = user;
        this.background = background;
        this.root = root;
        this.userProjectiles = userProjectiles;
    }

    /**
     * Initializes the handler for firing projectiles.
     */
    public void initializeFireProjectileHandler() {
        background.setOnKeyPressed(e -> {
            if (gameState == GameState.ACTIVE) {
                pressedKeys.add(e.getCode());
                if (e.getCode() == KeyCode.SPACE) fireProjectile();
            }
        });
        background.setOnKeyReleased(e -> {
            pressedKeys.remove(e.getCode());
            if (gameState != GameState.ACTIVE) {
                stopAllMovement();
            }
        });
    }

    /**
     * Fires a projectile from the user's plane.
     */
    private void fireProjectile() {
        if (gameState == GameState.ACTIVE) {
            ActiveActorDestructible projectile = user.fireProjectile();
            if (projectile != null) {  // Add this null check
                root.getChildren().add(projectile);
                userProjectiles.add(projectile);
            }
        }
    }

    /**
     * Updates the movement of the user's plane based on the pressed keys.
     */
    public void updateUserPlaneMovement() {
        if (gameState == GameState.ACTIVE) {
            if (pressedKeys.contains(KeyCode.UP)) user.moveUp();
            if (pressedKeys.contains(KeyCode.DOWN)) user.moveDown();
            if (pressedKeys.contains(KeyCode.LEFT)) user.moveLeft();
            if (pressedKeys.contains(KeyCode.RIGHT)) user.moveRight();
            if (!pressedKeys.contains(KeyCode.UP) && !pressedKeys.contains(KeyCode.DOWN)) user.stopVertical();
            if (!pressedKeys.contains(KeyCode.LEFT) && !pressedKeys.contains(KeyCode.RIGHT)) user.stopHorizontal();
        } else {
            stopAllMovement();
        }
    }

    /**
     * Stops all movement of the user's plane.
     */
    private void stopAllMovement() {
        pressedKeys.clear();
        user.stopVertical();
        user.stopHorizontal();
    }

    /**
     * Sets the game state.
     *
     * @param state the new game state.
     */
    public void setGameState(GameState state) {
        this.gameState = state;
        if (state != GameState.ACTIVE) {
            stopAllMovement();
        }
    }
}