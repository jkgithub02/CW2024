package com.example.demo.levels;

import com.example.demo.actors.planes.Boss;
import com.example.demo.view.LevelBossView;
import com.example.demo.view.LevelView;
import com.example.demo.view.ShieldImage;

/**
 * Represents the boss level in the game.
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int KILLS_TO_ADVANCE = 1;
	private final Boss boss;
	private LevelBossView levelView;
	private final ShieldImage shieldImage;
	private static final int SHIELD_X_POSITION = 1100; // X-coordinate position of the shield
	private static final int SHIELD_Y_POSITION = 0; // Y-coordinate position of the shield

	/**
	 * Constructs a LevelBoss with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}

	/**
	 * Initializes the friendly units in the level.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if the game is over by evaluating the state of the user and the boss.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			winGame();
		}
	}

	/**
	 * Spawns enemy units in the level.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			addShieldImage();
		}
	}

	private void addShieldImage() {
		shieldImage.setVisible(false);
		getRoot().getChildren().add(shieldImage);
	}

	/**
	 * Instantiates the view for the boss level.
	 *
	 * @return the view for the boss level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelBossView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
		return levelView;
	}

	@Override
	protected void updateLevelView() {
		super.updateLevelView();
		if (boss.isShielded()) {
			shieldImage.showShield();
		} else {
			shieldImage.hideShield();
		}
	}
}