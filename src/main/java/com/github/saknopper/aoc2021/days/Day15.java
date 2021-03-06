package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Splitter;

public class Day15 extends Day
{
    private static final int[][] SURROUNDING_POSITIONS = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day15.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        int maxHeight = lines.size();
        int maxWidth = lines.get(0).length();

        int[][] grid = new int[maxHeight][maxWidth];
        for (int y = 0; y < maxHeight; y++)
            for (int x = 0; x < maxWidth; x++)
                grid[y][x] = Integer.parseInt(lines.get(y).subSequence(x, x + 1), 0, 1, 10);

        return String.valueOf(calculateSafestPath(grid, maxHeight, maxWidth));
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day15.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        int originalHeight = lines.size();
        int originalWidth = lines.get(0).length();

        int timesToEnlarge = 5;

        final List<List<Integer>> tmpGrid = new ArrayList<>();
        lines.stream().forEach(l -> {
            List<Integer> row = new ArrayList<>();
            Splitter.fixedLength(1).splitToStream(l).map(Integer::valueOf).forEach(row::add);
            tmpGrid.add(row);
        });

        for (int step = 1; step < timesToEnlarge; step++)
            for (var row : tmpGrid)
                for (int i = 0; i < originalWidth; i++)
                    row.add((row.get(i) + step - 1) % 9 + 1);

        for (int step = 1; step < timesToEnlarge; step++)
            for (int row = 0; row < originalHeight; row++) {
                List<Integer> newRow = new ArrayList<>(tmpGrid.get(row));
                int currentStep = step;
                newRow.replaceAll(value -> (value + currentStep - 1) % 9 + 1);
                tmpGrid.add(newRow);
            }

        int maxHeight = tmpGrid.size();
        int maxWidth = tmpGrid.get(0).size();

        int[][] grid = new int[maxHeight][maxWidth];
        for (int y = 0; y < maxHeight; y++)
            for (int x = 0; x < maxWidth; x++)
                grid[y][x] = tmpGrid.get(y).get(x);

        return String.valueOf(calculateSafestPath(grid, maxHeight, maxWidth));
    }

    private int calculateSafestPath(int[][] grid, int maxHeight, int maxWidth) {
        int[][] distanceMap = new int[maxHeight][maxWidth];
        Arrays.stream(distanceMap).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));
        distanceMap[0][0] = 0;

        int[][] originMap = new int[maxHeight][maxWidth];

        boolean[][] visitedMap = new boolean[maxHeight][maxWidth];

        int x = 0;
        int y = 0;

        while (true) {
            visitedMap[y][x] = true;

            if (x == maxWidth - 1 && y == maxHeight - 1)
                break;

            for (var pos : SURROUNDING_POSITIONS) {
                int destY = y + pos[0];
                int destX = x + pos[1];
                if (destY >= 0 && destY < maxHeight && destX >= 0 && destX < maxWidth) {
                    if (!visitedMap[destY][destX] && grid[destY][destX] + distanceMap[y][x] < distanceMap[destY][destX]) {
                        distanceMap[destY][destX] = grid[destY][destX] + distanceMap[y][x];
                        originMap[destY][destX] = (y * maxWidth) + x;
                    }
                }
            }

            // Now search within the newly explored elements for the "lowest risk" path
            int currentBestValue = Integer.MAX_VALUE;
            for (int i = 0; i < maxHeight; i++)
                for (int j = 0; j < maxWidth; j++)
                    if (!visitedMap[i][j] && distanceMap[i][j] < currentBestValue) {
                        currentBestValue = distanceMap[i][j];
                        y = i;
                        x = j;
                    }
        }

        // Start backtracking through originMap
        int riskSum = 0;

        y = maxHeight - 1;
        x = maxWidth - 1;
        while (x > 0 || y > 0) {
            riskSum += grid[y][x];

            int y2 = originMap[y][x] / maxWidth;
            int x2 = originMap[y][x] % maxWidth;

            y = y2;
            x = x2;
        }

        return riskSum;
    }
}
