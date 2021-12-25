package com.github.saknopper.aoc2021.utils;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public final class GridUtils
{
    private GridUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> void printGrid(List<List<T>> grid, String delim) {
        grid.stream().forEach(row -> System.out.println(String.join(delim, row.stream().map(String::valueOf).toList())));
    }

    public static void printGrid(char[][] grid, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            StringJoiner sj = new StringJoiner(delim);
            for (int x = 0; x < grid[y].length; x++)
                sj.add(String.valueOf(grid[y][x]));

            sb.append(sj.toString());
            sb.append('\n');
        }

        System.out.println(sb.toString());
    }

    public static void printGrid(int[][] grid, String delim) {
        Arrays.stream(grid).forEach(row -> System.out.println(String.join(delim, Arrays.stream(row).mapToObj(String::valueOf).toList())));
    }
}
