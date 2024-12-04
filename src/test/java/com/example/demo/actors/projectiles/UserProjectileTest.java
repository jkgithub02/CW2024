package com.example.demo.actors.projectiles;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProjectileTest extends JavaFXTest {
    private UserProjectile userProjectile;

    @BeforeEach
    void setUp() {
        userProjectile = new UserProjectile(100, 100);
    }

    @Test
    void testInitialize() {
        assertNotNull(userProjectile);
    }

    @Test
    void testUpdatePosition() {
        double initialX = userProjectile.getTranslateX();
        userProjectile.updatePosition();
        assertNotEquals(initialX, userProjectile.getTranslateX());
    }
}