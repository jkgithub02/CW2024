package com.example.demo.actors.planes;

import com.example.demo.EnemyPlane;
import com.example.demo.JavaFXTest;
import com.example.demo.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest extends JavaFXTest {
    private EnemyPlane enemyPlane;

    @BeforeEach
    void setUp() {
        enemyPlane = new EnemyPlane(100, 100);
    }

    @Test
    void testInitialize() {
        assertNotNull(enemyPlane);
        assertEquals(1, enemyPlane.getHealth());
    }

    @Test
    void testUpdatePosition() {
        double initialX = enemyPlane.getTranslateX();
        enemyPlane.updatePosition();
        assertNotEquals(initialX, enemyPlane.getTranslateX());
    }

    @Test
    void testFireProjectile() {
        ActiveActorDestructible projectile = enemyPlane.fireProjectile();
        if (projectile != null) {
            assertNotNull(projectile);
        }
    }
}