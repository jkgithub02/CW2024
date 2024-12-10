package com.example.demo.view;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Represents the health bar for a boss character in the game.
 * Displays the health as a progress bar with a text overlay showing the actual health numbers.
 *
 * @see javafx.scene.control.ProgressBar
 * @see javafx.scene.layout.StackPane
 * @see javafx.scene.text.Text
 */
public class BossHealthBar extends StackPane {
    /**
     * The progress bar representing the boss's health.
     */
    private final ProgressBar healthBar;

    /**
     * The text displaying the actual health numbers.
     */
    private final Text healthText;

    /**
     * The maximum health of the boss.
     */
    private final int maxHealth;

    /**
     * Constructs a BossHealthBar with the specified position and maximum health.
     *
     * @param x the x-coordinate of the health bar.
     * @param y the y-coordinate of the health bar.
     * @param maxHealth the maximum health of the boss.
     */
    public BossHealthBar(double x, double y, int maxHealth) {
        this.maxHealth = maxHealth;

        // Create the health bar
        healthBar = new ProgressBar(1.0);
        healthBar.setPrefWidth(300);
        healthBar.setStyle("-fx-accent: red;");

        // Create the text that shows the actual health numbers
        healthText = new Text();
        healthText.setFill(Color.WHITE);
        updateHealth(maxHealth);

        // Position the health bar
        setTranslateX(x);
        setTranslateY(y);

        // Add both the bar and text to this container
        getChildren().addAll(healthBar, healthText);
    }

    /**
     * Updates the health bar and text to reflect the current health.
     *
     * @param currentHealth the current health of the boss.
     */
    public void updateHealth(int currentHealth) {
        double healthPercentage = (double) currentHealth / maxHealth;
        healthBar.setProgress(healthPercentage);
    }
}