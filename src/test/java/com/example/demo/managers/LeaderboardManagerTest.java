package com.example.demo.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    @TempDir
    Path tempDir;
    private File tempScoreFile;
    private static final String TEST_SCORES_FILE = "arcade_scores.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary score file for testing
        tempScoreFile = tempDir.resolve(TEST_SCORES_FILE).toFile();
        // Use reflection to set the SCORES_FILE path to our temporary file
        try {
            var field = LeaderboardManager.class.getDeclaredField("SCORES_FILE");
            field.setAccessible(true);
            field.set(null, tempScoreFile.getAbsolutePath());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Could not set up test environment: " + e.getMessage());
        }
    }

    @Test
    void addScore_WhenFileIsEmpty_ShouldCreateFileWithScore() {
        // Act
        LeaderboardManager.addScore(100);

        // Assert
        List<Integer> scores = LeaderboardManager.getTopScores();
        assertEquals(1, scores.size());
        assertEquals(100, scores.get(0));
    }

    @Test
    void addScore_WhenMultipleScores_ShouldMaintainDescendingOrder() {
        // Act
        LeaderboardManager.addScore(100);
        LeaderboardManager.addScore(200);
        LeaderboardManager.addScore(150);

        // Assert
        List<Integer> scores = LeaderboardManager.getTopScores();
        assertEquals(3, scores.size());
        assertEquals(Arrays.asList(200, 150, 100), scores);
    }

    @Test
    void addScore_WhenMoreThanMaxScores_ShouldKeepOnlyTopTen() throws IOException {
        // Arrange
        for (int i = 1; i <= 15; i++) {
            LeaderboardManager.addScore(i);
        }

        // Assert
        List<Integer> scores = LeaderboardManager.getTopScores();
        assertEquals(10, scores.size());
        assertEquals(15, scores.get(0)); // Highest score should be 15
        assertEquals(6, scores.get(9));  // Lowest score should be 6
    }

    @Test
    void getTopScores_WhenFileDoesNotExist_ShouldReturnEmptyList() {
        // Arrange
        tempScoreFile.delete();

        // Act
        List<Integer> scores = LeaderboardManager.getTopScores();

        // Assert
        assertTrue(scores.isEmpty());
    }

    @Test
    void getTopScores_WhenFileExists_ShouldReturnScoresInDescendingOrder() throws IOException {
        // Arrange
        List<String> testScores = Arrays.asList("100", "200", "150");
        Files.write(tempScoreFile.toPath(), testScores);

        // Act
        List<Integer> scores = LeaderboardManager.getTopScores();

        // Assert
        assertEquals(3, scores.size());
        assertEquals(Arrays.asList(200, 150, 100), scores);
    }

    @Test
    void addScore_WhenFileIsCorrupted_ShouldHandleGracefully() throws IOException {
        // Arrange
        Files.write(tempScoreFile.toPath(), Arrays.asList("100", "invalid", "150"));

        // Act & Assert
        assertDoesNotThrow(() -> {
            LeaderboardManager.addScore(125);
            List<Integer> scores = LeaderboardManager.getTopScores();
            assertTrue(scores.contains(125));
        });
    }

    @Test
    void addScore_WhenDuplicateScores_ShouldKeepDuplicates() {
        // Act
        LeaderboardManager.addScore(100);
        LeaderboardManager.addScore(100);

        // Assert
        List<Integer> scores = LeaderboardManager.getTopScores();
        assertEquals(2, scores.size());
        assertEquals(100, scores.get(0));
        assertEquals(100, scores.get(1));
    }
}