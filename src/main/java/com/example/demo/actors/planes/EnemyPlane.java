package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.factory.ProjectileFactory;

/**
 * Represents an enemy plane in the game, which is a type of FighterPlane.
 *
 * @see FighterPlane
 * @see ActiveActorDestructible
 */
public class EnemyPlane extends FighterPlane {

	/**
	 * The image name for the enemy plane.
	 */
	private static final String IMAGE_NAME = "enemyplane.png";

	/**
	 * The height of the enemy plane image.
	 */
	private static final int IMAGE_HEIGHT = 80;

	/**
	 * The horizontal velocity of the enemy plane.
	 */
	private static final int HORIZONTAL_VELOCITY = -8;

	/**
	 * The X position offset for the enemy plane projectile.
	 */
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

	/**
	 * The Y position offset for the enemy plane projectile.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0;

	/**
	 * The initial health of the enemy plane.
	 */
	private static final int INITIAL_HEALTH = 1;

	/**
	 * The fire rate of the enemy plane.
	 */
	private static final double FIRE_RATE = .01;

	/**
	 * The projectile factory for creating projectiles.
	 */
	private final ProjectileFactory projectileFactory;

	/**
	 * Constructs an EnemyPlane with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the enemy plane.
	 * @param initialYPos the initial Y position of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.projectileFactory = new ProjectileFactory(ProjectileFactory.ProjectileType.ENEMY);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile if the enemy plane decides to fire in the current frame.
	 *
	 * @return a new EnemyProjectile if the enemy plane fires, otherwise null.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return projectileFactory.createActor(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}