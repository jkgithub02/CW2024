package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.config.GameState;
import com.example.demo.managers.EntityManager;
import com.example.demo.view.LevelView;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LevelParentTest {

    private TestLevel testLevel;
    private TestLevelView testLevelView;

    @Mock
    private UserPlane mockUserPlane;

    @Mock
    private ActiveActorDestructible mockEnemy;

    // Test implementation of LevelView
    private static class TestLevelView extends LevelView {
        private int heartsRemaining = 3;
        private int killCount = 0;

        public TestLevelView(Group root, int hearts, int maxKills) {
            super(root, hearts, maxKills);
        }

        @Override
        public void removeHearts(int hearts) {
            this.heartsRemaining = hearts;
        }

        @Override
        public void updateKillCount(int kills) {
            this.killCount = kills;
        }

        public int getHeartsRemaining() {
            return heartsRemaining;
        }

        public int getKillCount() {
            return killCount;
        }
    }

    // Concrete implementation for testing
    private class TestLevel extends LevelParent {
        public TestLevel() {
            super("/backgrounds/test_background.png", 3);
        }

        @Override
        protected void checkIfGameOver() {
            // Test implementation
        }

        @Override
        protected void spawnEnemyUnits() {
            // Test implementation
        }

        @Override
        protected LevelView instantiateLevelView() {
            testLevelView = new TestLevelView(getRoot(), 3, 10);
            return testLevelView;
        }

        // Expose protected methods for testing
        public void testUpdateLevelView() {
            updateLevelView();
        }

        public void testAddEnemyUnit(ActiveActorDestructible enemy) {
            addEnemyUnit(enemy);
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testLevel = new TestLevel();
    }

    @Test
    void updateLevelView_ShouldUpdateHealthAndKillCount() {
        // Arrange
        when(mockUserPlane.getHealth()).thenReturn(2);
        when(mockUserPlane.getKillCount()).thenReturn(5);

        // Act
        testLevel.testUpdateLevelView();

        // Assert
        assertEquals(2, testLevelView.getHeartsRemaining());
        assertEquals(5, testLevelView.getKillCount());
    }

    @Test
    void addEnemyUnit_ShouldIncreaseEnemyCount() {
        // Arrange
        int initialEnemies = testLevel.getCurrentNumberOfEnemies();

        // Act
        testLevel.testAddEnemyUnit(mockEnemy);

        // Assert
        assertEquals(initialEnemies + 1, testLevel.getCurrentNumberOfEnemies());
    }

    @Test
    void initializeScene_ShouldCreateValidScene() {
        // Act
        Scene scene = testLevel.initializeScene();

        // Assert
        assertNotNull(scene);
        assertNotNull(scene.getRoot());
        assertTrue(scene.getRoot() instanceof Group);
    }

    @Test
    void nextLevelProperty_ShouldBeEmptyInitially() {
        // Assert
        assertTrue(testLevel.nextLevelProperty().get() == null ||
                testLevel.nextLevelProperty().get().isEmpty());
    }

    @Test
    void getEnemyMaximumYPosition_ShouldReturnValidValue() {
        // Act
        double maxYPosition = testLevel.getEnemyMaximumYPosition();

        // Assert
        assertTrue(maxYPosition > 0);
        assertTrue(maxYPosition < testLevel.getScreenWidth());
    }
}