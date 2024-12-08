package com.example.demo.actors.planes;

import com.example.demo.FighterPlane;
import com.example.demo.ImageTestConfig;
import com.example.demo.JavaFXTest;
import com.example.demo.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FighterPlaneTest extends JavaFXTest {
    private FighterPlane fighterPlane;

    @BeforeEach
    void setUp() {
        fighterPlane = new FighterPlane(ImageTestConfig.IMAGENAME, 80, 100, 100, 10) {
            @Override
            public ActiveActorDestructible fireProjectile() {
                return null; // Mock implementation
            }
            @Override
            public void updatePosition() {
                // Mock implementation
            }
            @Override
            public void takeDamage() {
                super.takeDamage();
            }
            @Override
            public void updateActor() {
                // Mock implementation
            }
        };
    }

    @Test
    void testInitialize() {
        assertNotNull(fighterPlane);
        assertEquals(10, fighterPlane.getHealth());
    }

    @Test
    void testTakeDamage() {
        fighterPlane.takeDamage();
        assertEquals(9, fighterPlane.getHealth());
    }
}