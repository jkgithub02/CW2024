package com.example.demo.factory;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Abstract factory class for creating actors.
 */
public abstract class AbstractActorFactory implements ActorFactory {
    /**
     * The default height of the actor's image.
     */
    protected final int defaultImageHeight;

    /**
     * Constructs an AbstractActorFactory with the specified default image height.
     *
     * @param defaultImageHeight the default height of the actor's image.
     */
    protected AbstractActorFactory(int defaultImageHeight) {
        this.defaultImageHeight = defaultImageHeight;
    }

    /**
     * Creates an actor at the specified coordinates.
     *
     * @param x the x-coordinate of the actor.
     * @param y the y-coordinate of the actor.
     * @return the created actor.
     */
    @Override
    public abstract ActiveActorDestructible createActor(double x, double y);
}