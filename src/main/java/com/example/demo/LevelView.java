package com.example.demo;

import javafx.scene.Group;

/**
 * Represents the view for a level in the game, including displays for hearts, kill count, and bullets.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 550;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final double KILL_COUNT_X_POSITION = 1100;
	private static final double KILL_COUNT_Y_POSITION = 25;
	private static final double BULLET_DISPLAY_X_POSITION = 35; // New position for bullet display
	private static final double BULLET_DISPLAY_Y_POSITION = 50; // Align with other displays

	private final Group root;
	private final HeartDisplay heartDisplay;
	private final KillCountDisplay killCountDisplay;
	private final BulletDisplay bulletDisplay;

	/**
	 * Constructs a LevelView with the specified parameters.
	 *
	 * @param root the root group of the scene.
	 * @param heartsToDisplay the number of hearts to display.
	 * @param maxKills the maximum number of kills to display.
	 * @param maxBullets the maximum number of bullets.
	 */
	public LevelView(Group root, int heartsToDisplay, int maxKills, int maxBullets) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.killCountDisplay = new KillCountDisplay(KILL_COUNT_X_POSITION, KILL_COUNT_Y_POSITION, maxKills);
		this.bulletDisplay = new BulletDisplay(BULLET_DISPLAY_X_POSITION, BULLET_DISPLAY_Y_POSITION, maxBullets);
	}

	/**
	 * Shows all displays by adding them to the root group.
	 */
	public void showAllDisplays() {
		showHeartDisplay();
		showKillCountDisplay();
		showBulletDisplay();
	}

	/**
	 * Shows the heart display by adding it to the root group.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Shows the kill count display by adding it to the root group.
	 */
	public void showKillCountDisplay() {
		root.getChildren().add(killCountDisplay.getContainer());
	}

	public void showBulletDisplay() {
		root.getChildren().add(bulletDisplay.getContainer());
	}



	/**
	 * Removes hearts from the display based on the remaining number of hearts.
	 *
	 * @param heartsRemaining the number of hearts remaining.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Updates the kill count display with the specified number of kills.
	 *
	 * @param kills the current number of kills.
	 */
	public void updateKillCount(int kills) {
		killCountDisplay.updateKillCount(kills);
	}

	/**
	 * Updates the bullet display with the current bullet status.
	 *
	 * @param currentBullets the current number of bullets.
	 * @param isReloading whether the gun is reloading.
	 * @param reloadProgress the progress of reloading (0.0 to 1.0).
	 */
	public void updateBulletStatus(int currentBullets, boolean isReloading, double reloadProgress) {
		bulletDisplay.updateDisplay(currentBullets, isReloading, reloadProgress);
	}
}