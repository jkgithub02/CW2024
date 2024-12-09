package com.example.demo.controller;

import com.example.demo.managers.KeyBindingsManager;
import com.example.demo.managers.SoundManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SettingsController {
    private Stage stage;
    private SoundManager soundManager;
    private final KeyBindingsManager keyBindingsManager;
    private Button currentlyBinding;

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
    @FXML
    private Button upKeyButton;
    @FXML
    private Button downKeyButton;
    @FXML
    private Button leftKeyButton;
    @FXML
    private Button rightKeyButton;

    public SettingsController() {
        this.keyBindingsManager = KeyBindingsManager.getInstance();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        soundManager = SoundManager.getInstance();
        keyBindingsManager.ensureDefaultBindings();
        initializeUI();
        initializeKeyBindingButtons();
    }

    private void initializeUI() {
        // Initialize volume sliders
        backgroundVolumeSlider.setValue(soundManager.getBackgroundVolume() * 100);
        effectsVolumeSlider.setValue(soundManager.getEffectsVolume() * 100);

        // UI Bindings
        backgroundImage.fitHeightProperty().bind(((Pane) backgroundImage.getParent()).heightProperty());
        backgroundImage.fitWidthProperty().bind(((Pane) backgroundImage.getParent()).widthProperty());

        settingsContainer.layoutXProperty().bind(((Pane) settingsContainer.getParent()).widthProperty()
                .subtract(settingsContainer.widthProperty()).divide(2));
        settingsContainer.layoutYProperty().bind(((Pane) settingsContainer.getParent()).heightProperty()
                .subtract(settingsContainer.heightProperty()).divide(2));
        settingsContainer.setPadding(new Insets(20, 40, 20, 40));

        // Background settings
        settingsBackground.minWidthProperty().bind(settingsContainer.widthProperty().add(80));
        settingsBackground.minHeightProperty().bind(settingsContainer.heightProperty().add(40));
        settingsBackground.maxWidthProperty().bind(settingsContainer.widthProperty().add(80));
        settingsBackground.maxHeightProperty().bind(settingsContainer.heightProperty().add(40));
        settingsBackground.layoutXProperty().bind(((Pane) settingsBackground.getParent()).widthProperty()
                .subtract(settingsBackground.widthProperty()).divide(2));
        settingsBackground.layoutYProperty().bind(((Pane) settingsBackground.getParent()).heightProperty()
                .subtract(settingsBackground.heightProperty()).divide(2));

        // Volume slider listeners
        backgroundVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                soundManager.setBackgroundVolume(newValue.doubleValue() / 100));
        effectsVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                soundManager.setEffectsVolume(newValue.doubleValue() / 100));
    }

    private void initializeKeyBindingButtons() {
        updateKeyBindingButton(upKeyButton, "UP");
        updateKeyBindingButton(downKeyButton, "DOWN");
        updateKeyBindingButton(leftKeyButton, "LEFT");
        updateKeyBindingButton(rightKeyButton, "RIGHT");
    }

    private void updateKeyBindingButton(Button button, String action) {
        if (button == null || action == null) return;

        KeyCode keyCode = keyBindingsManager.getBinding(action);
        button.setText(action + ": " + (keyCode != null ? keyCode.getName() : "Not Set"));
    }

    @FXML
    private void handleUpKeyBinding() {
        startBinding(upKeyButton, "UP");
    }

    @FXML
    private void handleDownKeyBinding() {
        startBinding(downKeyButton, "DOWN");
    }

    @FXML
    private void handleLeftKeyBinding() {
        startBinding(leftKeyButton, "LEFT");
    }

    @FXML
    private void handleRightKeyBinding() {
        startBinding(rightKeyButton, "RIGHT");
    }

    private void startBinding(Button button, String action) {
        if (currentlyBinding != null) {
            currentlyBinding.setStyle("");
        }
        currentlyBinding = button;
        button.setStyle("-fx-border-color: #ff0000;");
        button.setText("Press any key...");

        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (currentlyBinding == button) {
                    event.consume();
                    KeyCode newKey = event.getCode();

                    if (newKey != KeyCode.ESCAPE) {
                        if (keyBindingsManager.setBinding(action, newKey)) {
                            updateAllKeyBindingButtons();
                        } else {
                            button.setText("Key already in use!");
                            Timeline timeline = new Timeline(new KeyFrame(
                                    Duration.seconds(2),
                                    e -> updateKeyBindingButton(button, action)
                            ));
                            timeline.play();
                        }
                    }

                    currentlyBinding.setStyle("");
                    currentlyBinding = null;
                    button.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, this);
                }
            }
        };

        button.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
        button.requestFocus();
    }

    private void updateAllKeyBindingButtons() {
        updateKeyBindingButton(upKeyButton, "UP");
        updateKeyBindingButton(downKeyButton, "DOWN");
        updateKeyBindingButton(leftKeyButton, "LEFT");
        updateKeyBindingButton(rightKeyButton, "RIGHT");
    }

    @FXML
    private void handleResetBindings() {
        keyBindingsManager.resetToDefaults();
        updateAllKeyBindingButtons();
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