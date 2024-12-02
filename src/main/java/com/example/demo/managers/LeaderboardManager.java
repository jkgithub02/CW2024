package com.example.demo.managers;

import java.io.*;
import java.util.*;

/**
 * Manages the leaderboard for the game, including loading and saving scores.
 */
public class LeaderboardManager {
    private static final String SCORES_FILE = "arcade_scores.txt";
    private static final int MAX_SCORES = 10;

    /**
     * Adds a new score to the leaderboard.
     *
     * @param score the score to add.
     */
    public static void addScore(int score) {
        List<Integer> scores = loadScores();
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder());

        // Keep only top scores
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }

        saveScores(scores);
    }

    /**
     * Retrieves the top scores from the leaderboard.
     *
     * @return a list of the top scores.
     */
    public static List<Integer> getTopScores() {
        return loadScores();
    }

    /**
     * Loads the scores from the scores file.
     *
     * @return a list of scores.
     */
    private static List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            // If file doesn't exist yet, return empty list
            return scores;
        }
        return scores;
    }

    /**
     * Saves the scores to the scores file.
     *
     * @param scores the list of scores to save.
     */
    private static void saveScores(List<Integer> scores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE))) {
            for (Integer score : scores) {
                writer.println(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}