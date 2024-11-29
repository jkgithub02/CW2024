package com.example.demo.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KillCountDisplay {
    protected static final int LABEL_FONT_SIZE = 24;
    protected static final String LABEL_FONT_FAMILY = "Arcade";
    protected static final String LABEL_TEXT_COLOR = "-fx-text-fill: black;";
    private HBox container;
    private final double containerXPosition;
    private final double containerYPosition;
    protected int currentKills;
    protected final int maxKills;
    protected Label killCountLabel;

    public KillCountDisplay(double xPosition, double yPosition, int maxKills) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.maxKills = maxKills;
        this.currentKills = 0;
        initializeContainer();
        initializeKillCountLabel();
    }

    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
    }

    protected String getDisplayText() {  // New method to get display text
        return "Kills: " + currentKills + "/" + maxKills;
    }

    private void initializeKillCountLabel() {
        killCountLabel = new Label(getDisplayText());
        killCountLabel.setFont(Font.font(LABEL_FONT_FAMILY, FontWeight.BOLD, LABEL_FONT_SIZE));
        killCountLabel.setStyle(LABEL_TEXT_COLOR);
        container.getChildren().add(killCountLabel);
    }

    public void updateKillCount(int kills) {
        this.currentKills = kills;
        killCountLabel.setText(getDisplayText());
    }

    public HBox getContainer() {
        return container;
    }
}