package com.example.demo.actors.factory;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Abstract factory class for creating actors in the game.
 * Provides base implementation for actor factories with configurable image heights.
 * 
 * @see ActorFactory
 * @see ActiveActorDestructible
 */
public abstract class AbstractActorFactory implements ActorFactory {
    /**
     * The default height of the actor's image, used for rendering.
     */
    protected final int defaultImageHeight;

    /**
     * Constructs an AbstractActorFactory with the specified default image height.
     *
     * @param defaultImageHeight the default height of the actor's image
     */
    protected AbstractActorFactory(int defaultImageHeight) {
        this.defaultImageHeight = defaultImageHeight;
    }

    /**
     * Creates an actor at the specified coordinates.
     * Implementation is provided by concrete factory classes.
     *
     * @param x the x-coordinate of the actor
     * @param y the y-coordinate of the actor
     * @return the created actor instance
     */
    @Override
    public abstract ActiveActorDestructible createActor(double x, double y);
}