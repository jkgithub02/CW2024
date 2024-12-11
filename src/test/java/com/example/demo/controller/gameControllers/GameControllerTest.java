package com.example.demo.controller.gameControllers;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest extends JavaFXTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameController = new GameController(stage);
        });
    }

    @Test
    void testLaunchGame() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> gameController.launchGame());
        });
    }

    @Test
    void testGoToLevel() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> gameController.goToLevel("com.example.demo.levels.LevelOne", "Level 1"));
        });
    }
}