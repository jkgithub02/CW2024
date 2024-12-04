package com.example.demo.actors.planes;

import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest extends JavaFXTest {
    private UserPlane userPlane;

    @BeforeEach
    void setUp() {
        userPlane = new UserPlane(10);
    }

    @Test
    void testInitialize() {
        assertNotNull(userPlane);
        assertEquals(10, userPlane.getHealth());
    }

    @Test
    void testUpdatePosition() {
        double initialY = userPlane.getTranslateY();
        userPlane.moveUp();
        userPlane.updatePosition();
        assertNotEquals(initialY, userPlane.getTranslateY());
    }

    @Test
    void testFireProjectile() {
        ActiveActorDestructible projectile = userPlane.fireProjectile();
        assertNotNull(projectile);
    }

    @Test
    void testMovement() {
        userPlane.moveUp();
        assertEquals(-1, userPlane.getVerticalVelocityMultiplier());
        userPlane.moveDown();
        assertEquals(1, userPlane.getVerticalVelocityMultiplier());
        userPlane.moveLeft();
        assertEquals(-1, userPlane.getHorizontalVelocityMultiplier());
        userPlane.moveRight();
        assertEquals(1, userPlane.getHorizontalVelocityMultiplier());
        userPlane.stopHorizontal();
        assertEquals(0, userPlane.getHorizontalVelocityMultiplier());
        userPlane.stopVertical();
        assertEquals(0, userPlane.getVerticalVelocityMultiplier());
    }

    @Test
    void testKillCount() {
        assertEquals(0, userPlane.getKillCount());
        userPlane.incrementKillCount();
        assertEquals(1, userPlane.getKillCount());
    }
}