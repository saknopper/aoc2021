package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day25 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day25.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        int maxHeight = lines.size();
        int maxWidth = lines.get(0).length();

        char[][] grid = new char[maxHeight][maxWidth];
        for (int row = 0; row < maxHeight; row++)
            grid[row] = lines.get(row).toCharArray();

        int step = 0;
        GridWithNrOfMoves gridWithNrOfMoves = new GridWithNrOfMoves(grid, Integer.MAX_VALUE);
        for (; gridWithNrOfMoves.nrOfMoves != 0 && step < 1000; step++)
            gridWithNrOfMoves = performMoves(gridWithNrOfMoves, maxHeight, maxWidth);

        return String.valueOf(step);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        return "N/A";
    }

    private GridWithNrOfMoves performMoves(GridWithNrOfMoves gridWithNrOfMoves, int maxHeight, int maxWidth) {
        char[][] newGrid = new char[maxHeight][maxWidth];
        for (var row : newGrid)
            Arrays.fill(row, '.');

        int nrOfMoves = 0;

        // East
        for (int y = 0; y < maxHeight; y++) {
            for (int x = 0; x < maxWidth; x++) {
                if (gridWithNrOfMoves.grid[y][x] == '>') {
                    if (gridWithNrOfMoves.grid[y][(x + 1) % maxWidth] == '.') {
                        newGrid[y][x] = '.';
                        newGrid[y][(x + 1) % maxWidth] = '>';
                        nrOfMoves++;
                    } else {
                        newGrid[y][x] = '>';
                    }
                }
            }
        }

        // South
        for (int y = 0; y < maxHeight; y++) {
            for (int x = 0; x < maxWidth; x++) {
                if (gridWithNrOfMoves.grid[y][x] == 'v') {
                    if (gridWithNrOfMoves.grid[(y + 1) % maxHeight][x] != 'v' && newGrid[(y + 1) % maxHeight][x] != '>') {
                        newGrid[y][x] = '.';
                        newGrid[(y + 1) % maxHeight][x] = 'v';
                        nrOfMoves++;
                    } else {
                        newGrid[y][x] = 'v';
                    }
                }
            }
        }

        return new GridWithNrOfMoves(newGrid, nrOfMoves);
    }

    record GridWithNrOfMoves(char[][] grid, int nrOfMoves) {}
}
