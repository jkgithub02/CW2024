package com.example.demo.controller.gameControllers;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GameOverControllerTest extends JavaFXTest {
    private GameOverController gameOverController;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            gameOverController = new GameOverController();
            gameOverController.setStage(new Stage());
        });
    }

    @Test
    void testInitialize() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> gameOverController.initialize());
        });
    }

    @Test
    void testHandleRestart() {
        Platform.runLater(() -> {
            try {
                Method method = GameOverController.class.getDeclaredMethod("handleRestart");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(gameOverController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }

    @Test
    void testHandleMainMenu() {
        Platform.runLater(() -> {
            try {
                Method method = GameOverController.class.getDeclaredMethod("handleMainMenu");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(gameOverController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }
}