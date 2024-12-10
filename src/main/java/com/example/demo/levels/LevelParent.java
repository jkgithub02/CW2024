package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.DestructionType;
import com.example.demo.actors.planes.FighterPlane;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.config.GameConfig;
import com.example.demo.config.GameState;
import com.example.demo.managers.*;
import com.example.demo.view.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

/**
 * Abstract class representing a parent level in the game.
 *
 * @see com.example.demo.actors.ActiveActorDestructible
 * @see com.example.demo.actors.planes.UserPlane
 * @see com.example.demo.config.GameConfig
 * @see com.example.demo.config.GameState
 * @see com.example.demo.managers.EntityManager
 * @see com.example.demo.managers.CollisionManager
 * @see com.example.demo.managers.InputManager
 * @see com.example.demo.managers.PauseManager
 * @see com.example.demo.managers.GameInitializer
 * @see com.example.demo.managers.NavigationManager
 * @see com.example.demo.managers.SoundManager
 * @see com.example.demo.view.LevelView
 */
public abstract class LevelParent {

    /**
     * The height adjustment for the screen.
     */
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

    /**
     * The delay in milliseconds for the game loop.
     */
    private static final int MILLISECOND_DELAY = 50;

    /**
     * The height of the screen.
     */
    private final double screenHeight;

    /**
     * The width of the screen.
     */
    private final double screenWidth;

    /**
     * The maximum Y position for enemies.
     */
    private final double enemyMaximumYPosition;

    /**
     * The root group for the scene.
     */
    private final Group root;

    /**
     * The timeline for the game loop.
     */
    private final Timeline timeline;

    /**
     * The user-controlled plane.
     */
    private final UserPlane user;

    /**
     * The scene for the level.
     */
    private final Scene scene;

    /**
     * The background image for the level.
     */
    private final ImageView background;

    /**
     * The entity manager for managing game entities.
     */
    private final EntityManager entityManager;

    /**
     * The view for the level.
     */
    private LevelView levelView;

    /**
     * The property for the next level.
     */
    private final StringProperty nextLevelProperty = new SimpleStringProperty();

    /**
     * The set of pressed keys.
     */
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    /**
     * The map of actor hitboxes.
     */
    private Map<ActiveActorDestructible, Rectangle> actorHitboxes = new HashMap<>();

    /**
     * The collision manager for handling collisions.
     */
    private final CollisionManager collisionManager;

    /**
     * The input manager for handling user input.
     */
    private final InputManager inputManager;

    /**
     * The pause manager for handling game pauses.
     */
    private final PauseManager pauseHandler;

    /**
     * The game initializer for initializing the game.
     */
    private final GameInitializer gameInitializer;

    /**
     * The navigation manager for handling navigation.
     */
    private final NavigationManager navigationManager;

    /**
     * The sound manager for handling sounds.
     */
    private final SoundManager soundManager;

    /**
     * The global root group.
     */
    private static Group globalRoot;

    /**
     * Constructs a LevelParent with the specified background image and initial player health.
     *
     * @param backgroundImageName the name of the background image file.
     * @param playerInitialHealth the initial health of the player.
     */
    public LevelParent(String backgroundImageName, int playerInitialHealth) {
        this.screenHeight = GameConfig.SCREEN_HEIGHT;
        this.screenWidth = GameConfig.SCREEN_WIDTH;
        this.root = new Group();
        setGlobalRoot(this.root);
        this.scene = new Scene(root, this.screenWidth, this.screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.entityManager = EntityManager.getInstance(root);
        this.entityManager.reset();

        this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();

        this.collisionManager = new CollisionManager();
        this.inputManager = new InputManager(pressedKeys, user, background, root, entityManager.getUserProjectiles());
        this.pauseHandler = new PauseManager(
                scene,
                root,
                this::pauseGame,
                this::resumeGame,
                this::goToMainMenu,
                this::restartLevel
        );
        this.gameInitializer = new GameInitializer(root, scene, background, user, levelView, pauseHandler);
        this.navigationManager = new NavigationManager(scene);
        this.soundManager = SoundManager.getInstance();
        initializeTimelineAndMusic();
        entityManager.addFriendlyUnit(user);
        entityManager.addEnemyDestroyedListener(enemy -> user.incrementKillCount());
    }

    /**
     * Initializes friendly units in the game.
     */
    private void initializeFriendlyUnits() {
        if (!root.getChildren().contains(user)) {
            root.getChildren().add(user);
        }
    }

    /**
     * Checks if the game is over.
     */
    protected abstract void checkIfGameOver();

    /**
     * Spawns enemy units in the game.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Instantiates the level view.
     *
     * @return the instantiated level view.
     */
    protected abstract LevelView instantiateLevelView();

    /**
     * Initializes the scene for the level.
     *
     * @return the initialized scene.
     */
    public Scene initializeScene() {
        gameInitializer.initializeGame();
        inputManager.initializeFireProjectileHandler();
        return scene;
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        background.requestFocus();
        timeline.play();
    }

    /**
     * Advances to the next level.
     *
     * @param nextLevelClassName the class name of the next level.
     * @param nextLevelName the name of the next level.
     */
    protected void goToNextLevel(String nextLevelClassName, String nextLevelName) {
        stopTimelineAndMusic();
        entityManager.reset();
        nextLevelProperty.set(nextLevelClassName + "," + nextLevelName);
    }

    /**
     * Gets the next level property.
     *
     * @return the next level property.
     */
    public StringProperty nextLevelProperty() {
        return nextLevelProperty;
    }

    /**
     * Updates the scene.
     */
    private void updateScene() {
        initializeFriendlyUnits();
        spawnEnemyUnits();
        entityManager.updateActors();
        generateEnemyFire();
        handleEnemyPenetration();
        collisionManager.handleUserProjectileCollisions(
                entityManager.getUserProjectiles(),
                entityManager.getEnemyUnits()
        );
        collisionManager.handleEnemyProjectileCollisions(
                entityManager.getEnemyProjectiles(),
                entityManager.getFriendlyUnits()
        );
        collisionManager.handlePlaneCollisions(
                entityManager.getFriendlyUnits(),
                entityManager.getEnemyUnits()
        );
        entityManager.removeDestroyedActors();
        updateLevelView();
        checkIfGameOver();
        inputManager.updateUserPlaneMovement();
    }

    /**
     * Initializes the timeline and background music.
     */
    private void initializeTimelineAndMusic() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
        soundManager.playBackgroundMusic("level");
    }

