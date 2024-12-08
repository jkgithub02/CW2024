package com.example.demo.actors.factory;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.BossProjectile;
import com.example.demo.UserProjectile;
import com.example.demo.EnemyProjectile;

/**
 * Factory class for creating different types of projectiles.
 */
public class ProjectileFactory extends AbstractActorFactory {
    /**
     * Enum representing the types of projectiles that can be created.
     */
    public enum ProjectileType {
        USER,   // UserProjectile
        ENEMY,  // EnemyProjectile
        BOSS    // BossProjectile
    }

    private final ProjectileType type;

    /**
     * Constructs a ProjectileFactory with the specified projectile type.
     *
     * @param type the type of projectile to create.
     */
    public ProjectileFactory(ProjectileType type) {
        super(switch (type) {
            case USER -> 10;   // UserProjectile.IMAGE_HEIGHT
            case ENEMY -> 50;  // EnemyProjectile.IMAGE_HEIGHT
            case BOSS -> 75;   // BossProjectile.IMAGE_HEIGHT
        }); // Different heights for user/enemy projectiles
        this.type = type;
    }

    /**
     * Creates a projectile actor at the specified coordinates.
     *
     * @param x the x-coordinate of the actor.
     * @param y the y-coordinate of the actor.
     * @return the created projectile actor.
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