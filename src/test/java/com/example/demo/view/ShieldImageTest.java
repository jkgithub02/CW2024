package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ShieldImageTest {

    private ShieldImage shieldImage;
    private Stage stage;

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