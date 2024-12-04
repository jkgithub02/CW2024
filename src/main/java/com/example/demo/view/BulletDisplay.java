package com.example.demo.view;

import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Represents a display for bullet count and reload status in the game.
 */
public class BulletDisplay {
    private static final double TEXT_OFFSET = 10.0;
    private final Group container;
    private final Text bulletText;
    private final ProgressBar reloadProgress;
    private final int maxBullets;

    /**
     * Constructs a BulletDisplay with the specified position and maximum bullets.
     *
     * @param xPosition the X coordinate for the display.
     * @param yPosition the Y coordinate for the display.
     * @param maxBullets the maximum number of bullets.
     */
    public BulletDisplay(double xPosition, double yPosition, int maxBullets) {
        this.maxBullets = maxBullets;
        this.container = new Group();

        // Initialize bullet count text
        this.bulletText = new Text();
        this.bulletText.setFill(Color.BLACK);
        this.bulletText.setFont(Font.font("Arial Black", 25));
        this.bulletText.setX(xPosition);
        this.bulletText.setY(yPosition);

        // Initialize reload progress bar
        this.reloadProgress = new ProgressBar(1.0);
        this.reloadProgress.setPrefWidth(100);
        this.reloadProgress.setStyle("-fx-accent: #00ff00;");
        this.reloadProgress.setLayoutX(xPosition);
        this.reloadProgress.setLayoutY(yPosition + TEXT_OFFSET);
        this.reloadProgress.setVisible(false);

        // Add elements to container
        container.getChildren().addAll(bulletText, reloadProgress);

        // Set initial text
        updateDisplay(maxBullets, false, 1.0);
    }

    /**
     * Updates the bullet display with current status.
     *
     * @param currentBullets the current number of bullets.
     * @param isReloading whether the gun is reloading.
     * @param reloadProgress the progress of reloading (0.0 to 1.0).
     */
    public void updateDisplay(int currentBullets, boolean isReloading, double reloadProgress) {
        if (isReloading) {
            bulletText.setText("RELOADING...");
            this.reloadProgress.setVisible(true);
            this.reloadProgress.setProgress(reloadProgress);
        } else {
            bulletText.setText("Bullets: " + currentBullets + "/" + maxBullets);
            this.reloadProgress.setVisible(false);
        }
    }

    /**
     * Gets the container group that holds all display elements.
     *
     * @return the Group container.
     */
    public Group getContainer() {
        return container;
    }
}