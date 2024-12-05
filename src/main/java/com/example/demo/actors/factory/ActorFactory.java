package com.example.demo.actors.factory;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Interface for creating actors.
 */
public interface ActorFactory {
    /**
     * Creates an actor at the specified coordinates.
     *
     * @param x the x-coordinate of the actor.
     * @param y the y-coordinate of the actor.
     * @return the created actor.
     */
    ActiveActorDestructible createActor(double x, double y);
}