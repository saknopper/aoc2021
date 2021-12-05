package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 extends Day
{
    private static final Pattern SEGMENT_PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day05.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        List<Segment> segments = parseSegment(lines);

        int totalMaxX = Math.max(segments.stream().mapToInt(s -> s.x1).max().orElseThrow(NoSuchElementException::new),
                                 segments.stream().mapToInt(s -> s.x2).max().orElseThrow(NoSuchElementException::new));
        int totalMaxY = Math.max(segments.stream().mapToInt(s -> s.y1).max().orElseThrow(NoSuchElementException::new),
                                 segments.stream().mapToInt(s -> s.y2).max().orElseThrow(NoSuchElementException::new));

        int[][] grid = new int[totalMaxY + 1][totalMaxX + 1];
        segments.stream().filter(seg -> seg.x1 == seg.x2 || seg.y1 == seg.y2).forEach(seg -> {
            if (seg.x1 == seg.x2) {
                int minY = Math.min(seg.y1, seg.y2);
                int maxY = Math.max(seg.y1, seg.y2);
                for (int i = minY; i <= maxY; i++)
                    grid[i][seg.x1]++;
            } else if (seg.y1 == seg.y2) {
                int minX = Math.min(seg.x1, seg.x2);
                int maxX = Math.max(seg.x1, seg.x2);
                for (int i = minX; i <= maxX; i++)
                    grid[seg.y1][i]++;
            }
        });

        return String.valueOf(Arrays.stream(grid).flatMapToInt(Arrays::stream).filter(pos -> pos >= 2).count());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day05.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        List<Segment> segments = parseSegment(lines);

        int totalMaxX = Math.max(segments.stream().mapToInt(s -> s.x1).max().orElseThrow(NoSuchElementException::new),
                                 segments.stream().mapToInt(s -> s.x2).max().orElseThrow(NoSuchElementException::new));
        int totalMaxY = Math.max(segments.stream().mapToInt(s -> s.y1).max().orElseThrow(NoSuchElementException::new),
                                 segments.stream().mapToInt(s -> s.y2).max().orElseThrow(NoSuchElementException::new));

        int[][] grid = new int[totalMaxY + 1][totalMaxX + 1];
        segments.stream().forEach(seg -> {
            // TODO Simplify with deltaX and deltaY variables to avoid this many conditionals and for loops
            if (seg.x1 == seg.x2) {
                int minY = Math.min(seg.y1, seg.y2);
                int maxY = Math.max(seg.y1, seg.y2);
                for (int i = minY; i <= maxY; i++)
                    grid[i][seg.x1]++;
            } else if (seg.y1 == seg.y2) {
                int minX = Math.min(seg.x1, seg.x2);
                int maxX = Math.max(seg.x1, seg.x2);
                for (int i = minX; i <= maxX; i++)
                    grid[seg.y1][i]++;
            } else {
                if (seg.y1 < seg.y2) {
                    if (seg.x1 < seg.x2)
                        for (int y = seg.y1, x = seg.x1; y <= seg.y2; y++, x++)
                            grid[y][x]++;
                    else
                        for (int y = seg.y1, x = seg.x1; y <= seg.y2; y++, x--)
                            grid[y][x]++;
                } else {
                    if (seg.x1 < seg.x2)
                        for (int y = seg.y1, x = seg.x1; y >= seg.y2; y--, x++)
                            grid[y][x]++;
                    else
                        for (int y = seg.y1, x = seg.x1; y >= seg.y2; y--, x--)
                            grid[y][x]++;
                }
            }
        });

        return String.valueOf(Arrays.stream(grid).flatMapToInt(Arrays::stream).filter(pos -> pos >= 2).count());
    }

    private List<Segment> parseSegment(List<String> lines) {
        return lines.stream().map(l -> {
            final Matcher matcher = SEGMENT_PATTERN.matcher(l);
            matcher.find();
            final MatchResult mr = matcher.toMatchResult();

            return new Segment(Integer.valueOf(mr.group(1)), Integer.valueOf(mr.group(2)), Integer.valueOf(mr.group(3)),
                               Integer.valueOf(mr.group(4)));
        }).toList();
    }

    record Segment(int x1, int y1, int x2, int y2) {}
}
