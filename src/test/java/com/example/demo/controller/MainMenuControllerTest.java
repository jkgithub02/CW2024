package com.example.demo.controller;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuControllerTest extends JavaFXTest {
    private MainMenuController mainMenuController;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            mainMenuController = new MainMenuController();
            mainMenuController.setStage(new Stage());
        });
    }

    @Test
    void testStartGame() {
        Platform.runLater(() -> {
            try {
                Method method = MainMenuController.class.getDeclaredMethod("startGame");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(mainMenuController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }

    @Test
    void testStartArcadeMode() {
        Platform.runLater(() -> {
            try {
                Method method = MainMenuController.class.getDeclaredMethod("startArcadeMode");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(mainMenuController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }

    @Test
    void testShowLeaderboard() {
        Platform.runLater(() -> {
            try {
                Method method = MainMenuController.class.getDeclaredMethod("showLeaderboard");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(mainMenuController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }

    @Test
    void testExitGame() {
        Platform.runLater(() -> {
            try {
                Method method = MainMenuController.class.getDeclaredMethod("exitGame");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(mainMenuController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }
}