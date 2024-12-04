package com.example.demo.actors;

public class TestActor extends ActiveActorDestructible {

    public TestActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
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