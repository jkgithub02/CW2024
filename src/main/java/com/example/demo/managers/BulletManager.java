package com.example.demo.managers;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * Manages the bullets for the player's plane, including reloading.
 *
 * @see javafx.animation.Timeline
 * @see javafx.animation.KeyFrame
 * @see javafx.util.Duration
 * @see com.example.demo.managers.SoundManager
 */
public class BulletManager {
    /**
     * The maximum number of bullets.
     */
    private final int maxBullets;

    /**
     * The reload time in seconds.
     */
    private final double reloadTime;

    /**
     * The current number of bullets.
     */
    private int currentBullets;

    /**
     * Indicates whether reloading is in progress.
     */
    private boolean isReloading;

    /**
     * The timeline for the reload timer.
     */
    private Timeline reloadTimer;

    /**
     * The paused time duration for the reload timer.
     */
    private Duration pausedTime;

    /**
     * The sound manager for playing sounds.
     */
    private final SoundManager soundManager;

    /**
     * Constructs a BulletManager with the specified maximum bullets and reload time.
     *
     * @param maxBullets the maximum number of bullets.
     * @param reloadTime the reload time in seconds.
     */
    public BulletManager(int maxBullets, double reloadTime) {
        this.maxBullets = maxBullets;
        this.reloadTime = reloadTime;
        this.currentBullets = maxBullets;
        this.isReloading = false;
        this.soundManager = SoundManager.getInstance();
        setupReloadTimer();
    }

    /**
     * Sets up the reload timer.
     */
    private void setupReloadTimer() {
        reloadTimer = new Timeline(
                new KeyFrame(Duration.seconds(reloadTime), event -> {
                    currentBullets = maxBullets;
                    isReloading = false;
                    soundManager.playShootSound("reload");
                })
        );
        reloadTimer.setCycleCount(1);
    }

    /**
     * Checks if the player can shoot.
     *
     * @return true if the player can shoot, false otherwise.
     */
    public boolean canShoot() {
        return currentBullets > 0 && !isReloading;
    }

    /**
     * Shoots a bullet, reducing the current bullet count.
     */
    public void shoot() {
        if (canShoot()) {
            currentBullets--;
            if (currentBullets == 0) {
                isReloading = true;
                reloadTimer.playFromStart();
            }
        }
    }

    /**
     * Gets the current number of bullets.
     *
     * @return the current number of bullets.
     */
    public int getCurrentBullets() {
        return currentBullets;
    }

    /**
     * Gets the maximum number of bullets.
     *
     * @return the maximum number of bullets.
     */
    public int getMaxBullets() {
        return maxBullets;
    }

    /**
     * Checks if reloading is in progress.
     *
     * @return true if reloading is in progress, false otherwise.
     */
    public boolean isReloading() {
        return isReloading;
    }

    /**
     * Gets the reload progress as a percentage.
     *
     * @return the reload progress.
     */
    public double getReloadProgress() {
        if (!isReloading) return 1.0;
        return reloadTimer.getCurrentTime().toSeconds() / reloadTime;
    }

    /**
     * Pauses the reload timer.
     */
    public void pause() {
        if (isReloading && reloadTimer != null) {
            pausedTime = reloadTimer.getCurrentTime();
            reloadTimer.pause();
        }
    }

    /**
     * Resumes the reload timer.
     */
    public void resume() {
        if (isReloading && reloadTimer != null && pausedTime != null) {
            reloadTimer.playFrom(pausedTime);
        }
    }
}