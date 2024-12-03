package com.example.demo.managers;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundManagerTest extends JavaFXTest {
    private SoundManager soundManager;

    @BeforeEach
    void setUp() {
        soundManager = SoundManager.getInstance();
    }

    @Test
    void testSingletonInstance() {
        SoundManager anotherInstance = SoundManager.getInstance();
        assertSame(soundManager, anotherInstance);
    }

    @Test
    void testSetBackgroundVolume() {
        double testVolume = 0.5;
        soundManager.setBackgroundVolume(testVolume);
        assertEquals(testVolume, soundManager.getBackgroundVolume());
    }

    @Test
    void testSetEffectsVolume() {
        double testVolume = 0.7;
        soundManager.setEffectsVolume(testVolume);
        assertEquals(testVolume, soundManager.getEffectsVolume());
    }

    @Test
    void testPlayAndStopBackgroundMusic() {
        soundManager.playBackgroundMusic("menu");
        soundManager.stopAllBackgroundMusic();
        // Since we can't directly test audio playback, we mainly test that no exceptions occur
        assertTrue(true);
    }
}