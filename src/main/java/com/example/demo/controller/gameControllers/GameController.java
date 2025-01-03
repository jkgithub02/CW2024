package com.example.demo.controller.gameControllers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.view.effects.LevelTransitionScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;

/**
 * Controller class responsible for managing the game flow and transitions between levels.
 *
 * @see LevelTransitionScreen
 * @see com.example.demo.levels.LevelParent
 */
public class GameController {

	/**
	 * The fully qualified name of the starting level class.
	 */
	private static final String START_LEVEL_CLASS_NAME = "com.example.demo.levels.LevelOne";

	/**
	 * The display name of the starting level.
	 */
	private static final String START_LEVEL_DISPLAY_NAME = "Level 1";

	/**
	 * The primary stage for this application.
	 */
	private final Stage stage;

	/**
	 * Constructs a GameController with the specified stage.
	 *
	 * @param stage the primary stage for this application.
	 */
	public GameController(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by showing the stage and starting the first level.
	 *
	 * @throws ClassNotFoundException if the class cannot be located.
	 * @throws NoSuchMethodException if a particular method cannot be found.
	 * @throws SecurityException if a security violation occurs.
	 * @throws InstantiationException if the class that declares the underlying constructor represents an abstract class.
	 * @throws IllegalAccessException if the underlying constructor is inaccessible.
	 * @throws IllegalArgumentException if the method is invoked with incorrect arguments.
	 * @throws InvocationTargetException if the underlying constructor throws an exception.
	 * @see #goToLevel(String, String)
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.show();
		goToLevel(START_LEVEL_CLASS_NAME, START_LEVEL_DISPLAY_NAME);
	}

	/**
	 * Transitions to the specified level.
	 *
	 * @param className the fully qualified name of the level class.
	 * @param levelname the name of the level.
	 * @throws ClassNotFoundException if the class cannot be located.
	 * @throws NoSuchMethodException if a particular method cannot be found.
	 * @throws SecurityException if a security violation occurs.
	 * @throws InstantiationException if the class that declares the underlying constructor represents an abstract class.
	 * @throws IllegalAccessException if the underlying constructor is inaccessible.
	 * @throws IllegalArgumentException if the method is invoked with incorrect arguments.
	 * @throws InvocationTargetException if the underlying constructor throws an exception.
	 * @see #startLevel(String)
	 */
	public void goToLevel(String className, String levelname) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LevelTransitionScreen transitionScreen = new LevelTransitionScreen(stage, levelname, () -> {
			try {
				startLevel(className);
			} catch (Exception e) {
				showErrorAlert(e);
				e.printStackTrace();
			}
		});
		transitionScreen.show();
	}

	/**
	 * Starts the specified level.
	 *
	 * @param className the fully qualified name of the level class.
	 * @throws ClassNotFoundException if the class cannot be located.
	 * @throws NoSuchMethodException if a particular method cannot be found.
	 * @throws SecurityException if a security violation occurs.
	 * @throws InstantiationException if the class that declares the underlying constructor represents an abstract class.
	 * @throws IllegalAccessException if the underlying constructor is inaccessible.
	 * @throws IllegalArgumentException if the method is invoked with incorrect arguments.
	 * @throws InvocationTargetException if the underlying constructor throws an exception.
	 * @see #goToLevel(String, String)
	 */
	private void startLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor();
		// Use fixed dimensions instead of stage dimensions
		LevelParent myLevel = (LevelParent) constructor.newInstance();
		myLevel.nextLevelProperty().addListener((observable, oldValue, newValue) -> {
			try {
				String[] levelInfo = newValue.split(",");
				goToLevel(levelInfo[0], levelInfo[1]);
			} catch (Exception e) {
				showErrorAlert(e);
			}
		});
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Displays an error alert with the specified exception.
	 *
	 * @param e the exception to display.
	 */
	private void showErrorAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(e.getClass().toString());
		alert.show();
	}
}