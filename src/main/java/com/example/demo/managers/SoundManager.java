package com.example.demo.managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the sound effects and background music for the game.
 * Implements the Singleton design pattern to ensure only one instance exists.
 */
public class SoundManager {
    // Singleton instance
    private static SoundManager instance;

    private final Map<String, MediaPlayer> musicPlayers;
    private final Map<String, AudioClip> shootSounds;
    private final Map<String, AudioClip> damageSounds;
    private final Map<String, AudioClip> gameStateSounds;

    private final static String LEVEL_BACKGROUND_MUSIC_FILE_PATH = "/music/fightmusic.mp3";
    private final static String MENU_BACKGROUND_MUSIC_FILE_PATH = "/music/backgroundmusic.mp3";
    private final static String USER_SHOOT_SOUND_FILE_PATH = "/music/userprojectile.wav";
    private final static String ENEMY_SHOOT_SOUND_FILE_PATH = "/music/enemyprojectile.wav";
    private final static String USER_DAMAGED_SOUND_FILE_PATH = "/music/damaged.mp3";
    private final static String ENEMY_DAMAGED_SOUND_FILE_PATH = "/music/enemydamaged.wav";
    private final static String RELOAD_SOUND_FILE_PATH = "/music/userreload.mp3";
    private final static String BOSS_SHIELD_SOUND_FILE_PATH = "/music/shieldblock.mp3";
    private final static String GAME_OVER_SOUND_FILE_PATH = "/music/gameover.wav";
    private final static String VICTORY_SOUND_FILE_PATH = "/music/victory.wav";

    private double backgroundVolume = 1.0;
    private double effectsVolume = 1.0;

    // Make constructor private
    private SoundManager() {
        musicPlayers = new HashMap<>();
        shootSounds = new HashMap<>();
        damageSounds = new HashMap<>();
        gameStateSounds = new HashMap<>();

        initializeSounds();
    }

    /**
     * Initializes the sound effects and background music.
     */
    private void initializeSounds() {
        // Initialize background music
        loadBackgroundMusic("menu", MENU_BACKGROUND_MUSIC_FILE_PATH);
        loadBackgroundMusic("level", LEVEL_BACKGROUND_MUSIC_FILE_PATH);

        setBackgroundVolume(0.5); // Set default background volume

        // Initialize shoot sounds
        loadShootSound("user", USER_SHOOT_SOUND_FILE_PATH);
        loadShootSound("enemy", ENEMY_SHOOT_SOUND_FILE_PATH);
        loadShootSound("reload", RELOAD_SOUND_FILE_PATH);

        // Initialize damage sounds
        loadDamageSound("user", USER_DAMAGED_SOUND_FILE_PATH);
        loadDamageSound("enemy", ENEMY_DAMAGED_SOUND_FILE_PATH);
        loadDamageSound("shield", BOSS_SHIELD_SOUND_FILE_PATH);

        loadGameStateSound("gameover", GAME_OVER_SOUND_FILE_PATH);
        loadGameStateSound("victory", VICTORY_SOUND_FILE_PATH);

        setEffectsVolume(0.5); // Set default effects volume
    }

    /**
     * Returns the singleton instance of the SoundManager.
     *
     * @return the singleton instance.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Loads background music from the specified file path and associates it with the given key.
     *
     * @param key the key to associate with the background music.
     * @param musicFilePath the file path of the background music.
     */
    private void loadBackgroundMusic(String key, String musicFilePath) {
        Media music = new Media(getClass().getResource(musicFilePath).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        musicPlayers.put(key, mediaPlayer);
    }

    /**
     * Loads a shoot sound from the specified file path and associates it with the given key.
     *
     * @param key the key to associate with the shoot sound.
     * @param soundFilePath the file path of the shoot sound.
     */
    private void loadShootSound(String key, String soundFilePath) {
        AudioClip audioClip = new AudioClip(getClass().getResource(soundFilePath).toString());
        shootSounds.put(key, audioClip);
    }

    private void loadGameStateSound(String key, String soundFilePath) {
        AudioClip audioClip = new AudioClip(getClass().getResource(soundFilePath).toString());
        gameStateSounds.put(key, audioClip);
    }

    /**
     * Loads a damage sound from the specified file path and associates it with the given key.
     *
     * @param key the key to associate with the damage sound.
     * @param soundFilePath the file path of the damage sound.
     */
    private void loadDamageSound(String key, String soundFilePath) {
        AudioClip audioClip = new AudioClip(getClass().getResource(soundFilePath).toString());
        damageSounds.put(key, audioClip);
    }

    /**
     * Plays the background music associated with the given key.
     *
     * @param key the key of the background music to play.
     */
    public void playBackgroundMusic(String key) {
        MediaPlayer player = musicPlayers.get(key);
        if (player != null) {
            stopAllBackgroundMusic(); // Stop any currently playing music
            player.stop(); // Ensure this specific player is stopped
            player.seek(player.getStartTime()); // Reset to beginning
            player.play();
        }
    }

    /**
     * Stops all background music.
     */
    public void stopAllBackgroundMusic() {
        for (MediaPlayer player : musicPlayers.values()) {
            player.stop();
            player.seek(player.getStartTime()); // Reset all players to beginning
        }
    }

    /**
     * Plays the shoot sound associated with the given key.
     *
     * @param key the key of the shoot sound to play.
     */
    public void playShootSound(String key) {
        AudioClip clip = shootSounds.get(key);
        if (clip != null) {
            clip.play();
        }
    }

    /**
     * Plays the damage sound associated with the given key.
     *
     * @param key the key of the damage sound to play.
     */
    public void playDamagedSound(String key) {
        AudioClip clip = damageSounds.get(key);
        if (clip != null) {
            clip.play();
        }
    }

    public void playGameOverSound() {
        stopAllBackgroundMusic(); // Stop background music first
        AudioClip gameOverSound = gameStateSounds.get("gameover");
        if (gameOverSound != null) {
            gameOverSound.setVolume(effectsVolume);
            gameOverSound.play();
        }
    }

    public void playVictorySound() {
        stopAllBackgroundMusic(); // Stop background music first
        AudioClip victorySound = gameStateSounds.get("victory");
        if (victorySound != null) {
            victorySound.setVolume(effectsVolume);
            victorySound.play();
        }
    }

    /**
     * Gets the current background volume.
     *
     * @return the current background volume (0.0 to 1.0)
     */
    public double getBackgroundVolume() {
        return backgroundVolume;
    }

    /**
     * Gets the current effects volume.
     *
     * @return the current effects volume (0.0 to 1.0)
     */
    public double getEffectsVolume() {
        return effectsVolume;
    }

    /**
     * Sets the volume for all background music.
     *
     * @param volume the volume to set (0.0 to 1.0)
     */
    public void setBackgroundVolume(double volume) {
        this.backgroundVolume = volume;
        musicPlayers.values().forEach(player -> player.setVolume(volume));
    }

    /**
     * Sets the volume for all sound effects.
     *
     * @param volume the volume to set (0.0 to 1.0)
     */
    public void setEffectsVolume(double volume) {
        this.effectsVolume = volume;
        shootSounds.values().forEach(clip -> clip.setVolume(volume));
        damageSounds.values().forEach(clip -> clip.setVolume(volume));
        gameStateSounds.values().forEach(clip -> clip.setVolume(volume));
    }
}