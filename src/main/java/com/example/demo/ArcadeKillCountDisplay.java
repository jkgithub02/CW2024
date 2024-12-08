package com.example.demo;

/**
 * Displays the kill count in the arcade mode.
 */
public class ArcadeKillCountDisplay extends KillCountDisplay {

    /**
     * Constructs an ArcadeKillCountDisplay with the specified position.
     *
     * @param xPosition the x-coordinate of the display.
     * @param yPosition the y-coordinate of the display.
     */
    public ArcadeKillCountDisplay(double xPosition, double yPosition) {
        super(xPosition, yPosition, 0);
    }

    /**
     * Returns the display text for the current kill count.
     *
     * @return the display text.
     */
    @Override
    protected String getDisplayText() {
        return "Score: " + currentKills;
    }
}