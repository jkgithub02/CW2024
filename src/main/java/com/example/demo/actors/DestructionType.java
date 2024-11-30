package com.example.demo.actors;

/**
 * Enum representing the different types of destruction that can occur in the game.
 */
public enum DestructionType {
    /**
     * Destruction type when an enemy collides with the player plane.
     */
    COLLISION,

    /**
     * Destruction type when an enemy is destroyed by a player projectile.
     */
    PROJECTILE_KILL,

    /**
     * Destruction type when an enemy passes the defense line.
     */
    PENETRATED_DEFENSE
}