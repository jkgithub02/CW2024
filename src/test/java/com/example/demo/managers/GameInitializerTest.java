package com.example.demo.managers;

import com.example.demo.JavaFXTest;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.view.LevelView;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

class GameInitializerTest extends JavaFXTest {
    private GameInitializer gameInitializer;
    private Group root;
    private Scene scene;
    private ImageView background;
    private UserPlane user;
    private LevelView levelView;
    private PauseManager pauseHandler;
    private Stage stage;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            root = new Group();
            scene = new Scene(root, 800, 600);
            background = new ImageView();
            user = new UserPlane(1);
            levelView = new LevelView(root, 3, 5);

            // Create appropriate Runnable actions for PauseManager
            Runnable pauseAction = () -> {};
            Runnable resumeAction = () -> {};
            Runnable mainMenuAction = () -> {};
            Runnable restartAction = () -> {};

            pauseHandler = new PauseManager(
                    scene,
                    root,
                    pauseAction,
                    resumeAction,
                    mainMenuAction,
                    restartAction
            );

            gameInitializer = new GameInitializer(
                    root,
                    scene,
                    background,
                    user,
                    levelView,
                    pauseHandler
            );

            stage.setScene(scene);
            stage.show();
        });
    }

    @Test
    void testInitializeGame() {
        Platform.runLater(() -> {
            gameInitializer.initializeGame();

            // Verify background properties
            assertTrue(background.isFocusTraversable());
            assertEquals(scene.getHeight(), background.getFitHeight());
            assertEquals(scene.getWidth(), background.getFitWidth());

            // Verify background is added to root
            assertTrue(root.getChildren().contains(background));
        });
    }

    @Test
    void testRootContainsRequiredElements() {
        Platform.runLater(() -> {
            gameInitializer.initializeGame();

            // Check that root contains at least the background
            assertFalse(root.getChildren().isEmpty());
            assertTrue(root.getChildren().contains(background));
        });
    }
}