package com.example.demo.view;

import javafx.scene.Group;

public class ArcadeLevelView extends LevelView {
    private static final double KILL_COUNT_X_POSITION = 1100;
    private static final double KILL_COUNT_Y_POSITION = 25;
    private final Group root;
    private final ArcadeKillCountDisplay killCountDisplay;

    public ArcadeLevelView(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, 0);
        this.root = root;
        this.killCountDisplay = new ArcadeKillCountDisplay(KILL_COUNT_X_POSITION, KILL_COUNT_Y_POSITION);
    }

    @Override
    public void showKillCountDisplay() {
        root.getChildren().add(killCountDisplay.getContainer());
    }

    @Override
    public void updateKillCount(int kills) {
        killCountDisplay.updateKillCount(kills);
    }
}