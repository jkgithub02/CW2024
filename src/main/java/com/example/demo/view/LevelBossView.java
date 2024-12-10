package com.example.demo.view;

import javafx.scene.Group;

/**
 * Represents the view for a level with a boss in the game.
 *
 * @see javafx.scene.Group
 * @see com.example.demo.view.BossHealthBar
 */
public class LevelBossView extends LevelView {
	/**
	 * The x-coordinate position for the boss health bar.
	 */
	private static final double HEALTH_BAR_X = 550;

	/**
	 * The y-coordinate position for the boss health bar.
	 */
	private static final double HEALTH_BAR_Y = 50;

	/**
	 * The maximum health of the boss.
	 */
	private static final int BOSS_MAX_HEALTH = 100;

	/**
	 * The root group of the scene.
	 */
	private final Group root;

	/**
	 * The health bar for the boss.
	 */
	private final BossHealthBar bossHealthBar;

	/**
	 * Constructs a LevelBossView with the specified root group, number of hearts to display, and maximum kills.
	 *
	 * @param root the root group of the scene.
	 * @param heartsToDisplay the number of hearts to display.
	 * @param maxKills the maximum number of kills.
	 * @param maxBullets the maximum number of bullets.
	 */
	public LevelBossView(Group root, int heartsToDisplay, int maxKills, int maxBullets) {
		super(root, heartsToDisplay, maxKills, maxBullets);
		this.root = root;
		this.bossHealthBar = new BossHealthBar(HEALTH_BAR_X, HEALTH_BAR_Y, BOSS_MAX_HEALTH);
		addBossHealthBar();
	}

	/**
	 * Adds the boss health bar to the root group.
	 */
	private void addBossHealthBar() {
		root.getChildren().add(bossHealthBar);
	}

	/**
	 * Overrides the method to show the kill count display.
	 * This implementation is empty to prevent the kill count from being displayed.
	 */
	@Override
	public void showKillCountDisplay() {
		// Empty override to prevent kill count from being displayed
	}

	/**
	 * Overrides the method to update the kill count.
	 * This implementation is empty to prevent kill count updates like other levels.
	 *
	 * @param kills the number of kills to update.
	 */
	@Override
	public void updateKillCount(int kills) {
		// Empty override to prevent kill count updates
	}
}