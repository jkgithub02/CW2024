package com.example.demo.view;

public class ArcadeKillCountDisplay extends KillCountDisplay {

    public ArcadeKillCountDisplay(double xPosition, double yPosition) {
        super(xPosition, yPosition, 0);
    }

    @Override
    protected String getDisplayText() {
        return "Score: " + currentKills;
    }
}