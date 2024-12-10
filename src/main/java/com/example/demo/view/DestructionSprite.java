package com.example.demo.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Represents a sprite for destruction effects, such as explosions.
 *
 * @see javafx.animation.KeyFrame
 * @see javafx.animation.Timeline
 * @see javafx.scene.image.ImageView
 */
public class DestructionSprite {

    /**
     * The ImageView to display the explosion effect.
     */
    private final ImageView imageView;

    /**
     * The Timeline to control the animation of the explosion effect.
     */
    private final Timeline timeline;

    /**
     * The path to the explosion effect sprite images.
     */
    private final String explosionEffectSpritePath = "/com/example/demo/images/keyframes/";

    /**
     * The size of the sprite.
     */
    private final int spriteSize = 150;

    /**
     * The refresh rate for the sprite animation in milliseconds.
     */
    private final int spriteRefreshRate = 100;

    /**
     * Constructs a DestructionSprite with the specified position and root group.
     *
     * @param x the x-coordinate position of the sprite.
     * @param y the y-coordinate position of the sprite.
     * @param root the root group to which the sprite will be added.
     */
    public DestructionSprite(double x, double y, Group root) {
        this.imageView = new ImageView();
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.imageView.setFitWidth(spriteSize); // Set desired width
        this.imageView.setFitHeight(spriteSize); // Set desired height
        root.getChildren().add(imageView);

        timeline = new Timeline();
        for (int i = 1; i <= 9; i++) {
            String imagePath = explosionEffectSpritePath + "explosion_0" + i + ".png";
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * spriteRefreshRate), event -> {
                imageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> root.getChildren().remove(imageView));
    }

    /**
     * Plays the destruction effect.
     */
    public void playEffect() {
        timeline.play();
    }
}