package com.example.demo.managers;

import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private static String SCORES_FILE = "arcade_scores.txt";
    private static final int MAX_SCORES = 10;

    public static void addScore(int score) {
        List<Integer> scores = loadScores();
        scores.add(score);
        // Sort in descending order (highest first)
        Collections.sort(scores, Collections.reverseOrder());

        // Keep only top MAX_SCORES
        if (scores.size() > MAX_SCORES) {
            scores = new ArrayList<>(scores.subList(0, MAX_SCORES));
        }

        saveScores(scores);
    }

    public static List<Integer> getTopScores() {
        List<Integer> scores = loadScores();
        Collections.sort(scores, Collections.reverseOrder());
        return scores;
    }

    private static List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                    continue;
                }
            }
        } catch (IOException e) {
            // If file doesn't exist yet, return empty list
            return scores;
        }
        return scores;
    }

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