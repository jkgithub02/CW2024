package com.example.demo.controller;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArcadeModeControllerTest extends JavaFXTest {
    private ArcadeModeController arcadeModeController;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            arcadeModeController = new ArcadeModeController(stage);
        });
    }

    @Test
    void testLaunchArcadeMode() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> arcadeModeController.launchArcadeMode());
        });
    }
}