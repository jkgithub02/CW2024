package com.example.demo.view;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossHealthBarTest extends JavaFXTest {
    private BossHealthBar healthBar;
    private static final int MAX_HEALTH = 100;
    private static final double X_POS = 550;
    private static final double Y_POS = 50;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            healthBar = new BossHealthBar(X_POS, Y_POS, MAX_HEALTH);
        });
        waitForFxEvents();
    }

    @Test
    void testInitialization() {
        Platform.runLater(() -> {
            assertEquals(X_POS, healthBar.getTranslateX(), "X position should be set correctly");
            assertEquals(Y_POS, healthBar.getTranslateY(), "Y position should be set correctly");

            ProgressBar bar = (ProgressBar) healthBar.getChildren().get(0);
            assertEquals(1.0, bar.getProgress(), "Initial health should be full");
            assertEquals(300, bar.getPrefWidth(), "Width should be set to 300");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateHealth() {
        Platform.runLater(() -> {
            healthBar.updateHealth(50);
            ProgressBar bar = (ProgressBar) healthBar.getChildren().get(0);
            assertEquals(0.5, bar.getProgress(), "Health should be at 50%");

            healthBar.updateHealth(0);
            assertEquals(0.0, bar.getProgress(), "Health should be at 0%");
        });
        waitForFxEvents();
    }

    private void waitForFxEvents() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}