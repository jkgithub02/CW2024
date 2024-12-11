package com.example.demo.managers.gameManagers;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BulletManagerTest extends JavaFXTest {
    private BulletManager bulletManager;
    private static final int MAX_BULLETS = 10;
    private static final double RELOAD_TIME = 2.0;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            bulletManager = new BulletManager(MAX_BULLETS, RELOAD_TIME);
        });
        waitForFxEvents();
    }

    @Test
    void testInitialization() {
        Platform.runLater(() -> {
            assertEquals(MAX_BULLETS, bulletManager.getMaxBullets(),
                    "Max bullets should match constructor parameter");
            assertEquals(MAX_BULLETS, bulletManager.getCurrentBullets(),
                    "Initial bullets should equal max bullets");
            assertFalse(bulletManager.isReloading(),
                    "Should not be reloading initially");
            assertTrue(bulletManager.canShoot(),
                    "Should be able to shoot initially");
        });
        waitForFxEvents();
    }

    @Test
    void testShooting() {
        Platform.runLater(() -> {
            bulletManager.shoot();
            assertEquals(MAX_BULLETS - 1, bulletManager.getCurrentBullets(),
                    "Bullets should decrease by 1 after shooting");
            assertTrue(bulletManager.canShoot(),
                    "Should still be able to shoot");
        });
        waitForFxEvents();
    }

    @Test
    void testEmptyMagazine() {
        Platform.runLater(() -> {
            // Empty the magazine
            for (int i = 0; i < MAX_BULLETS; i++) {
                bulletManager.shoot();
            }

            assertEquals(0, bulletManager.getCurrentBullets(),
                    "Should have no bullets left");
            assertTrue(bulletManager.isReloading(),
                    "Should be reloading");
            assertFalse(bulletManager.canShoot(),
                    "Should not be able to shoot while reloading");
        });
        waitForFxEvents();
    }

    @Test
    void testReloadProgress() {
        Platform.runLater(() -> {
            // Empty the magazine to trigger reload
            for (int i = 0; i < MAX_BULLETS; i++) {
                bulletManager.shoot();
            }

            // Check initial reload progress
            assertTrue(bulletManager.getReloadProgress() >= 0.0 &&
                            bulletManager.getReloadProgress() <= 1.0,
                    "Reload progress should be between 0 and 1");
        });
        waitForFxEvents();
    }

    @Test
    void testPauseAndResume() {
        Platform.runLater(() -> {
            // Empty the magazine to trigger reload
            for (int i = 0; i < MAX_BULLETS; i++) {
                bulletManager.shoot();
            }

            // Wait a bit and then pause
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bulletManager.pause();
            double progressAtPause = bulletManager.getReloadProgress();

            // Wait a bit more
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Progress should not have changed while paused
            assertEquals(progressAtPause, bulletManager.getReloadProgress(),
                    "Progress should not change while paused");

            bulletManager.resume();
        });
        waitForFxEvents();
    }

    @Test
    void testCompleteReloadCycle() throws InterruptedException {
        CountDownLatch reloadLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            // Empty the magazine
            for (int i = 0; i < MAX_BULLETS; i++) {
                bulletManager.shoot();
            }

            // Set up a timeline to check when reload is complete
            new Thread(() -> {
                try {
                    // Wait for slightly longer than reload time
                    Thread.sleep((long)(RELOAD_TIME * 1000 + 100));
                    Platform.runLater(() -> {
                        assertFalse(bulletManager.isReloading(),
                                "Should not be reloading after reload time");
                        assertEquals(MAX_BULLETS, bulletManager.getCurrentBullets(),
                                "Should have full bullets after reload");
                        reloadLatch.countDown();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        // Wait for reload to complete
        assertTrue(reloadLatch.await((long) (RELOAD_TIME * 2), TimeUnit.SECONDS),
                "Reload should complete within time");
    }
}