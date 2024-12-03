package com.example.demo.actors.planes;

import com.example.demo.JavaFXTest;
import com.example.demo.actors.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class BossTest extends JavaFXTest {
    private Boss boss;

    @BeforeEach
    void setUp() {
        boss = new Boss();
    }

    @Test
    void testInitialize() {
        assertNotNull(boss);
        assertEquals(50, boss.getHealth());
        assertFalse(boss.isShielded());
    }

    @Test
    void testUpdatePosition() {
        double initialY = boss.getTranslateY();
        for (int i = 0; i < 20; i++) {
            boss.updatePosition();
            if (boss.getTranslateY() != initialY) {
                break;
            }
        }
        assertNotEquals(initialY, boss.getTranslateY());
    }

    @Test
    void testFireProjectile() {
        ActiveActorDestructible projectile = boss.fireProjectile();
        if (projectile != null) {
            assertNotNull(projectile);
        }
    }

    @Test
    void testTakeDamage() {
        int initialHealth = boss.getHealth();
        boss.takeDamage();
        assertEquals(initialHealth - 1, boss.getHealth());
    }

    @Test
    void testShieldActivation() {
        try {
            Method activateShieldMethod = Boss.class.getDeclaredMethod("activateShield");
            activateShieldMethod.setAccessible(true);
            Method deactivateShieldMethod = Boss.class.getDeclaredMethod("deactivateShield");
            deactivateShieldMethod.setAccessible(true);

            // Activate shield and test
            activateShieldMethod.invoke(boss);
            assertTrue(boss.isShielded());

            // Deactivate shield and test
            deactivateShieldMethod.invoke(boss);
            assertFalse(boss.isShielded());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}