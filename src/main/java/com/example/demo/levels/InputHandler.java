package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Set;

public class InputHandler {

    private final Set<KeyCode> pressedKeys;
    private final UserPlane user;
    private final ImageView background;
    private final Group root;
    private final List<ActiveActorDestructible> userProjectiles;

    public InputHandler(Set<KeyCode> pressedKeys, UserPlane user, ImageView background, Group root, List<ActiveActorDestructible> userProjectiles) {
        this.pressedKeys = pressedKeys;
        this.user = user;
        this.background = background;
        this.root = root;
        this.userProjectiles = userProjectiles;
    }

    public void initializeFireProjectileHandler() {
        background.setOnKeyPressed(e -> {
            pressedKeys.add(e.getCode());
            if (e.getCode() == KeyCode.SPACE) fireProjectile();
        });
        background.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    public void updateUserPlaneMovement() {
        if (pressedKeys.contains(KeyCode.UP)) user.moveUp();
        if (pressedKeys.contains(KeyCode.DOWN)) user.moveDown();
        if (pressedKeys.contains(KeyCode.LEFT)) user.moveLeft();
        if (pressedKeys.contains(KeyCode.RIGHT)) user.moveRight();
        if (!pressedKeys.contains(KeyCode.UP) && !pressedKeys.contains(KeyCode.DOWN)) user.stopVertical();
        if (!pressedKeys.contains(KeyCode.LEFT) && !pressedKeys.contains(KeyCode.RIGHT)) user.stopHorizontal();
    }
}