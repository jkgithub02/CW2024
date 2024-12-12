package com.example.demo.actors.projectiles;

/**
 * Represents a projectile fired by an enemy in the game.
 *
 * @see Projectile
 */
public class EnemyProjectile extends Projectile {

	/**
	 * The image name for the enemy projectile.
	 */
	private static final String IMAGE_NAME = "enemyFire.png";

	/**
	 * The height of the enemy projectile image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The horizontal velocity of the enemy projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an EnemyProjectile with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the projectile.
	 * @param initialYPos the initial Y position of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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