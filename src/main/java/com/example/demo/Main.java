package com.example.demo;

import com.example.demo.config.GameConfig;
import com.example.demo.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * The main entry point for all JavaFX applications.
 *
 * @see javafx.application.Application
 * @see javafx.stage.Stage
 */
public class Main extends Application {

	/**
	 * The main entry point for all JavaFX applications.
	 *
	 * @param stage the primary stage for this application, onto which
	 *              the application scene can be set.
	 * @throws ClassNotFoundException if the class cannot be located.
	 * @throws NoSuchMethodException if a particular method cannot be found.
	 * @throws SecurityException if a security violation occurs.
	 * @throws InstantiationException if the class that declares the underlying constructor represents an abstract class.
	 * @throws IllegalAccessException if the underlying constructor is inaccessible.
	 * @throws IllegalArgumentException if the method is invoked with incorrect arguments.
	 * @throws InvocationTargetException if the underlying constructor throws an exception.
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			URL fxmlLocation = getClass().getClassLoader().getResource("MenuScreen.fxml");
			FXMLLoader loader = new FXMLLoader(fxmlLocation);
			Parent root = loader.load();
			MainMenuController controller = loader.getController();
			controller.setStage(stage);

			Scene scene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
			stage.setTitle(GameConfig.TITLE);
			stage.setScene(scene);
			stage.setWidth(GameConfig.SCREEN_WIDTH);
			stage.setHeight(GameConfig.SCREEN_HEIGHT);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method which launches the JavaFX application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}
}