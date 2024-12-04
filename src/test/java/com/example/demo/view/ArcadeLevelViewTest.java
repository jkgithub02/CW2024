package com.example.demo.view;

import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcadeLevelViewTest extends JavaFXTest {
    private ArcadeLevelView arcadeLevelView;
    private Group root;
    private static final int HEARTS_TO_DISPLAY = 3;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            root = new Group();
            arcadeLevelView = new ArcadeLevelView(root, HEARTS_TO_DISPLAY);
        });
        waitForFxEvents();
    }

    @Test
    void testShowKillCountDisplay() {
        Platform.runLater(() -> {
            int initialChildCount = root.getChildren().size();
            arcadeLevelView.showKillCountDisplay();
            assertEquals(initialChildCount + 1, root.getChildren().size(),
                    "Kill count display should be added to root");
        });
        waitForFxEvents();
    }

    @Test
    void testUpdateKillCount() {
        Platform.runLater(() -> {
            arcadeLevelView.showKillCountDisplay();
            arcadeLevelView.updateKillCount(5);

            // Find the kill count display in the root's children
            boolean hasKillCountDisplay = root.getChildren().stream()
                    .anyMatch(node -> node.toString().contains("Score: 5"));
            assertTrue(hasKillCountDisplay, "Kill count should be updated to 5");
        });
        waitForFxEvents();
    }

    private void waitForFxEvents() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}