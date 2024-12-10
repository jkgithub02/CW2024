package com.example.demo.actors.projectiles;

/**
 * Represents a projectile fired by the user in the game.
 *
 * @see Projectile
 */
public class UserProjectile extends Projectile {

	/**
	 * The image name for the user projectile.
	 */
	private static final String IMAGE_NAME = "userfire.png";

	/**
	 * The height of the user projectile image.
	 */
	private static final int IMAGE_HEIGHT = 10;

	/**
	 * The horizontal velocity of the user projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a UserProjectile with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the projectile.
	 * @param initialYPos the initial Y position of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the user projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the user projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}