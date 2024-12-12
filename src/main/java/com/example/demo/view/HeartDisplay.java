package com.example.demo.view;

import com.example.demo.managers.SoundManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * Represents a display of hearts indicating the player's health.
 *
 * @see com.example.demo.managers.SoundManager
 * @see javafx.scene.image.ImageView
 * @see javafx.scene.layout.HBox
 */
public class HeartDisplay {

	/**
	 * The path to the heart image.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/**
	 * The height of each heart image.
	 */
	private static final int HEART_HEIGHT = 40;

	/**
	 * The index of the first item in the container.
	 */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/**
	 * The container for the heart images.
	 */
	private HBox container;

	/**
	 * The x-coordinate position of the container.
	 */
	private double containerXPosition;

	/**
	 * The y-coordinate position of the container.
	 */
	private double containerYPosition;

	/**
	 * The number of hearts to display.
	 */
	private int numberOfHeartsToDisplay;

	/**
	 * The SoundManager instance to play sounds.
	 */
	private final SoundManager soundManager = SoundManager.getInstance();

	/**
	 * Constructs a HeartDisplay with the specified position and number of hearts.
	 *
	 * @param xPosition the x-coordinate position of the heart display.
	 * @param yPosition the y-coordinate position of the heart display.
	 * @param heartsToDisplay the number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for the heart display.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the hearts in the display.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes a heart from the display.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Returns the container for the heart display.
	 *
	 * @return the container for the heart display.
	 */
	public HBox getContainer() {
		return container;
	}
}