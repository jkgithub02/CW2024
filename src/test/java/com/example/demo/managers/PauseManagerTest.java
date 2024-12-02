package com.example.demo.managers;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PauseManagerTest {

    private PauseManager pauseManager;
    private boolean pauseActionCalled;
    private boolean resumeActionCalled;
    private Group root;
    private Scene scene;

    @BeforeAll
    static void initJFX() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            root = new Group();
            scene = new Scene(root);

            // Reset test flags
            pauseActionCalled = false;
            resumeActionCalled = false;

            // Create PauseManager
            pauseManager = new PauseManager(
                    scene,
                    root,
                    () -> pauseActionCalled = true,    // pause action
                    () -> resumeActionCalled = true,    // resume action
                    () -> {},                          // main menu action
                    () -> {}                           // restart action
            );

            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void testInitialState() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertFalse(pauseManager.isPaused());
            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void testEscapeKeyPress() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Create escape key event
            KeyEvent escEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    "", "", KeyCode.ESCAPE,
                    false, false, false, false
            );

            // Test initial state
            assertFalse(pauseManager.isPaused());
            assertFalse(pauseActionCalled);

            // Simulate escape key press
            scene.getOnKeyPressed().handle(escEvent);

            // Verify pause state
            assertTrue(pauseManager.isPaused());
            assertTrue(pauseActionCalled);
            assertFalse(resumeActionCalled);

            // Simulate second escape key press
            scene.getOnKeyPressed().handle(escEvent);

            // Verify resume state
            assertFalse(pauseManager.isPaused());
            assertTrue(resumeActionCalled);

            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void testNonEscapeKeyPress() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Create space key event
            KeyEvent spaceEvent = new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    "", "", KeyCode.SPACE,
                    false, false, false, false
            );

            // Test initial state
            assertFalse(pauseManager.isPaused());
            assertFalse(pauseActionCalled);

            // Simulate space key press
            scene.getOnKeyPressed().handle(spaceEvent);

            // Verify state hasn't changed
            assertFalse(pauseManager.isPaused());
            assertFalse(pauseActionCalled);
            assertFalse(resumeActionCalled);

            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }
}