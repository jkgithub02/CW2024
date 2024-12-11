package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.view.effects.DestructionSprite;
import com.example.demo.levels.LevelParent;
import javafx.scene.Group;

/**
 * Represents a fighter plane in the game, which is a type of ActiveActorDestructible.
 *
 * @see ActiveActorDestructible
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The health of the fighter plane.
	 */
	private int health;

	/**
	 * Constructs a FighterPlane with the specified attributes.
	 *
	 * @param imageName the image name of the fighter plane.
	 * @param imageHeight the height of the fighter plane image.
	 * @param initialXPos the initial X position of the fighter plane.
	 * @param initialYPos the initial Y position of the fighter plane.
	 * @param health the initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 *
	 * @return a new projectile representing the fired projectile.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Inflicts damage to the fighter plane.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Destroys the fighter plane and plays the destruction effect.
	 */
	@Override
	public void destroy() {
		playDestructionEffect();
		super.destroy();
	}

	/**
	 * Plays the destruction effect for the fighter plane.
	 */
	private void playDestructionEffect() {
		Group root = LevelParent.getGlobalRoot(); // Access the global root here
		DestructionSprite destructionSprite = new DestructionSprite(getLayoutX() + getTranslateX(), getLayoutY() + getTranslateY(), root);
		destructionSprite.playEffect();
	}

	/**
	 * Gets the X position for the fighter plane projectile.
	 *
	 * @param xPositionOffset the X position offset for the projectile.
	 * @return the X position for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Gets the Y position for the fighter plane projectile.
	 *
	 * @param yPositionOffset the Y position offset for the projectile.
	 * @return the Y position for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the fighter plane is zero.
	 *
	 * @return true if the health is zero, false otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the health of the fighter plane.
	 *
	 * @return the health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}
}