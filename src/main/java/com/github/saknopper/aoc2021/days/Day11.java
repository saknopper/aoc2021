package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day11 extends Day
{
    private static final List<List<Integer>> SURROUNDING_POSITIONS = List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1),
                                                                             List.of(-1, -1), List.of(-1, 1), List.of(1, -1),
                                                                             List.of(1, 1));

    @Override
    public String getAnswerPartOne() throws Exception {
        List<List<Integer>> grid = parseInputToGrid();

        long sumOfFlashes = IntStream.rangeClosed(1, 100).boxed().map(step -> {
            increaseLevelByOne(grid);
            return checkLevelsAndPerformFlash(grid);
        }).mapToLong(Long::longValue).sum();

        return String.valueOf(sumOfFlashes);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        List<List<Integer>> grid = parseInputToGrid();

        return String.valueOf(IntStream.rangeClosed(1, 1000).boxed().map(step -> {
            increaseLevelByOne(grid);
            return checkLevelsAndPerformFlash(grid) == 100 ? Long.valueOf(step) : 0l;
        }).filter(step -> !Long.valueOf(0l).equals(step)).mapToLong(Long::longValue).findFirst().orElseThrow());
    }

    private List<List<Integer>> parseInputToGrid() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day11.txt").toURI());
        List<List<Integer>> grid = new ArrayList<>();
        Files.readAllLines(path).forEach(l -> {
            var row = new ArrayList<Integer>();
            l.chars().mapToObj(i -> (char) i).map(String::valueOf).map(Integer::valueOf).forEach(row::add);
            grid.add(row);
        });

        return grid;
    }

    private void increaseLevelByOne(List<List<Integer>> grid) {
        grid.stream().forEach(row -> row.replaceAll(level -> Integer.valueOf(level + 1)));
    }

    private long checkLevelsAndPerformFlash(List<List<Integer>> grid) {
        int height = grid.size();
        int width = grid.get(0).size();

        long flashes = 0l;

        List<List<Integer>> positionsToTakeActionForSurroundings = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid.get(y).get(x) == 10) {
                    flashes++;
                    grid.get(y).set(x, 0);
                    positionsToTakeActionForSurroundings.add(List.of(y, x));
                }
            }
        }

        for (var sourcePosition : positionsToTakeActionForSurroundings)
            flashes += increaseLevelsAndPerformFlashForSurroundings(grid, sourcePosition.get(0), sourcePosition.get(1));

        return flashes;
    }

    private long increaseLevelsAndPerformFlashForSurroundings(List<List<Integer>> grid, int y, int x) {
        long flashes = 0l;

        List<List<Integer>> positionsToTakeActionForSurroundings = new ArrayList<>();

        for (var pos : SURROUNDING_POSITIONS) {
            int surPosY = y + pos.get(0);
            int surPosX = x + pos.get(1);
            try {
                Integer level = grid.get(surPosY).get(surPosX);
                if (level != 0) {
                    Integer increasedLevel = level + 1;
                    grid.get(surPosY).set(surPosX, increasedLevel);
                    if (increasedLevel == 10) {
                        flashes++;
                        grid.get(surPosY).set(surPosX, 0);
                        positionsToTakeActionForSurroundings.add(List.of(surPosY, surPosX));
                    }
                }
            } catch (IndexOutOfBoundsException ignore) { /* ignore */ }
        }

        for (var sourcePosition : positionsToTakeActionForSurroundings)
            flashes += increaseLevelsAndPerformFlashForSurroundings(grid, sourcePosition.get(0), sourcePosition.get(1));

        return flashes;
    }
}
