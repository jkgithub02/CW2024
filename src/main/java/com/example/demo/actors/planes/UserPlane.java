package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.ProjectileFactory;
import com.example.demo.managers.BulletManager;
import com.example.demo.managers.SoundManager;
import javafx.animation.AnimationTimer;

/**
 * Represents the user's plane in the game, which is a type of FighterPlane.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final int X_LOWER_BOUND =0;
	private static final int X_UPPER_BOUND = 900;
	private static final double Y_UPPER_BOUND = 75;
	private static final double Y_LOWER_BOUND = 650;
	private static final double INITIAL_X_POSITION = 50.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 45;
	private static final double VERTICAL_VELOCITY = 1.1;
	private static final double HORIZONTAL_VELOCITY = 1.1;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int verticalVelocityMultiplier; // Separate multiplier for vertical movement
	private int horizontalVelocityMultiplier; // Separate multiplier for horizontal movement
	private int numberOfKills;
	private final ProjectileFactory projectileFactory;
	private final SoundManager soundManager = SoundManager.getInstance();
	private static final int MAX_BULLETS = 10;
	private static final double RELOAD_TIME = 1.0;
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
	 * Moves the user's plane up by setting the vertical velocity multiplier to -1.
	 */
	public void moveUp() {
		setVerticalVelocityMultiplier(-2);
	}

	/**
	 * Moves the user's plane down by setting the vertical velocity multiplier to 1.
	 */
	public void moveDown() {
		setVerticalVelocityMultiplier(2);
	}

	/**
	 * Moves the user's plane left by setting the horizontal velocity multiplier to -1.
	 */
	public void moveLeft() {
		setHorizontalVelocityMultiplier(-2);
	}

	/**
	 * Moves the user's plane right by setting the horizontal velocity multiplier to 1.
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

    public int getVerticalVelocityMultiplier() {
		return verticalVelocityMultiplier;
    }

	public int getHorizontalVelocityMultiplier() {
		return horizontalVelocityMultiplier;
	}

	public void setVerticalVelocityMultiplier(int verticalVelocityMultiplier) {
		this.verticalVelocityMultiplier = verticalVelocityMultiplier;
	}

	public void setHorizontalVelocityMultiplier(int horizontalVelocityMultiplier) {
		this.horizontalVelocityMultiplier = horizontalVelocityMultiplier;
	}

	public int getCurrentBullets() {
		return bulletManager.getCurrentBullets();
	}

	public boolean isReloading() {
		return bulletManager.isReloading();
	}

	public double getReloadProgress() {
		return bulletManager.getReloadProgress();
	}

	public void pauseReload() {
		bulletManager.pause();
	}

	public void resumeReload() {
		bulletManager.resume();
	}


}