package com.example.demo.view;

import com.example.demo.JavaFXTest;
import com.example.demo.LevelView;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

class LevelViewTest extends JavaFXTest {

    private LevelView levelView;
    private Group root;

    @Start
    private void start(Stage stage) {
        root = new Group();
        levelView = new LevelView(root, 3, 5, 10);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            root = new Group();
            levelView = new LevelView(root, 3, 5, 10);
        });
    }

    @Test
    void killCountDisplayIsShown() {
        Platform.runLater(() -> {
            assertEquals(0, root.getChildren().size());
            levelView.showKillCountDisplay();
            assertEquals(1, root.getChildren().size());
        });
    }

    @Test
    void heartDisplayIsShown() {
        Platform.runLater(() -> {
            assertEquals(0, root.getChildren().size());
            levelView.showHeartDisplay();
            assertEquals(1, root.getChildren().size());
        });
    }

    @Test
    void killCountIsUpdated() {
        Platform.runLater(() -> {
            levelView.showKillCountDisplay();
            levelView.updateKillCount(3);
            assertTrue(root.getChildren().size() > 0);
        });
    }

    @Test
    void heartsAreRemoved() {
        Platform.runLater(() -> {
            levelView.showHeartDisplay();
            levelView.removeHearts(1);
            assertTrue(root.getChildren().size() > 0);
        });
    }

    @Test
    void completeViewIsDisplayed() {
        Platform.runLater(() -> {
            levelView.showHeartDisplay();
            levelView.showKillCountDisplay();
            assertEquals(2, root.getChildren().size());
        });
    }

    @Test
    void completeViewIsUpdated() {
        Platform.runLater(() -> {
            levelView.showHeartDisplay();
            levelView.showKillCountDisplay();
            levelView.updateKillCount(3);
            levelView.removeHearts(2);
            assertEquals(2, root.getChildren().size());
        });
    }
}