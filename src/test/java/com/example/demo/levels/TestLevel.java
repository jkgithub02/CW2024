package com.example.demo.levels;

import com.example.demo.LevelParent;
import com.example.demo.LevelView;
import javafx.scene.Group;

public class TestLevel extends LevelParent {

    public TestLevel(String backgroundImageName, int playerInitialHealth) {
        super(backgroundImageName, playerInitialHealth);
    }

    @Override
    protected void checkIfGameOver() {
        // Implementation for testing
    }

    @Override
    protected void spawnEnemyUnits() {
        // Implementation for testing
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(new Group(), 3, 5, 10);
    }
}