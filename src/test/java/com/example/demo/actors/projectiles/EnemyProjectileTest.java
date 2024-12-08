package com.example.demo.actors.projectiles;

import com.example.demo.EnemyProjectile;
import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyProjectileTest extends JavaFXTest {
    private EnemyProjectile enemyProjectile;

    @BeforeEach
    void setUp() {
        enemyProjectile = new EnemyProjectile(100, 100);
    }

    @Test
    void testInitialize() {
        assertNotNull(enemyProjectile);
    }

    @Test
    void testUpdatePosition() {
        double initialX = enemyProjectile.getTranslateX();
        enemyProjectile.updatePosition();
        assertNotEquals(initialX, enemyProjectile.getTranslateX());
    }

    @Test
    void testTakeDamage() {
        enemyProjectile.takeDamage();
        assertTrue(enemyProjectile.isDestroyed());
    }
}