    /**
     * Stops the timeline and background music.
     */
    private void stopTimelineAndMusic() {
        timeline.stop();
        soundManager.stopAllBackgroundMusic();
    }

    /**
     * Generates enemy fire projectiles.
     */
    private void generateEnemyFire() {
        entityManager.getEnemyUnits().forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Spawns an enemy projectile.
     *
     * @param projectile the projectile to spawn.
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            soundManager.playShootSound("enemy");
            entityManager.addEnemyProjectile(projectile);
        }
    }

    /**
     * Handles enemy penetration of defenses.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : entityManager.getEnemyUnits()) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy(DestructionType.PENETRATED_DEFENSE);
            }
        }
    }

    /**
     * Updates the level view.
     */
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.updateKillCount(user.getKillCount());
        levelView.updateBulletStatus(
                user.getCurrentBullets(),
                user.isReloading(),
                user.getReloadProgress()
        );
    }

    /**
     * Checks if an enemy has penetrated defenses.
     *
     * @param enemy the enemy to check.
     * @return true if the enemy has penetrated defenses, false otherwise.
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    /**
     * Gets the user plane.
     *
     * @return the user plane.
     */
    protected UserPlane getUser() {
        return user;
    }

    /**
     * Gets the root group.
     *
     * @return the root group.
     */
    protected Group getRoot() {
        return root;
    }

    /**
     * Gets the current number of enemies.
     *
     * @return the current number of enemies.
     */
    protected int getCurrentNumberOfEnemies() {
        return entityManager.getCurrentNumberOfEnemies();
    }

    /**
     * Adds an enemy unit to the game.
     *
     * @param enemy the enemy unit to add.
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        entityManager.addEnemyUnit(enemy);
    }

    /**
     * Gets the maximum Y position for enemies.
     *
     * @return the maximum Y position for enemies.
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Gets the screen width.
     *
     * @return the screen width.
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Checks if the user is destroyed.
     *
     * @return true if the user is destroyed, false otherwise.
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Navigates to the main menu.
     */
    protected void goToMainMenu() {
        stopTimelineAndMusic();
        navigationManager.goToMainMenu();
    }

    /**
     * Restarts the level.
     */
    protected void restartLevel() {
        stopTimelineAndMusic();
        entityManager.reset();
        navigationManager.restartLevel(this.getClass());
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        if (!pauseHandler.isPaused()) {
            timeline.pause();
            user.pauseReload();
            inputManager.setGameState(GameState.PAUSED);
        }
    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        if (pauseHandler.isPaused()) {
            timeline.play();
            user.resumeReload();
            inputManager.setGameState(GameState.ACTIVE);
        }
    }

    /**
     * Handles winning the game.
     */
    protected void winGame() {
        stopTimelineAndMusic();
        inputManager.setGameState(GameState.WIN);
        soundManager.playVictorySound();
        navigationManager.showWinScreen();
    }

    /**
     * Handles losing the game.
     */
    protected void loseGame() {
        stopTimelineAndMusic();
        inputManager.setGameState(GameState.LOSE);
        soundManager.playGameOverSound();
        navigationManager.showGameOverScreen(this.getClass());
    }

    /**
     * Sets the global root group.
     *
     * @param root the root group to set.
     */
    public static void setGlobalRoot(Group root) {
        globalRoot = root;
    }

    /**
     * Gets the global root group.
     *
     * @return the global root group.
     */
    public static Group getGlobalRoot() {
        return globalRoot;
    }
}