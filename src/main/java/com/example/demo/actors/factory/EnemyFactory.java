package com.example.demo.actors.factory;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.EnemyPlane;
import com.example.demo.actors.planes.EnemyPlaneTwo;
import com.example.demo.actors.planes.Boss;

/**
 * Factory class for creating different types of enemy actors in the game.
 * Implements the Factory pattern to encapsulate enemy creation logic.
 * 
 * @see AbstractActorFactory
 * @see ActiveActorDestructible
 */
public class EnemyFactory extends AbstractActorFactory {
    /**
     * Enum representing the types of enemies that can be created.
     */
    public enum EnemyType {
        /** Regular enemy plane type */
        ENEMYPLANEONE,
        /** Advanced enemy plane type */
        ENEMYPLANETWO,
        /** Boss enemy type */
        BOSS
    }

    private final EnemyType type;

    /**
     * Constructs an EnemyFactory with the specified enemy type.
     *
     * @param type the type of enemy to create
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
     * Creates a new enemy actor at the specified coordinates.
     *
     * @param x the x-coordinate of the actor
     * @param y the y-coordinate of the actor
     * @return a new enemy instance of the configured type
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
     * Creates a new boss actor with predefined positioning.
     *
     * @return a new Boss instance
     * @throws IllegalStateException if the factory is not configured for boss creation
     */
    public Boss createBoss() {
        if (type != EnemyType.BOSS) {
            throw new IllegalStateException("This factory is not configured for boss creation");
        }
        return new Boss();
    }
}