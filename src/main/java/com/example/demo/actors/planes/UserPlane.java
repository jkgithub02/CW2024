package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.ProjectileFactory;
import com.example.demo.managers.gameManagers.BulletManager;
import com.example.demo.managers.SoundManager;
import javafx.animation.AnimationTimer;

/**
 * Represents the user's plane in the game, which is a type of FighterPlane.
 *
 * @see FighterPlane
 * @see ActiveActorDestructible
 */
public class UserPlane extends FighterPlane {

	/**
	 * The image name for the user's plane.
	 */
	private static final String IMAGE_NAME = "userplane.png";

	/**
	 * The lower bound for the X position of the user's plane.
	 */
	private static final int X_LOWER_BOUND = 0;

	/**
	 * The upper bound for the X position of the user's plane.
	 */
	private static final int X_UPPER_BOUND = 900;

	/**
	 * The upper bound for the Y position of the user's plane.
	 */
	private static final double Y_UPPER_BOUND = 75;

	/**
	 * The lower bound for the Y position of the user's plane.
	 */
	private static final double Y_LOWER_BOUND = 650;

	/**
	 * The initial X position of the user's plane.
	 */
	private static final double INITIAL_X_POSITION = 50.0;

	/**
	 * The initial Y position of the user's plane.
	 */
	private static final double INITIAL_Y_POSITION = 300.0;

	/**
	 * The height of the user's plane image.
	 */
	private static final int IMAGE_HEIGHT = 45;

	/**
	 * The vertical velocity of the user's plane.
	 */
	private static final double VERTICAL_VELOCITY = 1.2;

	/**
	 * The horizontal velocity of the user's plane.
	 */
	private static final double HORIZONTAL_VELOCITY = 1.2;

	/**
	 * The X position offset for the user's plane projectile.
	 */
	private static final int PROJECTILE_X_POSITION = 110;

	/**
	 * The Y position offset for the user's plane projectile.
	 */
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

	/**
	 * The maximum number of bullets the user's plane can have.
	 */
	private static final int MAX_BULLETS = 10;

	/**
	 * The reload time for the user's plane.
	 */
	private static final double RELOAD_TIME = 1.0;

	/**
	 * The vertical velocity multiplier for the user's plane.
	 */
	private int verticalVelocityMultiplier;

	/**
	 * The horizontal velocity multiplier for the user's plane.
	 */
	private int horizontalVelocityMultiplier;

	/**
	 * The number of kills made by the user's plane.
	 */
	private int numberOfKills;

	/**
	 * The projectile factory for creating projectiles.
	 */
	private final ProjectileFactory projectileFactory;

	/**
	 * The sound manager for playing sounds.
	 */
	private final SoundManager soundManager = SoundManager.getInstance();

	/**
	 * The bullet manager for managing bullets.
	 */
	private final BulletManager bulletManager;

