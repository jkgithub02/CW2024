package com.example.demo.managers;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class KeyBindingsManagerTest {

    private KeyBindingsManager keyBindingsManager;

    @BeforeEach
    void setUp() {
        keyBindingsManager = KeyBindingsManager.getInstance();
        keyBindingsManager.resetToDefaults();
    }

    @Test
    void testSingletonInstance() {
        KeyBindingsManager instance1 = KeyBindingsManager.getInstance();
        KeyBindingsManager instance2 = KeyBindingsManager.getInstance();
        assertSame(instance1, instance2, "Instances should be the same");
    }

    @Test
    void testEnsureDefaultBindings() {
        keyBindingsManager.ensureDefaultBindings();
        assertEquals(KeyCode.UP, keyBindingsManager.getBinding("UP"));
        assertEquals(KeyCode.DOWN, keyBindingsManager.getBinding("DOWN"));
        assertEquals(KeyCode.LEFT, keyBindingsManager.getBinding("LEFT"));
        assertEquals(KeyCode.RIGHT, keyBindingsManager.getBinding("RIGHT"));
        assertEquals(KeyCode.SPACE, keyBindingsManager.getBinding("FIRE"));
    }

    @Test
    void testGetBinding() {
        keyBindingsManager.setBinding("UP", KeyCode.W);
        assertEquals(KeyCode.W, keyBindingsManager.getBinding("UP"));
    }

    @Test
    void testSetBinding() {
        keyBindingsManager.setBinding("FIRE", KeyCode.UNDEFINED);
        assertTrue(keyBindingsManager.setBinding("JUMP", KeyCode.SPACE));
        assertEquals(KeyCode.SPACE, keyBindingsManager.getBinding("JUMP"));
    }

    @Test
    void testSetBinding_DuplicateKey() {
        keyBindingsManager.setBinding("UP", KeyCode.W);
        assertFalse(keyBindingsManager.setBinding("JUMP", KeyCode.W));
    }

    @Test
    void testResetToDefaults() {
        keyBindingsManager.setBinding("UP", KeyCode.W);
        keyBindingsManager.resetToDefaults();
        assertEquals(KeyCode.UP, keyBindingsManager.getBinding("UP"));
        assertEquals(KeyCode.DOWN, keyBindingsManager.getBinding("DOWN"));
        assertEquals(KeyCode.LEFT, keyBindingsManager.getBinding("LEFT"));
        assertEquals(KeyCode.RIGHT, keyBindingsManager.getBinding("RIGHT"));
        assertEquals(KeyCode.SPACE, keyBindingsManager.getBinding("FIRE"));
    }

    @Test
    void testIsKeyBound() {
        keyBindingsManager.setBinding("UP", KeyCode.W);
        assertTrue(keyBindingsManager.isKeyBound(KeyCode.W));
    }

    @Test
    void testIsKeyNotBound() {
        assertFalse(keyBindingsManager.isKeyBound(KeyCode.P));
    }
}