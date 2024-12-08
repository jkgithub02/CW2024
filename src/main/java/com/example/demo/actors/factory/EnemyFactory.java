package com.example.demo.actors.factory;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.EnemyPlane;
import com.example.demo.EnemyPlaneTwo;
import com.example.demo.Boss;

/**
 * Factory class for creating different types of enemy actors.
 */
public class EnemyFactory extends AbstractActorFactory {
    /**
     * Enum representing the types of enemies that can be created.
     */
    public enum EnemyType {
        ENEMYPLANEONE,      // Regular EnemyPlane
        ENEMYPLANETWO,      // EnemyPlaneTwo
        BOSS                // Boss
    }

    private final EnemyType type;

    /**
     * Constructs an EnemyFactory with the specified enemy type.
     *
     * @param type the type of enemy to create.
     */
    public EnemyFactory(EnemyType type) {
        super(switch(type) {
            case ENEMYPLANEONE -> 80;     // EnemyPlane.IMAGE_HEIGHT
            case ENEMYPLANETWO -> 60;     // EnemyPlaneTwo.IMAGE_HEIGHT
            case BOSS -> 60;              // Boss.IMAGE_HEIGHT
        });
        this.type = type;
    }

    /**
     * Creates an enemy actor at the specified coordinates.
     *
     * @param x the x-coordinate of the actor.
     * @param y the y-coordinate of the actor.
     * @return the created enemy actor.
     */
    @Override
    public ActiveActorDestructible createActor(double x, double y) {
        return switch(type) {
            case ENEMYPLANEONE -> new EnemyPlane(x, y);
            case ENEMYPLANETWO -> new EnemyPlaneTwo(x, y);
            case BOSS -> new Boss();  // Boss has fixed initial position
        };
    }

    /**
     * Creates a boss actor. This method should only be called if the factory is configured for boss creation.
     *
     * @return the created boss actor.
     * @throws IllegalStateException if the factory is not configured for boss creation.
     */
    public Boss createBoss() {
        if (type != EnemyType.BOSS) {
            throw new IllegalStateException("This factory is not configured for boss creation");
        }
        return new Boss();
    }
}