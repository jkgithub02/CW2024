package com.example.demo.controller.gameControllers;

import com.example.demo.JavaFXTest;
import com.example.demo.config.GameConfig;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PauseMenuControllerTest extends JavaFXTest {

    private PauseMenuController controller;

    @BeforeEach
    public void setUp() {
        controller = new PauseMenuController();
    }

    @Test
    void testInitialize() throws Exception {
        // Access private fields using reflection
        Field pauseRootField = PauseMenuController.class.getDeclaredField("pauseRoot");
        pauseRootField.setAccessible(true);
        StackPane pauseRoot = new StackPane();
        pauseRootField.set(controller, pauseRoot);

        Field overlayField = PauseMenuController.class.getDeclaredField("overlay");
        overlayField.setAccessible(true);
        Rectangle overlay = new Rectangle();
        overlayField.set(controller, overlay);

        controller.initialize();

        assertEquals(GameConfig.SCREEN_WIDTH, pauseRoot.getPrefWidth());
        assertEquals(GameConfig.SCREEN_HEIGHT, pauseRoot.getPrefHeight());
    }

    @Test
    void testHandleResume() throws Exception {
        Runnable resumeAction = mock(Runnable.class);
        controller.setActions(resumeAction, null, null);

        // Access and invoke private method using reflection
        var handleResume = PauseMenuController.class.getDeclaredMethod("handleResume");
        handleResume.setAccessible(true);
        handleResume.invoke(controller);

        verify(resumeAction).run();
    }

    @Test
    void testHandleRestart() throws Exception {
        Runnable restartAction = mock(Runnable.class);
        controller.setActions(null, restartAction, null);

        // Access and invoke private method using reflection
        var handleRestart = PauseMenuController.class.getDeclaredMethod("handleRestart");
        handleRestart.setAccessible(true);
        handleRestart.invoke(controller);

        verify(restartAction).run();
    }

    @Test
    void testHandleMainMenu() throws Exception {
        Runnable mainMenuAction = mock(Runnable.class);
        controller.setActions(null, null, mainMenuAction);

        // Access and invoke private method using reflection
        var handleMainMenu = PauseMenuController.class.getDeclaredMethod("handleMainMenu");
        handleMainMenu.setAccessible(true);
        handleMainMenu.invoke(controller);

        verify(mainMenuAction).run();
    }

    @Test
    void testPauseRootNotNull() throws Exception {
        // Access private fields using reflection
        Field pauseRootField = PauseMenuController.class.getDeclaredField("pauseRoot");
        pauseRootField.setAccessible(true);
        StackPane pauseRoot = new StackPane();
        pauseRootField.set(controller, pauseRoot);

        assertNotNull(pauseRootField.get(controller));
    }

    @Test
    void testOverlayNotNull() throws Exception {
        // Access private fields using reflection
        Field overlayField = PauseMenuController.class.getDeclaredField("overlay");
        overlayField.setAccessible(true);
        Rectangle overlay = new Rectangle();
        overlayField.set(controller, overlay);

        assertNotNull(overlayField.get(controller));
    }
}