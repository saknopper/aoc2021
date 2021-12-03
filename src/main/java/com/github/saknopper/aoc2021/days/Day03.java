package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Day03 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day03.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        final int lineLength = lines.get(0).length();
        final StringBuilder gammaRateSb = new StringBuilder();
        final StringBuilder epsilonRateSb = new StringBuilder();
        IntStream.range(0, lineLength).boxed().forEach(column -> {
            long onesForColumn = lines.stream().filter(line -> line.charAt(column) == '1').count();

            if (onesForColumn > (lines.size() / 2.0d)) {
                gammaRateSb.append('1');
                epsilonRateSb.append('0');
            } else {
                gammaRateSb.append('0');
                epsilonRateSb.append('1');
            }
        });

        return String.valueOf(Integer.parseInt(gammaRateSb.toString(), 2) * Integer.parseInt(epsilonRateSb.toString(), 2));
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day03.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        String oxygenRating = getLastRemainingLineAfterFiltering(List.copyOf(lines), CommonMode.MOST_COMMON);
        String co2Rating = getLastRemainingLineAfterFiltering(List.copyOf(lines), CommonMode.LEAST_COMMON);

        return String.valueOf(Integer.parseInt(oxygenRating, 2) * Integer.parseInt(co2Rating, 2));
    }

    private String getLastRemainingLineAfterFiltering(List<String> input, CommonMode mode) {
        for (int column = 0;; column++) {
            input = filterLinesByCommonValueInColumn(input, column, mode);
            if (input.size() <= 1) {
                return input.get(0);
            }
        }
    }

    private List<String> filterLinesByCommonValueInColumn(List<String> input, int column, CommonMode mode) {
        long onesForColumn = input.stream().filter(line -> line.charAt(column) == '1').count();

        char commonValue;
        if (mode == CommonMode.MOST_COMMON) {
            commonValue = onesForColumn >= (input.size() / 2.0d) ? '1' : '0';
        } else {
            commonValue = onesForColumn >= (input.size() / 2.0d) ? '0' : '1';
        }

        return input.stream().filter(line -> line.charAt(column) == commonValue).toList();
    }

    enum CommonMode {
        LEAST_COMMON, MOST_COMMON
    }
}
