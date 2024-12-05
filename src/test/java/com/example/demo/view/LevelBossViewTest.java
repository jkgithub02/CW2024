package com.example.demo.view;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelBossViewTest extends JavaFXTest {
    private LevelBossView bossLevelView;
    private Group root;
    private static final int HEARTS_TO_DISPLAY = 3;
    private static final int MAX_KILLS = 10;
    private static final int MAX_BULLETS = 10;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            root = new Group();
            bossLevelView = new LevelBossView(root, HEARTS_TO_DISPLAY, MAX_KILLS, MAX_BULLETS);
        });
        waitForFxEvents();
    }

    @Test
    void testBossHealthBarAdded() {
        Platform.runLater(() -> {
            boolean hasBossHealthBar = root.getChildren().stream()
                    .anyMatch(node -> node instanceof BossHealthBar);
            assertTrue(hasBossHealthBar, "Boss health bar should be added to root");
        });
        waitForFxEvents();
    }

    @Test
    void testKillCountDisplayNotShown() {
        Platform.runLater(() -> {
            int initialChildCount = root.getChildren().size();
            bossLevelView.showKillCountDisplay();
            assertEquals(initialChildCount, root.getChildren().size(),
                    "Kill count display should not be added");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateKillCountDoesNothing() {
        Platform.runLater(() -> {
            int initialChildCount = root.getChildren().size();
            bossLevelView.updateKillCount(5);
            assertEquals(initialChildCount, root.getChildren().size(),
                    "Kill count update should not affect display");
        });
        waitForFxEvents();
    }

    @Test
    void testHeartDisplayShown() {
        Platform.runLater(() -> {
            int initialChildCount = root.getChildren().size();
            bossLevelView.showHeartDisplay();
            assertTrue(root.getChildren().size() > initialChildCount,
                    "Heart display should be added to root");
        });
        waitForFxEvents();
    }

}