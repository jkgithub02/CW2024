package com.example.demo.actors.projectiles;

/**
 * Represents a projectile fired by the boss in the game.
 *
 * @see Projectile
 */
public class BossProjectile extends Projectile {

	/**
	 * The image name for the boss projectile.
	 */
	private static final String IMAGE_NAME = "fireball.png";

	/**
	 * The height of the boss projectile image.
	 */
	private static final int IMAGE_HEIGHT = 75;

	/**
	 * The horizontal velocity of the boss projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = -15;

	/**
	 * The initial X position of the boss projectile.
	 */
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a BossProjectile with the specified initial Y position.
	 *
	 * @param initialYPos the initial Y position of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}