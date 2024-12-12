package com.example.demo.managers;

import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * Manages key bindings for user actions in the game.
 */
public class KeyBindingsManager {
    /**
     * The singleton instance of KeyBindingsManager.
     */
    private static KeyBindingsManager instance;

    /**
     * A map associating actions with their respective key codes.
     */
    private final Map<String, KeyCode> keyBindings;

    /**
     * A map for reverse look-up of key codes to actions, used for checking duplicate bindings.
     */
    private final Map<KeyCode, String> reverseBindings;

    /**
     * User preferences for storing key bindings.
     */
    private final Preferences preferences;

    /**
     * Private constructor to initialize key bindings and preferences.
     */
    private KeyBindingsManager() {
        keyBindings = new HashMap<>();
        reverseBindings = new HashMap<>();
        preferences = Preferences.userNodeForPackage(KeyBindingsManager.class);
        ensureDefaultBindings();
    }

    /**
     * Returns the singleton instance of KeyBindingsManager.
     *
     * @return the singleton instance.
     */
    public static KeyBindingsManager getInstance() {
        if (instance == null) {
            instance = new KeyBindingsManager();
        }
        return instance;
    }

    /**
     * Ensures that default key bindings are set if no bindings exist.
     */
    public void ensureDefaultBindings() {
        if (!keyBindings.containsKey("UP")) {
            setBinding("UP", KeyCode.valueOf(preferences.get("UP", KeyCode.UP.name())));
        }
        if (!keyBindings.containsKey("DOWN")) {
            setBinding("DOWN", KeyCode.valueOf(preferences.get("DOWN", KeyCode.DOWN.name())));
        }
        if (!keyBindings.containsKey("LEFT")) {
            setBinding("LEFT", KeyCode.valueOf(preferences.get("LEFT", KeyCode.LEFT.name())));
        }
        if (!keyBindings.containsKey("RIGHT")) {
            setBinding("RIGHT", KeyCode.valueOf(preferences.get("RIGHT", KeyCode.RIGHT.name())));
        }
        if (!keyBindings.containsKey("FIRE")) {
            setBinding("FIRE", KeyCode.valueOf(preferences.get("FIRE", KeyCode.SPACE.name())));
        }
    }

    /**
     * Gets the key code associated with a specified action.
     *
     * @param action the action to get the key code for.
     * @return the key code associated with the action.
     */
    public KeyCode getBinding(String action) {
        KeyCode keyCode = keyBindings.get(action);
        if (keyCode == null) {
            switch (action) {
                case "UP": return KeyCode.UP;
                case "DOWN": return KeyCode.DOWN;
                case "LEFT": return KeyCode.LEFT;
                case "RIGHT": return KeyCode.RIGHT;
                case "FIRE": return KeyCode.SPACE;
                default: return null;
            }
        }
        return keyCode;
    }

    /**
     * Sets the key code for a specified action.
     *
     * @param action the action to set the key code for.
     * @param keyCode the key code to set for the action.
     * @return true if the binding was set successfully, false otherwise.
     */
    public boolean setBinding(String action, KeyCode keyCode) {
        if (keyCode == null || keyCode == KeyCode.ESCAPE) {
            return false;
        }

        String boundAction = reverseBindings.get(keyCode);
        if (boundAction != null && !boundAction.equals(action)) {
            return false;
        }

        KeyCode oldKey = keyBindings.get(action);
        if (oldKey != null) {
            reverseBindings.remove(oldKey);
        }

        keyBindings.put(action, keyCode);
        reverseBindings.put(keyCode, action);
        preferences.put(action, keyCode.name());
        return true;
    }

    /**
     * Resets all key bindings to their default values.
     */
    public void resetToDefaults() {
        keyBindings.clear();
        reverseBindings.clear();
        setBinding("UP", KeyCode.UP);
        setBinding("DOWN", KeyCode.DOWN);
        setBinding("LEFT", KeyCode.LEFT);
        setBinding("RIGHT", KeyCode.RIGHT);
        setBinding("FIRE", KeyCode.SPACE);
    }

    /**
     * Checks if a key code is already bound to an action.
     *
     * @param keyCode the key code to check.
     * @return true if the key code is bound, false otherwise.
     */
    public boolean isKeyBound(KeyCode keyCode) {
        return reverseBindings.containsKey(keyCode);
    }
}