package com.example.demo.levels;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LevelTwoTest extends JavaFXTest {

    @Test
    void testLevelTwoInitialization() {
        LevelTwo levelTwo = new LevelTwo();
        assertNotNull(levelTwo);
    }
}