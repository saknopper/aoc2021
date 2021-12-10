package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 extends Day
{
    private static final Map<String, String> VALID_CHUNK_PAIRS = Map.of("(", ")", "[", "]", "{", "}", "<", ">");

    private static final Map<String, Integer> ILLEGAL_CHAR_SCORES = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);
    private static final Map<String, Integer> AUTOCOMPLETE_CHAR_SCORES = Map.of("(", 1, "[", 2, "{", 3, "<", 4);

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day10.txt").toURI());
        int points = Files.readAllLines(path).stream().map(l -> {
            Deque<String> openings = new ArrayDeque<>();
            for (var ch : l.toCharArray()) {
                String token = String.valueOf(ch);
                if (VALID_CHUNK_PAIRS.keySet().contains(token))
                    openings.push(token);
                else {
                    String openingToken = openings.pop();
                    if (!VALID_CHUNK_PAIRS.get(openingToken).equals(token)) {
                        return ILLEGAL_CHAR_SCORES.get(token);
                    }
                }
            }

            return Integer.valueOf(0);
        }).mapToInt(Integer::intValue).sum();

        return String.valueOf(points);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day10.txt").toURI());
        List<Long> scores = Files.readAllLines(path).stream().map(l -> {
            Deque<String> openings = new ArrayDeque<>();
            for (var ch : l.toCharArray()) {
                String token = String.valueOf(ch);
                if (VALID_CHUNK_PAIRS.keySet().contains(token))
                    openings.push(token);
                else {
                    String openingToken = openings.pop();
                    if (!VALID_CHUNK_PAIRS.get(openingToken).equals(token))
                        return Long.valueOf(-1l);
                }
            }

            long score = 0l;
            String token;
            while ((token = openings.pollFirst()) != null) {
                score *= 5;
                score += AUTOCOMPLETE_CHAR_SCORES.get(token);
            }

            return Long.valueOf(score);
        }).filter(score -> !Long.valueOf(-1l).equals(score)).collect(Collectors.toList());

        Collections.sort(scores);

        return String.valueOf(scores.get(scores.size() / 2));
    }
}
