package com.example.demo;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public abstract class JavaFXTest {
    private static boolean initialized = false;

    @BeforeAll
    static void initToolkit() {
        if (!initialized) {
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            Platform.startup(() -> {});
            initialized = true;
        }
    }
}