package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.view.DestructionSprite;
import com.example.demo.levels.LevelParent;
import javafx.scene.Group;

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();

	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	@Override
	public void destroy() {
		playDestructionEffect();
		super.destroy();
	}

	private void playDestructionEffect() {
		Group root = LevelParent.getGlobalRoot(); // Access the global root here
		DestructionSprite destructionSprite = new DestructionSprite( getLayoutX() + getTranslateX(), getLayoutY() + getTranslateY(), root);
		destructionSprite.playEffect();
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	public int getHealth() {
		return health;
	}
}