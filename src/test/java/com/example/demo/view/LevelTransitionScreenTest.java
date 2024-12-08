package com.example.demo.view;

import com.example.demo.JavaFXTest;
import com.example.demo.LevelTransitionScreen;
import com.example.demo.config.GameConfig;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LevelTransitionScreenTest extends JavaFXTest {

    private Stage stage;
    private LevelTransitionScreen levelTransitionScreen;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        levelTransitionScreen = new LevelTransitionScreen(stage, "Test Level", () -> {});
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            if (stage == null) {
                stage = new Stage();
            }
            levelTransitionScreen = new LevelTransitionScreen(stage, "Test Level", () -> {});
        });
    }

    @Test
    void testShow() {
        Platform.runLater(() -> {
            levelTransitionScreen.show();
            Scene scene = stage.getScene();
            assertNotNull(scene);
            assertEquals(GameConfig.SCREEN_WIDTH, scene.getWidth());
            assertEquals(GameConfig.SCREEN_HEIGHT, scene.getHeight());
        });

        // Wait for the JavaFX thread to complete
        waitForRunLater();
    }

    private void waitForRunLater() {
        try {
            Thread.sleep(100); // Adjust the sleep time as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}