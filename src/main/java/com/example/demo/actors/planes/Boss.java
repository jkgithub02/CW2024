package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.ProjectileFactory;
import com.example.demo.managers.SoundManager;

import java.util.*;

/**
 * Represents a boss enemy in the game, which is a type of FighterPlane.
 *
 * @see FighterPlane
 * @see ActiveActorDestructible
 */
public class Boss extends FighterPlane {

	/**
	 * The image name for the boss plane.
	 */
	private static final String IMAGE_NAME = "bossplane.png";

	/**
	 * The initial X position of the boss plane.
	 */
	private static final double INITIAL_X_POSITION = 950.0;

	/**
	 * The initial Y position of the boss plane.
	 */
	private static final double INITIAL_Y_POSITION = 400;

	/**
	 * The Y position offset for the boss plane projectile.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;

	/**
	 * The fire rate of the boss plane.
	 */
	private static final double BOSS_FIRE_RATE = .05;

	/**
	 * The probability of the boss plane activating its shield.
	 */
	private static final double BOSS_SHIELD_PROBABILITY = .002;

	/**
	 * The height of the boss plane image.
	 */
	private static final int IMAGE_HEIGHT = 60;

	/**
	 * The vertical velocity of the boss plane.
	 */
	private static final int VERTICAL_VELOCITY = 8;

	/**
	 * The health of the boss plane.
	 */
	private static final int HEALTH = 50;

	/**
	 * The frequency of moves per cycle for the boss plane.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/**
	 * The value representing zero movement.
	 */
	private static final int ZERO = 0;

	/**
	 * The maximum number of frames the boss plane can move in the same direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

	/**
	 * The upper bound for the Y position of the boss plane.
	 */
	private static final int Y_POSITION_UPPER_BOUND = 75;

	/**
	 * The lower bound for the Y position of the boss plane.
	 */
	private static final int Y_POSITION_LOWER_BOUND = 630;

	/**
	 * The maximum number of frames the boss plane can have its shield activated.
	 */
	private static final int MAX_FRAMES_WITH_SHIELD = 500;

	/**
	 * The move pattern for the boss plane.
	 */
	private final List<Integer> movePattern;

	/**
	 * Indicates whether the boss plane is shielded.
	 */
	private boolean isShielded;

	/**
	 * The number of consecutive moves in the same direction.
	 */
	private int consecutiveMovesInSameDirection;

	/**
	 * The index of the current move in the move pattern.
	 */
	private int indexOfCurrentMove;

	/**
	 * The number of frames with the shield activated.
	 */
	private int framesWithShieldActivated;

	/**
	 * The projectile factory for creating projectiles.
	 */
	private final ProjectileFactory projectileFactory;

	/**
	 * The sound manager for playing sounds.
	 */
	private final SoundManager soundManager = SoundManager.getInstance();

	/**
	 * Constructs a Boss instance with predefined attributes.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.projectileFactory = new ProjectileFactory(ProjectileFactory.ProjectileType.BOSS);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Updates the position of the boss based on its move pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the state of the boss, including its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the boss decides to fire in the current frame.
	 *
	 * @return a new BossProjectile if the boss fires, otherwise null.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()) {
			return projectileFactory.createActor(getLayoutX(), getProjectileInitialPosition());
		}
		return null;
	}

	/**
	 * Inflicts damage to the boss if it is not shielded.
	 */
	@Override
	public void takeDamage() {
		if (isShielded) {
			soundManager.playDamagedSound("shield");  // Play shield block sound
		} else {
			super.takeDamage();
			soundManager.playDamagedSound("enemy");
		}
	}

	/**
	 * Initializes the move pattern for the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (shieldExhausted()) {
				deactivateShield();
			}
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}
	}

	/**
	 * Gets the next move for the boss based on its move pattern.
	 *
	 * @return the next move value.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss should fire a projectile in the current frame.
	 *
	 * @return true if the boss fires, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial position for the boss's projectile.
	 *
	 * @return the Y position for the projectile.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the shield should be activated.
	 *
	 * @return true if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the shield has been exhausted.
	 *
	 * @return true if the shield is exhausted, false otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield for the boss.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the shield for the boss.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Checks if the boss is shielded.
	 *
	 * @return true if the boss is shielded, false otherwise.
	 */
	public boolean isShielded() {
		return isShielded;
	}

	/**
	 * Gets the health of the boss.
	 *
	 * @return the health of the boss.
	 */
	public int getHealth() {
		return super.getHealth();
	}
}