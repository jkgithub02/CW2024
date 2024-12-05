package com.example.demo.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DestructionSprite {

    private final ImageView imageView;
    private final Timeline timeline;
    private final String explosionEffectSpritePath = "/com/example/demo/images/keyframes/";
    private final int spriteSize = 150;
    private final int spriteRefreshRate = 100;


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

    public void playEffect() {
        timeline.play();
    }
}