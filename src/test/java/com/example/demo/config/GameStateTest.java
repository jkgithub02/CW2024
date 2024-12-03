package com.example.demo.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void testEnumValues() {
        GameState[] states = GameState.values();
        assertEquals(4, states.length);

        assertTrue(contains(states, GameState.ACTIVE));
        assertTrue(contains(states, GameState.PAUSED));
        assertTrue(contains(states, GameState.WIN));
        assertTrue(contains(states, GameState.LOSE));
    }

    @Test
    void testEnumValueOf() {
        assertEquals(GameState.ACTIVE, GameState.valueOf("ACTIVE"));
        assertEquals(GameState.PAUSED, GameState.valueOf("PAUSED"));
        assertEquals(GameState.WIN, GameState.valueOf("WIN"));
        assertEquals(GameState.LOSE, GameState.valueOf("LOSE"));
    }

    private boolean contains(GameState[] states, GameState state) {
        for (GameState s : states) {
            if (s == state) return true;
        }
        return false;
    }
}