package com.example.demo.view;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KillCountDisplayTest {

    private KillCountDisplay killCountDisplay;

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        killCountDisplay = new KillCountDisplay(100, 50, 10);
    }

    @Test
    void testInitialKillCount() {
        assertEquals("Kills: 0/10", killCountDisplay.getDisplayText());
    }

    @Test
    void testUpdateKillCount() {
        killCountDisplay.updateKillCount(5);
        assertEquals("Kills: 5/10", killCountDisplay.getDisplayText());
    }

    @Test
    void testContainerPosition() {
        HBox container = killCountDisplay.getContainer();
        assertEquals(100, container.getLayoutX());
        assertEquals(50, container.getLayoutY());
    }
}