package com.example.demo.actors.projectiles;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossProjectileTest extends JavaFXTest{
    private BossProjectile bossProjectile;

    @BeforeEach
    void setUp() {
        bossProjectile = new BossProjectile(100);
    }

    @Test
    void testInitialize() {
        assertNotNull(bossProjectile);
    }

    @Test
    void testUpdatePosition() {
        double initialX = bossProjectile.getTranslateX();
        bossProjectile.updatePosition();
        assertNotEquals(initialX, bossProjectile.getTranslateX());
    }

    @Test
    void testTakeDamage() {
        bossProjectile.takeDamage();
        assertTrue(bossProjectile.isDestroyed());
    }
}