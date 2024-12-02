package com.example.demo.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {

    @Test
    void testScreenDimensions() {
        assertEquals(1300, GameConfig.SCREEN_WIDTH);
        assertEquals(750, GameConfig.SCREEN_HEIGHT);
    }

    @Test
    void testGameTitle() {
        assertEquals("Sky Battle", GameConfig.TITLE);
    }

    @Test
    void testPlayerInitialHealth() {
        assertEquals(5, GameConfig.PLAYER_INITIAL_HEALTH);
    }
}