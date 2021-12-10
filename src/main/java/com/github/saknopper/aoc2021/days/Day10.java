package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Day10 extends Day
{
    private static final Map<String, String> VALID_CHUNK_PAIRS = Map.of("(", ")", "[", "]", "{", "}", "<", ">");

    private static final Map<String, Integer> ILLEGAL_CHAR_SCORES = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);
    private static final Map<String, Integer> AUTOCOMPLETE_CHAR_SCORES = Map.of("(", 1, "[", 2, "{", 3, "<", 4);

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day10.txt").toURI());

        long points = 0l;
        for (var l : Files.readAllLines(path)) {
            Deque<String> openings = new ArrayDeque<>();
            for (var ch : l.toCharArray()) {
                String token = String.valueOf(ch);
                if (VALID_CHUNK_PAIRS.keySet().contains(token))
                    openings.push(token);
                else if (VALID_CHUNK_PAIRS.values().contains(token)) {
                    String openingToken = openings.pop();
                    if (!VALID_CHUNK_PAIRS.get(openingToken).equals(token)) {
                        points += ILLEGAL_CHAR_SCORES.get(token);
                        break;
                    }
                }
            }
        }

        return String.valueOf(points);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        List<Long> scores = new ArrayList<>();

        Path path = Paths.get(getClass().getClassLoader().getResource("day10.txt").toURI());
        List<String> incompleteLines = Files.readAllLines(path).stream().filter(Day10::isNotCorruptedLine).toList();
        for (var l : incompleteLines) {
            Deque<String> openings = new ArrayDeque<>();
            for (var ch : l.toCharArray()) {
                String token = String.valueOf(ch);
                if (VALID_CHUNK_PAIRS.keySet().contains(token))
                    openings.push(token);
                else
                    openings.pop();
            }

            long score = 0l;
            String token;
            while ((token = openings.pollFirst()) != null) {
                score *= 5;
                score += AUTOCOMPLETE_CHAR_SCORES.get(token);
            }

            scores.add(score);
        }

        Collections.sort(scores);

        return String.valueOf(scores.get(scores.size() / 2));
    }

    private static boolean isNotCorruptedLine(String l) {
        Deque<String> openings = new ArrayDeque<>();
        for (var ch : l.toCharArray()) {
            String token = String.valueOf(ch);
            if (VALID_CHUNK_PAIRS.keySet().contains(token))
                openings.push(token);
            else if (VALID_CHUNK_PAIRS.values().contains(token)) {
                String openingToken = openings.pop();
                if (!VALID_CHUNK_PAIRS.get(openingToken).equals(token))
                    return false;
            }
        }

        return true;
    }
}
