package com.example.demo.view;

import com.example.demo.ArcadeLevelView;
import com.example.demo.JavaFXTest;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

            Label killCountLabel = (Label) root.getChildren().stream()
                    .filter(node -> node instanceof HBox)
                    .flatMap(node -> ((HBox) node).getChildren().stream())
                    .filter(node -> node instanceof Label)
                    .findFirst()
                    .orElse(null);

            assertNotNull(killCountLabel, "Kill count label should exist");
            assertEquals("Score: 5", killCountLabel.getText(), "Kill count should show correct score");
        });
        waitForFxEvents();
    }
}