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
    private final KeyBindingsManager keyBindingsManager;
    private boolean isMovingVertically = false;
    private boolean isMovingHorizontally = false;


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
        this.keyBindingsManager = KeyBindingsManager.getInstance();
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
            KeyCode upKey = keyBindingsManager.getBinding("UP");
            KeyCode downKey = keyBindingsManager.getBinding("DOWN");
            KeyCode leftKey = keyBindingsManager.getBinding("LEFT");
            KeyCode rightKey = keyBindingsManager.getBinding("RIGHT");

            // Handle vertical movement
            if (pressedKeys.contains(upKey) && !pressedKeys.contains(downKey)) {
                user.moveUp();
                isMovingVertically = true;
            } else if (pressedKeys.contains(downKey) && !pressedKeys.contains(upKey)) {
                user.moveDown();
                isMovingVertically = true;
            } else {
                if (isMovingVertically) {
                    user.stopVertical();
                    isMovingVertically = false;
                }
            }

            // Handle horizontal movement
            if (pressedKeys.contains(leftKey) && !pressedKeys.contains(rightKey)) {
                user.moveLeft();
                isMovingHorizontally = true;
            } else if (pressedKeys.contains(rightKey) && !pressedKeys.contains(leftKey)) {
                user.moveRight();
                isMovingHorizontally = true;
            } else {
                if (isMovingHorizontally) {
                    user.stopHorizontal();
                    isMovingHorizontally = false;
                }
            }
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
        isMovingVertically = false;
        isMovingHorizontally = false;
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