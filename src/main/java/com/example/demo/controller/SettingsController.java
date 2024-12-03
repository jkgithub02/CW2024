package com.example.demo.controller;

import com.example.demo.managers.SoundManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {
    private Stage stage;
    private SoundManager soundManager;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Pane settingsBackground;

    @FXML
    private VBox settingsContainer;

    @FXML
    private Slider backgroundVolumeSlider;

    @FXML
    private Slider effectsVolumeSlider;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        soundManager = SoundManager.getInstance();

        // Initialize volume sliders
        backgroundVolumeSlider.setValue(soundManager.getBackgroundVolume() * 100);
        effectsVolumeSlider.setValue(soundManager.getEffectsVolume() * 100);

        // Bind background image dimensions
        backgroundImage.fitHeightProperty().bind(((Pane) backgroundImage.getParent()).heightProperty());
        backgroundImage.fitWidthProperty().bind(((Pane) backgroundImage.getParent()).widthProperty());

        // Center the settings container
        settingsContainer.layoutXProperty().bind(((Pane) settingsContainer.getParent()).widthProperty()
                .subtract(settingsContainer.widthProperty()).divide(2));
        settingsContainer.layoutYProperty().bind(((Pane) settingsContainer.getParent()).heightProperty()
                .subtract(settingsContainer.heightProperty()).divide(2));

        // Add padding to settingsContainer
        settingsContainer.setPadding(new Insets(20, 40, 20, 40));

        // Make settingsBackground match settingsContainer size with extra padding
        settingsBackground.minWidthProperty().bind(settingsContainer.widthProperty().add(80));
        settingsBackground.minHeightProperty().bind(settingsContainer.heightProperty().add(40));
        settingsBackground.maxWidthProperty().bind(settingsContainer.widthProperty().add(80));
        settingsBackground.maxHeightProperty().bind(settingsContainer.heightProperty().add(40));

        // Center the settingsBackground
        settingsBackground.layoutXProperty().bind(((Pane) settingsBackground.getParent()).widthProperty()
                .subtract(settingsBackground.widthProperty()).divide(2));
        settingsBackground.layoutYProperty().bind(((Pane) settingsBackground.getParent()).heightProperty()
                .subtract(settingsBackground.heightProperty()).divide(2));

        // Add volume slider listeners
        backgroundVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            soundManager.setBackgroundVolume(newValue.doubleValue() / 100);
        });

        effectsVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            soundManager.setEffectsVolume(newValue.doubleValue() / 100);
        });
    }

    @FXML
    private void backToMenu() {
        try {
            soundManager.stopAllBackgroundMusic();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MenuScreen.fxml"));
            Scene menuScene = new Scene(loader.load());
            MainMenuController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}