	/**
	 * Constructs a UserPlane with the specified initial health.
	 *
	 * @param initialHealth the initial health of the user's plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.projectileFactory = new ProjectileFactory(ProjectileFactory.ProjectileType.USER);
		this.bulletManager = new BulletManager(MAX_BULLETS, RELOAD_TIME);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
		startAnimation();
	}

	/**
	 * Starts the animation timer to update the position of the user's plane.
	 */
	private void startAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				updatePosition();
			}
		};
		timer.start();
	}

	/**
	 * Updates the position of the user's plane based on the current velocity multipliers.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX(); // Store initial X translation

			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier); // Use vertical multiplier
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier); // Use horizontal multiplier

			double newPositionY = getLayoutY() + getTranslateY();
			double newPositionX = getLayoutX() + getTranslateX(); // Calculate new X position

			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}

			// Add bounds for X movement (replace with your actual bounds)
			if (newPositionX < X_LOWER_BOUND || newPositionX > X_UPPER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Inflicts damage to the user's plane and plays the appropriate sound.
	 */
	@Override
	public void takeDamage() {
		super.takeDamage();
		soundManager.playDamagedSound("user");
	}

	/**
	 * Updates the state of the user's plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user's plane.
	 *
	 * @return a new UserProjectile representing the fired projectile.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bulletManager.canShoot()) {
			bulletManager.shoot();
			double projectileX = getLayoutX() + getTranslateX() + PROJECTILE_X_POSITION;
			double projectileY = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			soundManager.playShootSound("user");
			return projectileFactory.createActor(projectileX, projectileY);
		}
		return null;
	}

	/**
	 * Checks if the user's plane is currently moving.
	 *
	 * @return true if the plane is moving, false otherwise.
	 */
	private boolean isMoving() {
		return (horizontalVelocityMultiplier != 0 || verticalVelocityMultiplier != 0);
	}

	/**
	 * Moves the user's plane up by setting the vertical velocity multiplier to -2.
	 */
	public void moveUp() {
		setVerticalVelocityMultiplier(-2);
	}

	/**
	 * Moves the user's plane down by setting the vertical velocity multiplier to 2.
	 */
	public void moveDown() {
		setVerticalVelocityMultiplier(2);
	}

	/**
	 * Moves the user's plane left by setting the horizontal velocity multiplier to -2.
	 */
	public void moveLeft() {
		setHorizontalVelocityMultiplier(-2);
	}

	/**
	 * Moves the user's plane right by setting the horizontal velocity multiplier to 2.
	 */
	public void moveRight() {
		setHorizontalVelocityMultiplier(2);
	}

	/**
	 * Stops the horizontal movement of the user's plane by setting the horizontal velocity multiplier to 0.
	 */
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0; // Stop horizontal movement
	}

	/**
	 * Stops the vertical movement of the user's plane by setting the vertical velocity multiplier to 0.
	 */
	public void stopVertical() {
		verticalVelocityMultiplier = 0; // Stop vertical movement
	}

	/**
	 * Gets the current kill count of the user's plane.
	 *
	 * @return the current kill count.
	 */
	public int getKillCount() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the user's plane by 1.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Gets the vertical velocity multiplier of the user's plane.
	 *
	 * @return the vertical velocity multiplier.
	 */
	public int getVerticalVelocityMultiplier() {
		return verticalVelocityMultiplier;
	}

	/**
	 * Gets the horizontal velocity multiplier of the user's plane.
	 *
	 * @return the horizontal velocity multiplier.
	 */
	public int getHorizontalVelocityMultiplier() {
		return horizontalVelocityMultiplier;
	}

	/**
	 * Sets the vertical velocity multiplier of the user's plane.
	 *
	 * @param verticalVelocityMultiplier the new vertical velocity multiplier.
	 */
	public void setVerticalVelocityMultiplier(int verticalVelocityMultiplier) {
		this.verticalVelocityMultiplier = verticalVelocityMultiplier;
	}

	/**
	 * Sets the horizontal velocity multiplier of the user's plane.
	 *
	 * @param horizontalVelocityMultiplier the new horizontal velocity multiplier.
	 */
	public void setHorizontalVelocityMultiplier(int horizontalVelocityMultiplier) {
		this.horizontalVelocityMultiplier = horizontalVelocityMultiplier;
	}

	/**
	 * Gets the current number of bullets available for the user's plane.
	 *
	 * @return the current number of bullets.
	 */
	public int getCurrentBullets() {
		return bulletManager.getCurrentBullets();
	}

	/**
	 * Checks if the user's plane is currently reloading.
	 *
	 * @return true if the plane is reloading, false otherwise.
	 */
	public boolean isReloading() {
		return bulletManager.isReloading();
	}

	/**
	 * Gets the reload progress of the user's plane.
	 *
	 * @return the reload progress as a percentage.
	 */
	public double getReloadProgress() {
		return bulletManager.getReloadProgress();
	}

	/**
	 * Pauses the reloading process of the user's plane.
	 */
	public void pauseReload() {
		bulletManager.pause();
	}

	/**
	 * Resumes the reloading process of the user's plane.
	 */
	public void resumeReload() {
		bulletManager.resume();
	}
}