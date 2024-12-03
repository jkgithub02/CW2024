package com.example.demo.levels;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LevelOneTest extends JavaFXTest {

    @Test
    void testLevelOneInitialization() {
        LevelOne levelOne = new LevelOne();
        assertNotNull(levelOne);
    }
}