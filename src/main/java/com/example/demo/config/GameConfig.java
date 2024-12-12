package com.example.demo.config;

/**
 * Configuration class that holds constant values used throughout the application.
 */
public final class GameConfig {


    /**
     * Private constructor to prevent instantiation.
     */
    private GameConfig() {}

    /**
     * The width of the game screen.
     */
    public static final double SCREEN_WIDTH = 1300;

    /**
     * The height of the game screen.
     */
    public static final double SCREEN_HEIGHT = 750;

    /**
     * The title of the game.
     */
    public static final String TITLE = "Sky Battle";

    /**
     * The initial health of the player.
     */
    public static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The maximum number of bullets the player can have.
     */
    public static final int PLAYER_MAX_BULLETS = 10;
}