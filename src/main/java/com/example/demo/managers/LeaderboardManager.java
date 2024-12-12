package com.example.demo.managers;

import java.io.*;
import java.util.*;

/**
 * Manages the leaderboard for high scores in the game.
 */
public class LeaderboardManager {
    /**
     * The file name for storing scores.
     */
    private static String SCORES_FILE = "arcade_scores.txt";

    /**
     * The maximum number of scores to keep in the leaderboard.
     */
    private static final int MAX_SCORES = 10;

    /**
     * Adds a score to the leaderboard.
     *
     * @param score the score to add.
     */
    public static void addScore(int score) {
        List<Integer> scores = loadScores();
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder());

        if (scores.size() > MAX_SCORES) {
            scores = new ArrayList<>(scores.subList(0, MAX_SCORES));
        }

        saveScores(scores);
    }

    /**
     * Gets the top scores from the leaderboard.
     *
     * @return the list of top scores.
     */
    public static List<Integer> getTopScores() {
        List<Integer> scores = loadScores();
        Collections.sort(scores, Collections.reverseOrder());
        return scores;
    }

    /**
     * Loads scores from the scores file.
     *
     * @return the list of loaded scores.
     */
    private static List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            return scores;
        }
        return scores;
    }

    /**
     * Saves scores to the scores file.
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