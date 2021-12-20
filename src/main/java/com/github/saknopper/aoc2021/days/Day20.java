package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day20 extends Day
{
    private static final int[][] POSITIONS = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day20.txt").toURI());
        List<String> lines = Files.readAllLines(path).stream().filter(Predicate.not(String::isBlank)).collect(Collectors.toList());

        final String imageEnhancer = lines.remove(0);

        return String.valueOf(applyImageEnhancerAmountOfSteps(lines, 2, imageEnhancer));
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day20.txt").toURI());
        List<String> lines = Files.readAllLines(path).stream().filter(Predicate.not(String::isBlank)).collect(Collectors.toList());

        final String imageEnhancer = lines.remove(0);

        return String.valueOf(applyImageEnhancerAmountOfSteps(lines, 50, imageEnhancer));
    }

    private int applyImageEnhancerAmountOfSteps(List<String> lines, int steps, String imageEnhancer) {
        int inputHeight = lines.size();
        int inputWidth = lines.get(0).length();

        int maxHeight = inputHeight + ((steps + steps) * 2);
        int maxWidth = inputWidth + ((steps + steps) * 2);

        int[][] grid = new int[maxHeight][maxWidth];

        for (int y = 0; y < inputHeight; y++)
            for (int x = 0; x < inputWidth; x++)
                grid[y + steps + steps][x + steps + steps] = lines.get(y).substring(x, x + 1).equals("#") ? 1 : 0;

        for (int i = 0; i < steps; i++)
            grid = applyImageEnhancer(grid, maxHeight, maxWidth, imageEnhancer);

        int[][] croppedGrid = new int[inputHeight + (steps * 2)][inputHeight + (steps * 2)];
        for (int y = 0; y < croppedGrid.length; y++)
            for (int x = 0; x < croppedGrid[0].length; x++)
               croppedGrid[y][x] = grid[y + steps][x + steps];

        return Arrays.stream(croppedGrid).flatMapToInt(Arrays::stream).sum();
    }

    private int[][] applyImageEnhancer(int[][] input, int maxHeight, int maxWidth, String enhancer) {
        int[][] grid = new int[maxHeight][maxWidth];
        for (int y = 1; y < maxHeight - 1; y++) {
            for (int x = 1; x < maxWidth - 1; x++) {
                grid[y][x] = getNewValue(input, y, x, enhancer);
            }
        }

        return grid;
    }

    private int getNewValue(int[][] input, int y, int x, String enhancer) {
        StringBuilder sb = new StringBuilder();

        for (var pos : POSITIONS)
            sb.append(input[y + pos[0]][x + pos[1]]);

        int enhancerPos = Integer.valueOf(sb.toString(), 2);

        return enhancer.substring(enhancerPos, enhancerPos + 1).equals("#") ? 1 : 0;
    }
}
