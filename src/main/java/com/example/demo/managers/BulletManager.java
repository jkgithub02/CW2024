package com.example.demo.managers;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class BulletManager {
    private final int maxBullets;
    private final double reloadTime;
    private int currentBullets;
    private boolean isReloading;
    private Timeline reloadTimer;
    private Duration pausedTime;
    private final SoundManager soundManager;

    public BulletManager(int maxBullets, double reloadTime) {
        this.maxBullets = maxBullets;
        this.reloadTime = reloadTime;
        this.currentBullets = maxBullets;
        this.isReloading = false;
        this.soundManager = SoundManager.getInstance();
        setupReloadTimer();
    }

    private void setupReloadTimer() {
        reloadTimer = new Timeline(
                new KeyFrame(Duration.seconds(reloadTime), event -> {
                    currentBullets = maxBullets;
                    isReloading = false;
                    soundManager.playShootSound("reload"); // Add reload sound to SoundManager
                })
        );
        reloadTimer.setCycleCount(1);
    }

    public boolean canShoot() {
        return currentBullets > 0 && !isReloading;
    }

    public void shoot() {
        if (canShoot()) {
            currentBullets--;
            if (currentBullets == 0) {
                isReloading = true;
                reloadTimer.playFromStart();
            }
        }
    }

    public int getCurrentBullets() {
        return currentBullets;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public double getReloadProgress() {
        if (!isReloading) return 1.0;
        return reloadTimer.getCurrentTime().toSeconds() / reloadTime;
    }

    public void pause() {
        if (isReloading && reloadTimer != null) {
            pausedTime = reloadTimer.getCurrentTime();
            reloadTimer.pause();
        }
    }

    public void resume() {
        if (isReloading && reloadTimer != null && pausedTime != null) {
            reloadTimer.playFrom(pausedTime);
        }
    }
}