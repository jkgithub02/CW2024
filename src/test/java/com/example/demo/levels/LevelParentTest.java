package com.example.demo.levels;

import com.example.demo.JavaFXTest;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import javafx.application.Platform;

import static org.junit.jupiter.api.Assertions.*;

class LevelParentTest extends JavaFXTest {

    private TestLevel testLevel;
    private Stage stage;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            testLevel = new TestLevel("/com/example/demo/images/testImage.png", 3);
            Scene scene = testLevel.initializeScene();
            stage.setScene(scene);
            stage.show();
        });
    }

    @Test
    void testInitializeScene() {
        Platform.runLater(() -> {
            Scene scene = testLevel.initializeScene();
            assertNotNull(scene);
            assertEquals(800, scene.getWidth());  // Example width, replace with actual value
            assertEquals(600, scene.getHeight()); // Example height, replace with actual value
        });
    }

    @Test
    void testStartGame() {
        Platform.runLater(() -> {
            testLevel.startGame();
            // Add assertions or verifications as needed
        });
    }

    @Test
    void testGoToNextLevel() {
        Platform.runLater(() -> {
            testLevel.goToNextLevel("NextLevelClassName", "NextLevelName");
            assertEquals("NextLevelClassName,NextLevelName", testLevel.nextLevelProperty().get());
        });
    }

    @Test
    void testPauseAndResumeGame() {
        Platform.runLater(() -> {
            testLevel.pauseGame();
            // Verify game is paused
            testLevel.resumeGame();
            // Verify game is resumed
        });
    }

    @Test
    void testWinGame() {
        Platform.runLater(() -> {
            testLevel.winGame();
            // Add assertions or verifications as needed
        });
    }

    @Test
    void testLoseGame() {
        Platform.runLater(() -> {
            testLevel.loseGame();
            // Add assertions or verifications as needed
        });
    }
}