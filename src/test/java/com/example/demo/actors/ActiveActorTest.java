package com.example.demo.actors;

import com.example.demo.ImageTestConfig;
import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveActorTest extends JavaFXTest {
    private ActiveActor activeActor;

    @BeforeEach
    void setUp() {
        activeActor = new ActiveActor(ImageTestConfig.IMAGENAME, 80, 100, 100) {
            @Override
            public void updatePosition() {
                moveHorizontally(10);
            }
        };
    }

    @Test
    void testInitialize() {
        assertNotNull(activeActor);
    }

    @Test
    void testUpdatePosition() {
        double initialX = activeActor.getTranslateX();
        activeActor.updatePosition();
        assertNotEquals(initialX, activeActor.getTranslateX());
    }
}