package com.example.demo.managers;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.DestructionType;
import com.example.demo.actors.planes.FighterPlane;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Manages the entities in the game, including friendly units, enemy units, and projectiles.
 * Implements the Singleton pattern to ensure only one instance exists.
 */
public class EntityManager {
    /**
     * The singleton instance of the EntityManager.
     */
    private static EntityManager instance;

    /**
     * The current root group for the game entities.
     */
    private static Group currentRoot;

    /**
     * The list of friendly units in the game.
     */
    private final List<ActiveActorDestructible> friendlyUnits;

    /**
     * The list of enemy units in the game.
     */
    private final List<ActiveActorDestructible> enemyUnits;

    /**
     * The list of user projectiles in the game.
     */
    private final List<ActiveActorDestructible> userProjectiles;

    /**
     * The list of enemy projectiles in the game.
     */
    private final List<ActiveActorDestructible> enemyProjectiles;

    /**
     * The map of actor hitboxes.
     */
    private final Map<ActiveActorDestructible, Rectangle> actorHitboxes;

    /**
     * The root group for the game entities.
     */
    private Group root;

    /**
     * The list of listeners for when an enemy is destroyed.
     */
    private final List<Consumer<ActiveActorDestructible>> enemyDestroyedListeners;

    /**
     * Private constructor for Singleton pattern.
     *
     * @param root the root group for the game entities.
     */
    private EntityManager(Group root) {
        this.root = root;
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.actorHitboxes = new HashMap<>();
        this.enemyDestroyedListeners = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of EntityManager.
     * Creates a new instance if none exists or if the root group has changed.
     *
     * @param root the root group for the game entities.
     * @return the EntityManager instance.
     */
    public static synchronized EntityManager getInstance(Group root) {
        if (instance == null || currentRoot != root) {
            instance = new EntityManager(root);
            currentRoot = root;
        }
        return instance;
    }

    /**
     * Resets the entity manager state for a new level or game.
     */
    public void reset() {
        friendlyUnits.clear();
        enemyUnits.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
        actorHitboxes.clear();
        enemyDestroyedListeners.clear();
    }

    /**
     * Updates the root group if needed (for example, when switching levels).
     *
     * @param newRoot the new root group.
     */
    public void updateRoot(Group newRoot) {
        this.root = newRoot;
        currentRoot = newRoot;
    }

    /**
     * Adds a friendly unit to the game.
     *
     * @param unit the friendly unit to add.
     */
    public void addFriendlyUnit(ActiveActorDestructible unit) {
        friendlyUnits.add(unit);
    }

    /**
     * Adds an enemy unit to the game.
     *
     * @param enemy the enemy unit to add.
     */
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Adds a listener for when an enemy is destroyed.
     *
     * @param listener the listener to add.
     */
    public void addEnemyDestroyedListener(Consumer<ActiveActorDestructible> listener) {
        enemyDestroyedListeners.add(listener);
    }

    /**
     * Adds an enemy projectile to the game.
     *
     * @param projectile the enemy projectile to add.
     */
    public void addEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            enemyProjectiles.add(projectile);
            root.getChildren().add(projectile);
        }
    }

    /**
     * Updates the state of all actors in the game.
     */
    public void updateActors() {
        updateActorList(friendlyUnits);
        updateActorList(enemyUnits);
        updateActorList(userProjectiles);
        updateActorList(enemyProjectiles);
    }

    /**
     * Updates the state of the actors in the specified list.
     *
     * @param actors the list of actors to update.
     */
    private void updateActorList(List<ActiveActorDestructible> actors) {
        actors.forEach(actor -> {
            actor.updateActor();
        });
    }

    /**
     * Removes destroyed actors from the game.
     */
    public void removeDestroyedActors() {
        removeDestroyedFromList(friendlyUnits);
        removeDestroyedFromList(enemyUnits);
        removeDestroyedFromList(userProjectiles);
        removeDestroyedFromList(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from the specified list.
     *
     * @param actors the list of actors to remove.
     */
    private void removeDestroyedFromList(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .collect(Collectors.toList());

        destroyedActors.forEach(actor -> {
            root.getChildren().remove(actor);
            root.getChildren().remove(actorHitboxes.remove(actor));

            // Only notify if it's an enemy fighter plane destroyed by projectile
            if (enemyUnits.contains(actor) &&
                    actor instanceof FighterPlane &&
                    actor.getDestructionType() == DestructionType.PROJECTILE_KILL) {
                notifyEnemyDestroyed(actor);
            }
        });

        actors.removeAll(destroyedActors);
    }

    /**
     * Notifies listeners that an enemy has been destroyed.
     *
     * @param enemy the destroyed enemy.
     */
    private void notifyEnemyDestroyed(ActiveActorDestructible enemy) {
        enemyDestroyedListeners.forEach(listener -> listener.accept(enemy));
    }

    /**
     * Gets the list of friendly units.
     *
     * @return the list of friendly units.
     */
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    /**
     * Gets the list of enemy units.
     *
     * @return the list of enemy units.
     */
    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Gets the list of user projectiles.
     *
     * @return the list of user projectiles.
     */
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Gets the list of enemy projectiles.
     *
     * @return the list of enemy projectiles.
     */
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Gets the current number of enemies.
     *
     * @return the current number of enemies.
     */
    public int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }
}