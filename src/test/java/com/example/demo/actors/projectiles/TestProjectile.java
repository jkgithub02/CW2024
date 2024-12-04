package com.example.demo.actors.projectiles;

import com.example.demo.actors.ActiveActorDestructible;

public class TestProjectile extends ActiveActorDestructible {

    public TestProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        // Implement method
    }

    @Override
    public void updateActor() {
        // Implement method
    }

    @Override
    public void takeDamage() {
        // Implement method
    }
}
