package com.example.demo.levels;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArcadeLevelTest extends JavaFXTest {

    @Test
    void testArcadeLevelInitialization() {
        ArcadeLevel arcadeLevel = new ArcadeLevel();
        assertNotNull(arcadeLevel);
    }
}