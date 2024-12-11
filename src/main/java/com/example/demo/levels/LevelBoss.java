package com.example.demo.levels;

import com.example.demo.actors.planes.Boss;
import com.example.demo.config.GameConfig;
import com.example.demo.actors.factory.EnemyFactory;
import com.example.demo.view.bosslevel.BossHealthBar;
import com.example.demo.view.bosslevel.LevelBossView;
import com.example.demo.view.LevelView;
import com.example.demo.view.bosslevel.ShieldImage;

/**
 * Represents the boss level in the game.
 *
 * @see com.example.demo.actors.planes.Boss
 * @see com.example.demo.actors.factory.EnemyFactory
 * @see BossHealthBar
 * @see LevelBossView
 * @see ShieldImage
 */
public class LevelBoss extends LevelParent {

	/**
	 * The background image name for the boss level.
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";

	/**
	 * The number of kills required to advance to the next level.
	 */
	private static final int KILLS_TO_ADVANCE = 1;

	/**
	 * The boss character of the level.
	 */
	private final Boss boss;

	/**
	 * The view for the boss level.
	 */
	private LevelBossView levelView;

	/**
	 * The shield image for the boss.
	 */
	private final ShieldImage shieldImage;

	/**
	 * The X-coordinate position of the shield.
	 */
	private static final int SHIELD_X_POSITION = 850;

	/**
	 * The Y-coordinate position of the shield.
	 */
	private static final int SHIELD_Y_POSITION = 0;

	/**
	 * The X-coordinate position of the boss health bar.
	 */
	private static final int HEALTH_BAR_X_POSITION = SHIELD_X_POSITION + 100;

	/**
	 * The Y-coordinate position of the boss health bar.
	 */
	private static final int HEALTH_BAR_Y_POSITION = 30;

	/**
	 * The factory to create enemy units.
	 */
	private final EnemyFactory enemyFactory;

	/**
	 * The boss health bar.
	 */
	private BossHealthBar bossHealthBar;

	/**
	 * Constructs a LevelBoss with the specified screen dimensions.
	 */
	public LevelBoss() {
		super(BACKGROUND_IMAGE_NAME, GameConfig.PLAYER_INITIAL_HEALTH);
		this.enemyFactory = new EnemyFactory(EnemyFactory.EnemyType.BOSS);
		this.boss = (Boss) enemyFactory.createActor(0, 0);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthBar = new BossHealthBar(HEALTH_BAR_X_POSITION, HEALTH_BAR_Y_POSITION, boss.getHealth());
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
			addHealthBar();
		}
	}

	/**
	 * Adds the shield image to the root and sets its visibility to false.
	 */
	private void addShieldImage() {
		shieldImage.setVisible(false);
		getRoot().getChildren().add(shieldImage);
	}

	/**
	 * Adds the boss health bar to the root.
	 */
	private void addHealthBar() {
		getRoot().getChildren().add(bossHealthBar);
	}

	/**
	 * Instantiates the view for the boss level.
	 *
	 * @return the view for the boss level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelBossView(getRoot(), GameConfig.PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE, GameConfig.PLAYER_MAX_BULLETS);
		return levelView;
	}

	/**
	 * Updates the level view and shows or hides the shield image based on the boss's shield status.
	 */
	@Override
	protected void updateLevelView() {
		super.updateLevelView();
		// Update the boss health bar
		if (bossHealthBar != null && boss != null) {
			bossHealthBar.updateHealth(boss.getHealth());
		}

		if (boss.isShielded()) {
			shieldImage.showShield();
		} else {
			shieldImage.hideShield();
		}
	}
}