package com.example.demo.view;

import com.example.demo.ArcadeKillCountDisplay;
import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcadeKillCountDisplayTest extends JavaFXTest {
    private ArcadeKillCountDisplay killCountDisplay;
    private static final double X_POS = 1100;
    private static final double Y_POS = 25;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            killCountDisplay = new ArcadeKillCountDisplay(X_POS, Y_POS);
        });
        waitForFxEvents();
    }

    @Test
    void testInitialization() {
        Platform.runLater(() -> {
            assertEquals(X_POS, killCountDisplay.getContainer().getLayoutX(),
                    "X position should be set correctly");
            assertEquals(Y_POS, killCountDisplay.getContainer().getLayoutY(),
                    "Y position should be set correctly");
            assertEquals("Score: 0", killCountDisplay.getDisplayText(),
                    "Initial score should be 0");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateKillCount() {
        Platform.runLater(() -> {
            killCountDisplay.updateKillCount(10);
            assertEquals("Score: 10", killCountDisplay.getDisplayText(),
                    "Score should update to 10");
        });
        waitForFxEvents();
    }
}