package com.example.demo.view;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class BulletDisplayTest extends JavaFXTest {
    private BulletDisplay bulletDisplay;
    private static final double X_POSITION = 100.0;
    private static final double Y_POSITION = 100.0;
    private static final int MAX_BULLETS = 30;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            bulletDisplay = new BulletDisplay(X_POSITION, Y_POSITION, MAX_BULLETS);
        });
        waitForFxEvents();
    }

    @Test
    void testInitialization() {
        Platform.runLater(() -> {
            Group container = bulletDisplay.getContainer();
            assertNotNull(container, "Container should not be null");

            assertEquals(2, container.getChildren().size(),
                    "Container should have 2 children (text and progress bar)");

            Text bulletText = (Text) container.getChildren().get(0);
            ProgressBar progressBar = (ProgressBar) container.getChildren().get(1);

            // Test initial text position
            assertEquals(X_POSITION, bulletText.getX(),
                    "Text X position should match constructor parameter");
            assertEquals(Y_POSITION, bulletText.getY(),
                    "Text Y position should match constructor parameter");

            // Test initial progress bar position
            assertEquals(X_POSITION, progressBar.getLayoutX(),
                    "Progress bar X position should match constructor parameter");
            assertEquals(Y_POSITION + 10.0, progressBar.getLayoutY(),
                    "Progress bar Y position should be offset by TEXT_OFFSET");

            // Test initial visibility and values
            assertFalse(progressBar.isVisible(),
                    "Progress bar should be initially hidden");
            assertEquals(String.format("Bullets: %d", MAX_BULLETS),
                    bulletText.getText(), "Initial bullet text should show max bullets");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateDisplayWithBullets() {
        Platform.runLater(() -> {
            int currentBullets = 15;
            bulletDisplay.updateDisplay(currentBullets, false, 1.0);

            Text bulletText = (Text) bulletDisplay.getContainer().getChildren().get(0);
            ProgressBar progressBar = (ProgressBar) bulletDisplay.getContainer().getChildren().get(1);

            assertEquals(String.format("Bullets: %d", currentBullets),
                    bulletText.getText(), "Bullet text should show current bullets");
            assertFalse(progressBar.isVisible(),
                    "Progress bar should be hidden when not reloading");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateDisplayWhileReloading() {
        Platform.runLater(() -> {
            double reloadProgress = 0.5;
            bulletDisplay.updateDisplay(0, true, reloadProgress);

            Text bulletText = (Text) bulletDisplay.getContainer().getChildren().get(0);
            ProgressBar progressBar = (ProgressBar) bulletDisplay.getContainer().getChildren().get(1);

            assertEquals("RELOADING...", bulletText.getText(),
                    "Text should show reloading message");
            assertTrue(progressBar.isVisible(),
                    "Progress bar should be visible while reloading");
            assertEquals(reloadProgress, progressBar.getProgress(),
                    "Progress bar should show correct reload progress");
        });
        waitForFxEvents();
    }

    @Test
    void testProgressBarStyle() {
        Platform.runLater(() -> {
            ProgressBar progressBar = (ProgressBar) bulletDisplay.getContainer().getChildren().get(1);
            assertEquals("-fx-accent: #00ff00;", progressBar.getStyle(),
                    "Progress bar should have green accent style");
            assertEquals(100, progressBar.getPrefWidth(),
                    "Progress bar should have width of 100");
        });
        waitForFxEvents();
    }

    @Test
    void testTextStyle() {
        Platform.runLater(() -> {
            Text bulletText = (Text) bulletDisplay.getContainer().getChildren().get(0);
            assertEquals("Arial Black", bulletText.getFont().getFamily(),
                    "Text should use Arial Black font");
            assertEquals(25, bulletText.getFont().getSize(),
                    "Text should have size 25");
        });
        waitForFxEvents();
    }
}