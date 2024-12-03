package com.example.demo.controller;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardControllerTest extends JavaFXTest {
    private LeaderboardController leaderboardController;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            leaderboardController = new LeaderboardController();
            leaderboardController.setStage(new Stage());
        });
    }

    @Test
    void testDisplayScores() {
        Platform.runLater(() -> {
            try {
                Method method = LeaderboardController.class.getDeclaredMethod("displayScores");
                method.setAccessible(true);
                assertDoesNotThrow(() -> method.invoke(leaderboardController));
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        });
    }
}