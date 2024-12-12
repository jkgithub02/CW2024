package com.example.demo.controller.gameControllers;

import com.example.demo.JavaFXTest;
import com.example.demo.config.GameConfig;
import com.example.demo.levels.LevelOne;
import com.example.demo.managers.NavigationManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WinScreenControllerTest extends JavaFXTest {

    private WinScreenController controller;

    @BeforeEach
    public void setUp() throws Exception {
        Platform.runLater(() -> {
            try {
                controller = new WinScreenController();
                Field winRootField = WinScreenController.class.getDeclaredField("winRoot");
                winRootField.setAccessible(true);
                StackPane winRoot = new StackPane();
                winRootField.set(controller, winRoot);

                Stage stage = new Stage();
                Scene scene = new Scene(winRoot);
                stage.setScene(scene);
                controller.setStage(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        waitForRunLater();
    }

    @Test
    void testInitialize() throws Exception {
        Platform.runLater(() -> {
            try {
                Field winRootField = WinScreenController.class.getDeclaredField("winRoot");
                winRootField.setAccessible(true);  // Make the private field accessible
                StackPane winRoot = new StackPane();
                winRootField.set(controller, winRoot);

                controller.initialize();
                assertEquals(GameConfig.SCREEN_WIDTH, winRoot.getPrefWidth());
                assertEquals(GameConfig.SCREEN_HEIGHT, winRoot.getPrefHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        waitForRunLater();
    }

    @Test
    void testHandleRestart() throws Exception {
        Platform.runLater(() -> {
            try {
                Method handleRestart = WinScreenController.class.getDeclaredMethod("handleRestart");
                handleRestart.setAccessible(true);

                NavigationManager mockNavigationManager = mock(NavigationManager.class);
                doNothing().when(mockNavigationManager).restartLevel(LevelOne.class);

                handleRestart.invoke(controller);
                verify(mockNavigationManager, times(1)).restartLevel(LevelOne.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        waitForRunLater();
    }

    @Test
    void testHandleMainMenu() throws Exception {
        Platform.runLater(() -> {
            try {
                Method handleMainMenu = WinScreenController.class.getDeclaredMethod("handleMainMenu");
                handleMainMenu.setAccessible(true);

                NavigationManager mockNavigationManager = mock(NavigationManager.class);
                doNothing().when(mockNavigationManager).goToMainMenu();

                handleMainMenu.invoke(controller);
                verify(mockNavigationManager, times(1)).goToMainMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        waitForRunLater();
    }

    private void waitForRunLater() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        latch.await();
    }
}