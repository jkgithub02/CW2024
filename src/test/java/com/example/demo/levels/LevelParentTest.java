//package com.example.demo.levels;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.example.demo.actors.ActiveActorDestructible;
//import com.example.demo.actors.planes.UserPlane;
//import com.example.demo.config.GameConfig;
//import com.example.demo.config.GameState;
//import com.example.demo.managers.*;
//import com.example.demo.view.LevelView;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.util.Duration;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.HashSet;
//import java.util.Set;
//
//class LevelParentTest {
//
//    private LevelParent levelParent;
//    private UserPlane userPlaneMock;
//    private EntityManager entityManagerMock;
//    private CollisionManager collisionManagerMock;
//    private InputManager inputManagerMock;
//    private PauseManager pauseManagerMock;
//    private GameInitializer gameInitializerMock;
//    private NavigationManager navigationManagerMock;
//    private SoundManager soundManagerMock;
//    private LevelView levelViewMock;
//
//    @BeforeEach
//    void setUp() {
//        userPlaneMock = mock(UserPlane.class);
//        entityManagerMock = mock(EntityManager.class);
//        collisionManagerMock = mock(CollisionManager.class);
//        inputManagerMock = mock(InputManager.class);
//        pauseManagerMock = mock(PauseManager.class);
//        gameInitializerMock = mock(GameInitializer.class);
//        navigationManagerMock = mock(NavigationManager.class);
//        soundManagerMock = mock(SoundManager.class);
//        levelViewMock = mock(LevelView.class);
//
//        levelParent = new LevelParent("background.png", 100) {
//            @Override
//            protected void checkIfGameOver() {
//                // Implement abstract method for testing
//            }
//
//            @Override
//            protected void spawnEnemyUnits() {
//                // Implement abstract method for testing
//            }
//
//            @Override
//            protected LevelView instantiateLevelView() {
//                return levelViewMock;
//            }
//        };
//
//        levelParent.setUserPlane(userPlaneMock);
//        levelParent.setEntityManager(entityManagerMock);
//        levelParent.setCollisionManager(collisionManagerMock);
//        levelParent.setInputManager(inputManagerMock);
//        levelParent.setPauseManager(pauseManagerMock);
//        levelParent.setGameInitializer(gameInitializerMock);
//        levelParent.setNavigationManager(navigationManagerMock);
//        levelParent.setSoundManager(soundManagerMock);
//    }
//
//    @Test
//    void initializeScene_shouldInitializeGame() {
//        Scene scene = levelParent.initializeScene();
//        assertNotNull(scene);
//        verify(gameInitializerMock).initializeGame();
//        verify(inputManagerMock).initializeFireProjectileHandler();
//    }
//
//    @Test
//    void startGame_shouldPlayTimeline() {
//        levelParent.startGame();
//        verify(soundManagerMock).playBackgroundMusic("level");
//    }
//
//    @Test
//    void goToNextLevel_shouldStopTimelineAndMusic() {
//        levelParent.goToNextLevel("NextLevelClass", "NextLevelName");
//        verify(soundManagerMock).stopAllBackgroundMusic();
//    }
//
//    @Test
//    void pauseGame_shouldPauseTimeline() {
//        when(pauseManagerMock.isPaused()).thenReturn(false);
//        levelParent.pauseGame();
//        verify(inputManagerMock).setGameState(GameState.PAUSED);
//    }
//
//    @Test
//    void resumeGame_shouldPlayTimeline() {
//        when(pauseManagerMock.isPaused()).thenReturn(true);
//        levelParent.resumeGame();
//        verify(inputManagerMock).setGameState(GameState.ACTIVE);
//    }
//
//    @Test
//    void winGame_shouldShowWinScreen() {
//        levelParent.winGame();
//        verify(navigationManagerMock).showWinScreen();
//    }
//
//    @Test
//    void loseGame_shouldShowGameOverScreen() {
//        levelParent.loseGame();
//        verify(navigationManagerMock).showGameOverScreen(levelParent.getClass());
//    }
//}