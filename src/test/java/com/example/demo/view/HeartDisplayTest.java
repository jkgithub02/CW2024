package com.example.demo.view;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeartDisplayTest {

    private HeartDisplay heartDisplay;

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        heartDisplay = new HeartDisplay(100, 50, 5);
    }

    @Test
    void testInitialHearts() {
        HBox container = heartDisplay.getContainer();
        assertEquals(5, container.getChildren().size());
    }

    @Test
    void testRemoveHeart() {
        heartDisplay.removeHeart();
        HBox container = heartDisplay.getContainer();
        assertEquals(4, container.getChildren().size());
    }

    @Test
    void testContainerPosition() {
        HBox container = heartDisplay.getContainer();
        assertEquals(100, container.getLayoutX());
        assertEquals(50, container.getLayoutY());
    }
}