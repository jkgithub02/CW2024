package com.example.demo.managers;

import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class KeyBindingsManager {
    private static KeyBindingsManager instance;
    private final Map<String, KeyCode> keyBindings;
    private final Map<KeyCode, String> reverseBindings; // For checking duplicate bindings
    private final Preferences preferences;

    private KeyBindingsManager() {
        keyBindings = new HashMap<>();
        reverseBindings = new HashMap<>();
        preferences = Preferences.userNodeForPackage(KeyBindingsManager.class);
        ensureDefaultBindings();
    }

    public static KeyBindingsManager getInstance() {
        if (instance == null) {
            instance = new KeyBindingsManager();
        }
        return instance;
    }

    public void ensureDefaultBindings() {
        // Only set defaults if no bindings exist
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


    public KeyCode getBinding(String action) {
        KeyCode keyCode = keyBindings.get(action);
        // Return default binding if none exists
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
    public boolean setBinding(String action, KeyCode keyCode) {
        // Don't allow null or ESCAPE key bindings
        if (keyCode == null || keyCode == KeyCode.ESCAPE) {
            return false;
        }

        // Check if key is already bound to a different action
        String boundAction = reverseBindings.get(keyCode);
        if (boundAction != null && !boundAction.equals(action)) {
            return false; // Key is already bound to another action
        }

        // Remove old binding if it exists
        KeyCode oldKey = keyBindings.get(action);
        if (oldKey != null) {
            reverseBindings.remove(oldKey);
        }

        // Set new binding
        keyBindings.put(action, keyCode);
        reverseBindings.put(keyCode, action);
        preferences.put(action, keyCode.name());
        return true;
    }

    public void resetToDefaults() {
        keyBindings.clear();
        reverseBindings.clear();
        setBinding("UP", KeyCode.UP);
        setBinding("DOWN", KeyCode.DOWN);
        setBinding("LEFT", KeyCode.LEFT);
        setBinding("RIGHT", KeyCode.RIGHT);
        setBinding("FIRE", KeyCode.SPACE);
    }

    public boolean isKeyBound(KeyCode keyCode) {
        return reverseBindings.containsKey(keyCode);
    }
}