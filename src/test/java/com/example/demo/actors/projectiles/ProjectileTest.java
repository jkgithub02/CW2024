package com.example.demo.actors.projectiles;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest extends JavaFXTest {
    private Projectile projectile;

    @BeforeEach
    void setUp() {
        projectile = new UserProjectile(100, 100); // Using UserProjectile as a concrete class
    }

    @Test
    void testInitialize() {
        assertNotNull(projectile);
    }

    @Test
    void testTakeDamage() {
        projectile.takeDamage();
        assertTrue(projectile.isDestroyed());
    }
}