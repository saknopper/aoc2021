package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13 extends Day
{
    private static final Pattern MARK_PATTERN = Pattern.compile("\\d+,\\d+");
    private static final Pattern FOLD_PATTERN = Pattern.compile("fold along ([xy]){1}=(\\d+)");

    @Override
    public String getAnswerPartOne() throws Exception {
        Set<Mark> markedPositions = parseMarkedPositions();
        List<Fold> folds = parseFolds();
        performFold(markedPositions, folds.get(0));

        return String.valueOf(markedPositions.size());
    }

	@Override
    public String getAnswerPartTwo() throws Exception {
        Set<Mark> markedPositions = parseMarkedPositions();
        List<Fold> folds = parseFolds();

        for (var fold : folds)
            performFold(markedPositions, fold);

        int height = markedPositions.stream().map(mark -> Integer.valueOf(mark.y)).mapToInt(Integer::intValue).max().orElseThrow() + 1;
        int width = markedPositions.stream().map(mark -> Integer.valueOf(mark.x)).mapToInt(Integer::intValue).max().orElseThrow() + 1;
        String[][] grid = new String[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                grid[y][x] = ".";

        for (var mark : markedPositions)
            grid[mark.y][mark.x] = "#";

        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                sb.append(grid[y][x]);
            sb.append("\n");
        }

        return sb.toString();
    }

    private void performFold(Set<Mark> markedPositions, Fold fold) {
        List<Mark> toReposition;
        if (fold.direction == FoldDirection.UP) {
            markedPositions.removeIf(mark -> mark.y == fold.position);
            toReposition = markedPositions.stream().filter(mark -> mark.y > fold.position).toList();
        } else {
            markedPositions.removeIf(mark -> mark.x == fold.position);
            toReposition = markedPositions.stream().filter(mark -> mark.x > fold.position).toList();
        }

        for (var mark : toReposition) {
            markedPositions.remove(mark);
            if (fold.direction == FoldDirection.UP)
                markedPositions.add(new Mark(mark.x, fold.position - (mark.y - fold.position)));
            else
                markedPositions.add(new Mark(fold.position - (mark.x - fold.position), mark.y));
        }
    }

    private Set<Mark> parseMarkedPositions() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day13.txt").toURI());
        return Files.readAllLines(path).stream().filter(l -> MARK_PATTERN.matcher(l).matches()).map(l -> {
            var splitted = l.split(",");
            return new Mark(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
        }).collect(Collectors.toSet());
	}

    private List<Fold> parseFolds() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day13.txt").toURI());
        return Files.readAllLines(path).stream().filter(l -> FOLD_PATTERN.matcher(l).matches()).map(l -> {
            var matcher = FOLD_PATTERN.matcher(l);
            matcher.find();
            FoldDirection direction = matcher.group(1).equals("y") ? FoldDirection.UP : FoldDirection.LEFT;
            int position = Integer.parseInt(matcher.group(2));
            return new Fold(direction, position);
        }).toList();
    }

    record Mark(int x, int y) {}

    record Fold(FoldDirection direction, int position) {}

    enum FoldDirection { UP, LEFT }
}
