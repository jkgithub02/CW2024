package com.example.demo.actors;



/**
 * Abstract class representing an active actor that can be destroyed.
 *
 * @see com.example.demo.actors.ActiveActor
 * @see com.example.demo.actors.Destructible
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * Indicates whether the actor is destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * The type of destruction.
	 */
	private DestructionType destructionType;

	/**
	 * Constructs an ActiveActorDestructible with the specified parameters.
	 *
	 * @param imageName the name of the image representing the actor.
	 * @param imageHeight the height of the image.
	 * @param initialXPos the initial X position of the actor.
	 * @param initialYPos the initial Y position of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor.
	 */
	public abstract void updateActor();

	/**
	 * Inflicts damage to the actor.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Destroys the actor with a specified destruction type.
	 *
	 * @param type the type of destruction.
	 */
	public void destroy(DestructionType type) {
		this.destructionType = type;
		this.isDestroyed = true;
	}

	/**
	 * Gets the type of destruction.
	 *
	 * @return the destruction type.
	 */
	public DestructionType getDestructionType() {
		return destructionType;
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed true if the actor is destroyed, false otherwise.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}