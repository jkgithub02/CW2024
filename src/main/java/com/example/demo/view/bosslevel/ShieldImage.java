package com.example.demo.view.bosslevel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents an image of a shield in the game.
 *
 * @see javafx.scene.image.ImageView
 */
public class ShieldImage extends ImageView {

	/**
	 * The path to the shield image.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";

	/**
	 * The size of the shield image.
	 */
	private static final int SHIELD_SIZE = 100;

	/**
	 * Constructs a ShieldImage with the specified position.
	 *
	 * @param xPosition the x-coordinate position of the shield image.
	 * @param yPosition the y-coordinate position of the shield image.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setPreserveRatio(true);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Shows the shield image by making it visible.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield image by making it invisible.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}