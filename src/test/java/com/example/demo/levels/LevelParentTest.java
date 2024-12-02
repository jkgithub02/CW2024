package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.config.GameConfig;
import com.example.demo.managers.*;
import com.example.demo.view.LevelView;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelParentTest {
    private static class TestLevelParent extends LevelParent {
        public TestLevelParent(String backgroundImageName, int playerInitialHealth) {
            super(backgroundImageName, playerInitialHealth);
        }

        @Override
        protected void checkIfGameOver() {
            // Implement abstract method
        }

        @Override
        protected void spawnEnemyUnits() {
            // Implement abstract method
        }

        @Override
        protected LevelView instantiateLevelView() {
            return mock(LevelView.class);
        }
    }

    @Mock
    private UserPlane mockUserPlane;
    @Mock
    private EntityManager mockEntityManager;
    @Mock
    private CollisionManager mockCollisionManager;
    @Mock
    private InputManager mockInputManager;
    @Mock
    private PauseManager mockPauseManager;
    @Mock
    private GameInitializer mockGameInitializer;
    @Mock
    private NavigationManager mockNavigationManager;
    @Mock
    private SoundManager mockSoundManager;

    private TestLevelParent testLevelParent;

    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Initializes the JavaFX environment
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testLevelParent = new TestLevelParent("/path/to/background.png", 100);

        testLevelParent = spy(testLevelParent);
        doReturn(mockUserPlane).when(testLevelParent).getUser();
        doReturn(mockEntityManager).when(testLevelParent).getEntityManager();
        doReturn(mockCollisionManager).when(testLevelParent).getCollisionManager();
        doReturn(mockInputManager).when(testLevelParent).getInputManager();
        doReturn(mockPauseManager).when(testLevelParent).getPauseManager();
        doReturn(mockGameInitializer).when(testLevelParent).getGameInitializer();
        doReturn(mockNavigationManager).when(testLevelParent).getNavigationManager();
        doReturn(mockSoundManager).when(testLevelParent).getSoundManager();
    }

    @Test
    public void testInitializeScene() {
        Scene scene = testLevelParent.initializeScene();
        assertNotNull(scene);
    }

    @Test
    public void testStartGame() {
        testLevelParent.startGame();
        verify(mockSoundManager).playBackgroundMusic("level");
    }

    @Test
    public void testPauseAndResumeGame() {
        testLevelParent.pauseGame();
        verify(mockPauseManager).isPaused();
        verify(mockInputManager).setGameState(GameConfig.GameState.PAUSED);

        testLevelParent.resumeGame();
        verify(mockInputManager).setGameState(GameConfig.GameState.ACTIVE);
    }

    @Test
    public void testGoToNextLevel() {
        testLevelParent.goToNextLevel("NextLevelClassName", "NextLevelName");
        verify(mockSoundManager).stopAllBackgroundMusic();
    }

    @Test
    public void testWinGame() {
        testLevelParent.winGame();
        verify(mockInputManager).setGameState(GameConfig.GameState.WIN);
        verify(mockNavigationManager).showWinScreen();
    }

    @Test
    public void testLoseGame() {
        testLevelParent.loseGame();
        verify(mockInputManager).setGameState(GameConfig.GameState.LOSE);
        verify(mockNavigationManager).showGameOverScreen(TestLevelParent.class);
    }
}