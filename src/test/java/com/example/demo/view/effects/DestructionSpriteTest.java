package com.example.demo.view.effects;

import com.example.demo.JavaFXTest;
import javafx.animation.Timeline;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DestructionSpriteTest extends JavaFXTest {

    private Group root;
    private DestructionSprite destructionSprite;

    @BeforeEach
    void setUp() {
        root = new Group();
        destructionSprite = new DestructionSprite(100, 100, root);
    }

    @Test
    void testDestructionSpriteInitialization() {
        assertNotNull(destructionSprite);
        assertEquals(1, root.getChildren().size());
    }

    @Test
    void testPlayEffect() {
        destructionSprite.playEffect();
        Timeline timeline = (Timeline) getPrivateField(destructionSprite, "timeline");
        assertEquals(1, timeline.getCycleCount());
    }

    private Object getPrivateField(Object target, String fieldName) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}