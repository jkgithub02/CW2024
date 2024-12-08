package com.example.demo.actors.planes;

import com.example.demo.EnemyPlaneTwo;
import com.example.demo.JavaFXTest;
import com.example.demo.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTwoTest extends JavaFXTest {
    private EnemyPlaneTwo enemyPlaneTwo;

    @BeforeEach
    void setUp() {
        enemyPlaneTwo = new EnemyPlaneTwo(100, 100);
    }

    @Test
    void testInitialize() {
        assertNotNull(enemyPlaneTwo);
        assertEquals(2, enemyPlaneTwo.getHealth());
    }

    @Test
    void testUpdatePosition() {
        double initialX = enemyPlaneTwo.getTranslateX();
        enemyPlaneTwo.updatePosition();
        assertNotEquals(initialX, enemyPlaneTwo.getTranslateX());
    }

    @Test
    void testFireProjectile() {
        ActiveActorDestructible projectile = enemyPlaneTwo.fireProjectile();
        if (projectile != null) {
            assertNotNull(projectile);
        }
    }
}