package com.example.demo.managers;

import com.example.demo.JavaFXTest;
import com.example.demo.levels.TestLevel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class NavigationManagerTest extends JavaFXTest {
    private NavigationManager navigationManager;
    private Stage stage;
    private Scene scene;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                scene = new Scene(new Pane(), 800, 600);
                navigationManager = new NavigationManager(scene);
                stage.setScene(scene);
                stage.show();
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Setup timed out");
        waitForFxEvents(); // Using the existing waitForFxEvents from JavaFXTest
    }

    @Test
    void testGoToMainMenu() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                navigationManager.goToMainMenu();
                Scene currentScene = stage.getScene();
                assertNotNull(currentScene);
                assertTrue(currentScene.getRoot().getChildrenUnmodifiable().size() > 0);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");
        waitForFxEvents();
    }

    @Test
    void testShowWinScreen() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                navigationManager.showWinScreen();
                Scene currentScene = stage.getScene();
                assertNotNull(currentScene);
                assertTrue(currentScene.getRoot().getChildrenUnmodifiable().size() > 0);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");
        waitForFxEvents();
    }

    @Test
    void testShowGameOverScreen() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                navigationManager.showGameOverScreen(TestLevel.class);
                Scene currentScene = stage.getScene();
                assertNotNull(currentScene);
                assertTrue(currentScene.getRoot().getChildrenUnmodifiable().size() > 0);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");
        waitForFxEvents();
    }
}