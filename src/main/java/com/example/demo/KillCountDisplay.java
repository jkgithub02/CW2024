package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Displays the kill count in the game.
 */
public class KillCountDisplay {
    protected static final int LABEL_FONT_SIZE = 25;
    protected static final String LABEL_FONT_FAMILY = "Arial Black";
    protected static final String LABEL_TEXT_COLOR = "-fx-text-fill: black;";
    private HBox container;
    private final double containerXPosition;
    private final double containerYPosition;
    protected int currentKills;
    protected final int maxKills;
    protected Label killCountLabel;

    /**
     * Constructs a KillCountDisplay with the specified position and maximum kills.
     *
     * @param xPosition the x-coordinate of the display.
     * @param yPosition the y-coordinate of the display.
     * @param maxKills the maximum number of kills to display.
     */
    public KillCountDisplay(double xPosition, double yPosition, int maxKills) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.maxKills = maxKills;
        this.currentKills = 0;
        initializeContainer();
        initializeKillCountLabel();
    }

    /**
     * Initializes the container for the kill count display.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
    }

    /**
     * Returns the display text for the current kill count.
     *
     * @return the display text.
     */
    protected String getDisplayText() {
        return "Kills: " + currentKills + "/" + maxKills;
    }

    /**
     * Initializes the label for displaying the kill count.
     */
    private void initializeKillCountLabel() {
        killCountLabel = new Label(getDisplayText());
        killCountLabel.setFont(Font.font(LABEL_FONT_FAMILY, FontWeight.BOLD, LABEL_FONT_SIZE));
        killCountLabel.setStyle(LABEL_TEXT_COLOR);
        container.getChildren().add(killCountLabel);
    }

    /**
     * Updates the kill count display with the specified number of kills.
     *
     * @param kills the number of kills to update the display with.
     */
    public void updateKillCount(int kills) {
        this.currentKills = kills;
        killCountLabel.setText(getDisplayText());
    }

    /**
     * Returns the container for the kill count display.
     *
     * @return the container.
     */
    public HBox getContainer() {
        return container;
    }
}