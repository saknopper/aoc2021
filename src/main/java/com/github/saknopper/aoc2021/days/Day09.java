package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 extends Day
{
    private static final List<List<Integer>> SURROUNDING_POSITIONS = List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1));

    @Override
    public String getAnswerPartOne() throws Exception {
        List<List<Integer>> grid = parseInputToGrid();

        int height = grid.size();
        int width = grid.get(0).size();

        long sum = 0;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (isLowPoint(grid, y, x))
                   sum += grid.get(y).get(x) + 1;

        return String.valueOf(sum);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        List<List<Integer>> grid = parseInputToGrid();

        int height = grid.size();
        int width = grid.get(0).size();

        List<Integer> basinSizes = new ArrayList<>();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (isLowPoint(grid, y, x))
                   basinSizes.add(getBasinSize(grid, y, x, height, width, new ArrayList<>()));

        Collections.sort(basinSizes);
        int basinCount = basinSizes.size();

        return String.valueOf(basinSizes.get(basinCount - 1) * basinSizes.get(basinCount - 2) * basinSizes.get(basinCount - 3));
    }

    // Based on https://en.wikipedia.org/wiki/Flood_fill
    private static Integer getBasinSize(List<List<Integer>> grid, int y, int x, int height, int width, List<List<Integer>> checked) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0;
        if (grid.get(y).get(x) == 9 || checked.contains(List.of(y, x)))
            return 0;

        checked.add(List.of(y, x));

        Integer count = 1;
        for (var toCheck : SURROUNDING_POSITIONS)
            count += getBasinSize(grid, y + toCheck.get(0), x + toCheck.get(1), height, width, checked);
 
        return count;
    }

    private static boolean isLowPoint(List<List<Integer>> grid, int yPos, int xPos) {
        int valueOfPoint = grid.get(yPos).get(xPos);

        for (var toCheck : SURROUNDING_POSITIONS) {
            try {
                if (valueOfPoint >= grid.get(yPos + toCheck.get(0)).get(xPos + toCheck.get(1))) {
                    return false;
                }
            } catch (IndexOutOfBoundsException ignore) { }
        }

        return true;
    }

    private List<List<Integer>> parseInputToGrid() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day09.txt").toURI());
        List<List<Integer>> grid = new ArrayList<>();
        Files.readAllLines(path).forEach(l -> {
            var row = new ArrayList<Integer>();
            l.chars().mapToObj(i -> (char)i).map(ch -> ch.toString()).map(Integer::valueOf).forEach(row::add);
            grid.add(row);
        });

        return grid;
    }
}
