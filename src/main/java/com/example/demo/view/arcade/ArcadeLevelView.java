package com.example.demo.view.arcade;

import com.example.demo.config.GameConfig;
import com.example.demo.view.LevelView;
import javafx.scene.Group;

/**
 * Represents the view for an arcade level in the game.
 *
 * @see com.example.demo.config.GameConfig
 * @see ArcadeKillCountDisplay
 */
public class ArcadeLevelView extends LevelView {
    /**
     * The x-coordinate position for the kill count display.
     */
    private static final double KILL_COUNT_X_POSITION = 1100;

    /**
     * The y-coordinate position for the kill count display.
     */
    private static final double KILL_COUNT_Y_POSITION = 25;

    /**
     * The root group of the scene.
     */
    private final Group root;

    /**
     * The kill count display for the arcade level.
     */
    private final ArcadeKillCountDisplay killCountDisplay;

    /**
     * Constructs an ArcadeLevelView with the specified root group and number of hearts to display.
     *
     * @param root the root group of the scene.
     * @param heartsToDisplay the number of hearts to display.
     */
    public ArcadeLevelView(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, 0, GameConfig.PLAYER_MAX_BULLETS);
        this.root = root;
        this.killCountDisplay = new ArcadeKillCountDisplay(KILL_COUNT_X_POSITION, KILL_COUNT_Y_POSITION);
    }

    /**
     * Displays the kill count display on the screen.
     */
    @Override
    public void showKillCountDisplay() {
        root.getChildren().add(killCountDisplay.getContainer());
    }

    /**
     * Updates the kill count display with the specified number of kills.
     *
     * @param kills the number of kills to update the display with.
     */
    @Override
    public void updateKillCount(int kills) {
        killCountDisplay.updateKillCount(kills);
    }
}