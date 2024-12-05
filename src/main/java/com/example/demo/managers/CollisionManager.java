package com.example.demo.managers;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.DestructionType;
import com.example.demo.actors.planes.FighterPlane;

import java.util.List;

/**
 * Manages collision detection and handling between different game entities.
 */
public class CollisionManager {

    private final SoundManager soundManager;

    public CollisionManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public CollisionManager() {
        this(SoundManager.getInstance());
    }

    /**
     * Handles collisions between friendly and enemy planes.
     *
     * @param friendlyUnits the list of friendly units.
     * @param enemyUnits the list of enemy units.
     */
    public void handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits,
                                      List<ActiveActorDestructible> enemyUnits) {
        for (ActiveActorDestructible friendly : friendlyUnits) {
            for (ActiveActorDestructible enemy : enemyUnits) {
                if (friendly.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    friendly.takeDamage();
                    enemy.takeDamage();

                    // Check if either plane should be destroyed
                    if (friendly instanceof FighterPlane && ((FighterPlane) friendly).getHealth() <= 0) {
                        friendly.destroy(DestructionType.COLLISION);
                    }
                    if (enemy instanceof FighterPlane && ((FighterPlane) enemy).getHealth() <= 0) {
                        enemy.destroy(DestructionType.COLLISION);
                        soundManager.playDamagedSound("enemy");
                    }
                }
            }
        }
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     *
     * @param userProjectiles the list of user projectiles.
     * @param enemyUnits the list of enemy units.
     */
    public void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles,
                                               List<ActiveActorDestructible> enemyUnits) {
        for (ActiveActorDestructible enemy : enemyUnits) {
            for (ActiveActorDestructible projectile : userProjectiles) {
                if (enemy.getBoundsInParent().intersects(projectile.getBoundsInParent())) {
                    enemy.takeDamage();  // Call takeDamage() instead of destroy()
                    projectile.destroy(DestructionType.COLLISION);

                    // If the enemy's health reaches 0, then destroy it
                    if (enemy instanceof FighterPlane && ((FighterPlane) enemy).getHealth() <= 0) {
                        enemy.destroy(DestructionType.PROJECTILE_KILL);
                        soundManager.playDamagedSound("enemy");
                    }
                }
            }
        }
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     *
     * @param enemyProjectiles the list of enemy projectiles.
     * @param friendlyUnits the list of friendly units.
     */
    public void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles,
                                                List<ActiveActorDestructible> friendlyUnits) {
        for (ActiveActorDestructible friendly : friendlyUnits) {
            for (ActiveActorDestructible projectile : enemyProjectiles) {
                if (friendly.getBoundsInParent().intersects(projectile.getBoundsInParent())) {
                    friendly.takeDamage();
                    projectile.destroy(DestructionType.COLLISION);
                }
            }
        }
    }
}