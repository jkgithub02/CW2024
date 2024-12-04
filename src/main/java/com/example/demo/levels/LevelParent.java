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
 */
public abstract class LevelParent {

    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 50;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final Group root;
    private final Timeline timeline;
    private final UserPlane user;
    private final Scene scene;
    private final ImageView background;
    private final EntityManager entityManager;

    private LevelView levelView;
    private final StringProperty nextLevelProperty = new SimpleStringProperty();
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private Map<ActiveActorDestructible, Rectangle> actorHitboxes = new HashMap<>();

    private final CollisionManager collisionManager;
    private final InputManager inputManager;
    private final PauseManager pauseHandler;
    private final GameInitializer gameInitializer;
    private final NavigationManager navigationManager;
    private final SoundManager soundManager;
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
                this::goToMainMenu,    // Add main menu action
                this::restartLevel     // Add restart action
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
        navigationManager.showWinScreen();
    }

    /**
     * Handles losing the game.
     */
    protected void loseGame() {
        stopTimelineAndMusic();
        inputManager.setGameState(GameState.LOSE);
        navigationManager.showGameOverScreen(this.getClass());
    }

    public static void setGlobalRoot(Group root) {
        globalRoot = root;
    }

    public static Group getGlobalRoot() {
        return globalRoot;
    }

}