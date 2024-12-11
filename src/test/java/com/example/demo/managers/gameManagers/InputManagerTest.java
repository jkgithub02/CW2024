package com.example.demo.managers.gameManagers;

import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.config.GameState;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InputManagerTest extends JavaFXTest {
    private InputManager inputManager;
    private Set<KeyCode> pressedKeys;
    private UserPlane user;
    private ImageView background;
    private Group root;
    private List<ActiveActorDestructible> userProjectiles;
    private Stage stage;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            pressedKeys = new HashSet<>();
            background = new ImageView();
            root = new Group();
            userProjectiles = new ArrayList<>();
            // Create a minimal UserPlane instance - you might need to adjust constructor parameters
            user = new UserPlane(1 );

            inputManager = new InputManager(
                    pressedKeys,
                    user,
                    background,
                    root,
                    userProjectiles
            );
        });
    }

    @Test
    void testGameStateChange() {
        Platform.runLater(() -> {
            inputManager.setGameState(GameState.PAUSED);
            pressedKeys.add(KeyCode.UP);
            inputManager.updateUserPlaneMovement();

            // Verify that the pressedKeys set is empty after setting game state to PAUSED
            assertTrue(pressedKeys.isEmpty());
        });
    }

    @Test
    void testKeyPressHandling() {
        Platform.runLater(() -> {
            inputManager.initializeFireProjectileHandler();

            // Simulate key press
            background.fireEvent(new javafx.scene.input.KeyEvent(
                    javafx.scene.input.KeyEvent.KEY_PRESSED,
                    "", "", KeyCode.SPACE,
                    false, false, false, false
            ));

            // Verify key was added to pressedKeys
            assertTrue(pressedKeys.contains(KeyCode.SPACE));
        });
    }

    @Test
    void testKeyReleaseHandling() {
        Platform.runLater(() -> {
            inputManager.initializeFireProjectileHandler();
            pressedKeys.add(KeyCode.SPACE);

            // Simulate key release
            background.fireEvent(new javafx.scene.input.KeyEvent(
                    javafx.scene.input.KeyEvent.KEY_RELEASED,
                    "", "", KeyCode.SPACE,
                    false, false, false, false
            ));

            // Verify key was removed from pressedKeys
            assertFalse(pressedKeys.contains(KeyCode.SPACE));
        });
    }
}