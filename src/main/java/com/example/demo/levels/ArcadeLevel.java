package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.EnemyFactory;
import com.example.demo.view.ArcadeLevelView;
import com.example.demo.view.LevelView;
import com.example.demo.config.GameConfig;
import com.example.demo.managers.LeaderboardManager;

/**
 * Class representing the arcade level in the game.
 *
 * @see com.example.demo.actors.ActiveActorDestructible
 * @see com.example.demo.actors.factory.EnemyFactory
 * @see com.example.demo.view.ArcadeLevelView
 * @see com.example.demo.managers.LeaderboardManager
 */
public class ArcadeLevel extends LevelParent {

    /**
     * The background image name for the arcade level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgroundarcade2.jpg";

    /**
     * The initial number of enemies.
     */
    private static final int INITIAL_ENEMIES = 5;

    /**
     * The maximum number of enemies.
     */
    private static final int MAXIMUM_ENEMIES = 8;

    /**
     * The initial spawn rate of enemies.
     */
    private static final double INITIAL_SPAWN_RATE = 0.25;

    /**
     * The upper bound for the enemy Y position.
     */
    private static final double ENEMY_Y_UPPER_BOUND = 30;

    /**
     * The increment value for the spawn rate.
     */
    private static final double spawnRateIncrement = 0.05;

    /**
     * The maximum cap for the spawn rate.
     */
    private static final double spawnRateCap = 0.75;

    /**
     * The total number of enemies.
     */
    private int totalEnemies;

    /**
     * The current spawn rate of enemies.
     */
    private double spawnRate;

    /**
     * The current wave of enemies.
     */
    private int currentWave;

    /**
     * The factory to create enemy units.
     */
    private final EnemyFactory enemyFactory;

    /**
     * Constructs an ArcadeLevel with initial settings.
     */
    public ArcadeLevel() {
        super(BACKGROUND_IMAGE_NAME, GameConfig.PLAYER_INITIAL_HEALTH);
        this.enemyFactory = new EnemyFactory(EnemyFactory.EnemyType.ENEMYPLANEONE);
        this.totalEnemies = INITIAL_ENEMIES;
        this.spawnRate = INITIAL_SPAWN_RATE;
        this.currentWave = 1;
    }

    /**
     * Checks if the game is over and handles the end game logic.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            // Save score before ending game
            saveScore(getUser().getKillCount());
            loseGame();
        } else if (getUser().getKillCount() >= totalEnemies) {
            nextWave();
        }
    }

    /**
     * Advances to the next wave of enemies, increasing difficulty.
     */
    private void nextWave() {
        currentWave++;
        totalEnemies += 1;
        spawnRate = Math.min(spawnRate + spawnRateIncrement, spawnRateCap);
    }

    /**
     * Spawns enemy units based on the current wave and spawn rate.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentEnemies = getCurrentNumberOfEnemies();
        int maxEnemies = Math.min(totalEnemies, MAXIMUM_ENEMIES);
        for (int i = 0; i < maxEnemies - currentEnemies; i++) {
            if (Math.random() < spawnRate) {
                double yPos = ENEMY_Y_UPPER_BOUND + Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible enemy = enemyFactory.createActor(getScreenWidth(), yPos);
                addEnemyUnit(enemy);
            }
        }
    }

    /**
     * Instantiates the view for the arcade level.
     *
     * @return the level view.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new ArcadeLevelView(getRoot(), GameConfig.PLAYER_INITIAL_HEALTH);
    }

    /**
     * Saves the player's score to the leaderboard.
     *
     * @param score the score to save.
     */
    private void saveScore(int score) {
        LeaderboardManager.addScore(score);
    }
}