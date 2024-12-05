package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.config.GameConfig;
import com.example.demo.actors.factory.EnemyFactory;
import com.example.demo.view.LevelView;

/**
 * Represents the first level in the game.
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.levels.LevelTwo";
	private static final String NEXT_LEVEL_NAME = "Level Two";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final double ENEMY_Y_UPPER_BOUND = 30;
	private final EnemyFactory enemyFactory;

	/**
	 * Constructs a LevelOne with the specified screen dimensions.
	 */
	public LevelOne() {
		super(BACKGROUND_IMAGE_NAME, GameConfig.PLAYER_INITIAL_HEALTH);
		this.enemyFactory = new EnemyFactory(EnemyFactory.EnemyType.ENEMYPLANEONE);
	}

	/**
	 * Checks if the game is over by evaluating the state of the user and the kill target.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL, NEXT_LEVEL_NAME);
		}
	}

	/**
	 * Spawns enemy units in the level based on the spawn probability.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = ENEMY_Y_UPPER_BOUND + Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = enemyFactory.createActor(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Instantiates the view for the first level.
	 *
	 * @return the view for the first level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), GameConfig.PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE, GameConfig.PLAYER_MAX_BULLETS);
	}

	/**
	 * Checks if the user has reached the kill target to advance to the next level.
	 *
	 * @return true if the user has reached the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getKillCount() >= KILLS_TO_ADVANCE;
	}
}