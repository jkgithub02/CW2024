package com.example.demo.actors.factory;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Interface defining the contract for actor creation in the game.
 * Implements the Factory pattern for creating game actors.
 * 
 * @see ActiveActorDestructible
 */
public interface ActorFactory {
    /**
     * Creates an actor at the specified coordinates.
     * 
     * @param x the x-coordinate of the actor
     * @param y the y-coordinate of the actor
     * @return the created actor instance
     */
    ActiveActorDestructible createActor(double x, double y);
}