package com.example.demo.view;

import com.example.demo.JavaFXTest;
import com.example.demo.ShieldImage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

class ShieldImageTest extends JavaFXTest {

    private ShieldImage shieldImage;
    private Stage stage;

    @BeforeEach
    void setUp() {
        shieldImage = new ShieldImage(0,0);
    }

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        this.shieldImage = new ShieldImage(0, 0);
        Scene scene = new Scene(new StackPane(shieldImage), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shieldImageIsInitiallyHidden() {
        assertFalse(shieldImage.isVisible());
    }

    @Test
    void shieldImageIsShownWhenShowShieldIsCalled() {
        shieldImage.showShield();
        assertTrue(shieldImage.isVisible());
    }

    @Test
    void shieldImageIsHiddenWhenHideShieldIsCalled() {
        shieldImage.showShield();
        shieldImage.hideShield();
        assertFalse(shieldImage.isVisible());
    }


    @Test
    void shieldImageHasCorrectSize() {
        assertEquals(100, shieldImage.getFitHeight());
        assertTrue(shieldImage.isPreserveRatio());
    }
}