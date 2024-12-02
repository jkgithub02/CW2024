package com.example.demo.actors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActiveActorDestructibleTest {

    private static class TestActiveActorDestructible extends ActiveActorDestructible {

        public TestActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            // Implement abstract method
        }

        @Override
        public void updateActor() {
            // Implement abstract method
        }

        @Override
        public void takeDamage() {
            // Implement abstract method
        }
    }

    private TestActiveActorDestructible actor;

    @BeforeEach
    public void setUp() {
        actor = new TestActiveActorDestructible("testImage.png", 100, 0, 0);
    }

    @Test
    void initialDestroyedStateShouldBeFalse() {
        assertFalse(actor.isDestroyed());
    }

    @Test
    void destroyShouldSetDestroyedState() {
        actor.destroy();
        assertTrue(actor.isDestroyed());
    }

    @Test
    void destroyWithTypeShouldSetDestructionType() {
        DestructionType destructionType = DestructionType.PROJECTILE_KILL;
        actor.destroy(destructionType);
        assertTrue(actor.isDestroyed());
        assertEquals(destructionType, actor.getDestructionType());
    }

    @Test
    void setDestroyedShouldUpdateDestroyedState() {
        actor.setDestroyed(true);
        assertTrue(actor.isDestroyed());
        actor.setDestroyed(false);
        assertFalse(actor.isDestroyed());
    }

    @Test
    void getDestructionTypeShouldReturnNullInitially() {
        assertNull(actor.getDestructionType());
    }
}