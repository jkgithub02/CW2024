package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.ImageTestConfig;
import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveActorDestructibleTest extends JavaFXTest {
    private ActiveActorDestructible activeActorDestructible;

    @BeforeEach
    void setUp() {
        activeActorDestructible = new ActiveActorDestructible(ImageTestConfig.IMAGENAME, 80, 100, 100) {
            @Override
            public void updatePosition() {
                moveHorizontally(10);
            }

            @Override
            public void updateActor() {
            }

            @Override
            public void takeDamage() {
            }
        };
    }

    @Test
    void testInitialize() {
        assertNotNull(activeActorDestructible);
    }

    @Test
    void testDestroy() {
        activeActorDestructible.destroy();
        assertTrue(activeActorDestructible.isDestroyed());
    }
}