package com.example.demo.actors.factory;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.projectiles.BossProjectile;
import com.example.demo.actors.projectiles.UserProjectile;
import com.example.demo.actors.projectiles.EnemyProjectile;

/**
 * Factory class for creating different types of projectiles in the game.
 * Implements the Factory pattern to encapsulate projectile creation logic.
 * 
 * @see AbstractActorFactory
 * @see ActiveActorDestructible
 */
public class ProjectileFactory extends AbstractActorFactory {
    /**
     * Enum representing the types of projectiles that can be created.
     * Each type corresponds to a specific projectile class implementation.
     */
    public enum ProjectileType {
        /** Player-fired projectiles */
        USER,   
        /** Enemy-fired projectiles */
        ENEMY,  
        /** Boss-fired projectiles */
        BOSS    
    }

    private final ProjectileType type;

    /**
     * Constructs a ProjectileFactory with the specified projectile type.
     *
     * @param type the type of projectile to create
     */
    public ProjectileFactory(ProjectileType type) {
        super(switch (type) {
            case USER -> 10;   // UserProjectile.IMAGE_HEIGHT
            case ENEMY -> 50;  // EnemyProjectile.IMAGE_HEIGHT
            case BOSS -> 75;   // BossProjectile.IMAGE_HEIGHT
        });
        this.type = type;
    }

    /**
     * Creates a new projectile actor at the specified coordinates.
     *
     * @param x the x-coordinate of the actor
     * @param y the y-coordinate of the actor
     * @return a new projectile instance of the configured type
     */
    @Override
    public ActiveActorDestructible createActor(double x, double y) {
        return switch(type) {
            case USER -> new UserProjectile(x, y);
            case ENEMY -> new EnemyProjectile(x, y);
            case BOSS -> new BossProjectile(y);  // Boss has fixed initial position
        };
    }
}