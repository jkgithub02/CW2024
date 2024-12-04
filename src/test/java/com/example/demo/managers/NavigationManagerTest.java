package com.example.demo.managers;

import com.example.demo.JavaFXTest;
import com.example.demo.controller.MainMenuController;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.TestLevel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

class NavigationManagerTest extends JavaFXTest {
    private NavigationManager navigationManager;
    private Stage stage;
    private Scene scene;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(new Pane(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            navigationManager = new NavigationManager(scene);
        });
    }

    @Test
    void testGoToMainMenu() {
        Platform.runLater(() -> {
            navigationManager.goToMainMenu();
            Scene currentScene = stage.getScene();
            assertNotNull(currentScene);
            assertTrue(currentScene.getRoot().getChildrenUnmodifiable().size() > 0);
        });
    }

    @Test
    void testShowWinScreen() {
        Platform.runLater(() -> {
            navigationManager.showWinScreen();
            Scene currentScene = stage.getScene();
            assertNotNull(currentScene);
            assertTrue(currentScene.getRoot().getChildrenUnmodifiable().size() > 0);
        });
    }

    @Test
    void testShowGameOverScreen() {
        Platform.runLater(() -> {
            navigationManager.showGameOverScreen(TestLevel.class);
            Scene currentScene = stage.getScene();
            assertNotNull(currentScene);
            assertTrue(currentScene.getRoot().getChildrenUnmodifiable().size() > 0);
        });
    }
}