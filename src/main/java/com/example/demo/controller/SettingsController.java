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

/**
 * Controller class for managing the settings screen.
 *
 * @see com.example.demo.managers.KeyBindingsManager
 * @see com.example.demo.managers.SoundManager
 */
public class SettingsController {
    /**
     * The primary stage of the application.
     */
    private Stage stage;

    /**
     * The sound manager instance.
     */
    private SoundManager soundManager;

    /**
     * The key bindings manager instance.
     */
    private final KeyBindingsManager keyBindingsManager;

    /**
     * The button currently being bound.
     */
    private Button currentlyBinding;

    /**
     * The background image of the settings screen.
     */
    @FXML
    private ImageView backgroundImage;

    /**
     * The background pane for the settings.
     */
    @FXML
    private Pane settingsBackground;

    /**
     * The container for the settings.
     */
    @FXML
    private VBox settingsContainer;

    /**
     * The slider for adjusting the background volume.
     */
    @FXML
    private Slider backgroundVolumeSlider;

    /**
     * The slider for adjusting the effects volume.
     */
    @FXML
    private Slider effectsVolumeSlider;

    /**
     * The button for binding the up key.
     */
    @FXML
    private Button upKeyButton;

    /**
     * The button for binding the down key.
     */
    @FXML
    private Button downKeyButton;

    /**
     * The button for binding the left key.
     */
    @FXML
    private Button leftKeyButton;

    /**
     * The button for binding the right key.
     */
    @FXML
    private Button rightKeyButton;

    /**
     * The button for binding the fire key.
     */
    @FXML
    private Button fireKeyButton;

    /**
     * Constructs a SettingsController.
     */
    public SettingsController() {
        this.keyBindingsManager = KeyBindingsManager.getInstance();
    }

    /**
     * Sets the stage for this controller.
     *
     * @param stage the primary stage of the application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the settings screen by setting up the UI components and loading the key bindings.
     */
    @FXML
    private void initialize() {
        soundManager = SoundManager.getInstance();
        keyBindingsManager.ensureDefaultBindings();
        initializeUI();
        initializeKeyBindingButtons();
    }

    /**
     * Initializes the UI components and sets up the volume sliders.
     */
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

    /**
     * Initializes the key binding buttons.
     */
    private void initializeKeyBindingButtons() {
        updateKeyBindingButton(upKeyButton, "UP");
        updateKeyBindingButton(downKeyButton, "DOWN");
        updateKeyBindingButton(leftKeyButton, "LEFT");
        updateKeyBindingButton(rightKeyButton, "RIGHT");
        updateKeyBindingButton(fireKeyButton, "FIRE");
    }

    /**
     * Updates the text of the key binding button with the current key binding.
     *
     * @param button the button to update.
     * @param action the action associated with the key binding.
     */
    private void updateKeyBindingButton(Button button, String action) {
        if (button == null || action == null) return;

        KeyCode keyCode = keyBindingsManager.getBinding(action);
        button.setText(action + ": " + (keyCode != null ? keyCode.getName() : "Not Set"));
    }

    /**
     * Handles the binding of the up key.
     */
    @FXML
    private void handleUpKeyBinding() {
        startBinding(upKeyButton, "UP");
    }

    /**
     * Handles the binding of the down key.
     */
    @FXML
    private void handleDownKeyBinding() {
        startBinding(downKeyButton, "DOWN");
    }

    /**
     * Handles the binding of the left key.
     */
    @FXML
    private void handleLeftKeyBinding() {
        startBinding(leftKeyButton, "LEFT");
    }

    /**
     * Handles the binding of the right key.
     */
    @FXML
    private void handleRightKeyBinding() {
        startBinding(rightKeyButton, "RIGHT");
    }

    /**
     * Handles the binding of the fire key.
     */
    @FXML
    private void handleFireKeyBinding() {
        startBinding(fireKeyButton, "FIRE");
    }

    /**
     * Starts the key binding process for the specified button and action.
     *
     * @param button the button to bind.
     * @param action the action associated with the key binding.
     */
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

    /**
     * Updates all key binding buttons with the current key bindings.
     */
    private void updateAllKeyBindingButtons() {
        updateKeyBindingButton(upKeyButton, "UP");
        updateKeyBindingButton(downKeyButton, "DOWN");
        updateKeyBindingButton(leftKeyButton, "LEFT");
        updateKeyBindingButton(rightKeyButton, "RIGHT");
        updateKeyBindingButton(fireKeyButton, "FIRE");
    }

    /**
     * Handles resetting the key bindings to their default values.
     */
    @FXML
    private void handleResetBindings() {
        keyBindingsManager.resetToDefaults();
        updateAllKeyBindingButtons();
    }

    /**
     * Handles navigating back to the main menu.
     */
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