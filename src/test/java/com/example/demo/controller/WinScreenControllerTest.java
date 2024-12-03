//package com.example.demo.controller;
//
//import com.example.demo.JavaFXTest;
//import com.example.demo.config.GameConfig;
//import com.example.demo.levels.LevelOne;
//import com.example.demo.managers.NavigationManager;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Method;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//public class WinScreenControllerTest extends JavaFXTest {
//
//    private WinScreenController controller;
//    private StackPane winRoot;
//    private NavigationManager navigationManager;
//
//    @BeforeEach
//    public void setUp() {
//        controller = new WinScreenController();
//        winRoot = new StackPane();
//        controller.winRoot = winRoot;
//        Stage stage = new Stage();
//        controller.setStage(stage);
//        navigationManager = mock(NavigationManager.class);
//    }
//
//    @Test
//    void testInitialize() {
//        controller.initialize();
//        assertEquals(GameConfig.SCREEN_WIDTH, winRoot.getPrefWidth());
//        assertEquals(GameConfig.SCREEN_HEIGHT, winRoot.getPrefHeight());
//    }
//
//    @Test
//    void testHandleRestart() throws Exception {
//        Method handleRestart = WinScreenController.class.getDeclaredMethod("handleRestart");
//        handleRestart.setAccessible(true);
//        handleRestart.invoke(controller);
//        verify(navigationManager, mock -> mock.restartLevel(LevelOne.class));
//    }
//
//    @Test
//    void testHandleMainMenu() throws Exception {
//        Method handleMainMenu = WinScreenController.class.getDeclaredMethod("handleMainMenu");
//        handleMainMenu.setAccessible(true);
//        handleMainMenu.invoke(controller);
//        verify(navigationManager, mock -> mock.goToMainMenu());
//    }
//}