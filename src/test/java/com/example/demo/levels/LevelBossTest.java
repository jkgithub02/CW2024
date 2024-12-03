package com.example.demo.levels;

import com.example.demo.JavaFXTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LevelBossTest extends JavaFXTest {

    @Test
    void testLevelBossInitialization() {
        LevelBoss levelBoss = new LevelBoss();
        assertNotNull(levelBoss);
    }
}