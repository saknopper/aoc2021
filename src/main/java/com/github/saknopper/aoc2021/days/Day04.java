package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day04 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day04.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        Deque<Integer> numbers = new ArrayDeque<>(Arrays.stream(lines.get(0).split(",")).map(Integer::valueOf).toList());
        List<Board> boards = new ArrayList<>();

        lines.remove(0);
        lines = lines.stream().filter(l -> !l.isBlank()).toList();
        for (int i = 0; i < lines.size(); i += 5) {
            List<String> boardInput = lines.subList(i, i + 5);
            boards.add(new Board(boardInput));
        }

        List<Integer> unmarkedNumbersOfWinningBoard = List.of();
        Integer nr;
        while ((nr = numbers.pollFirst()) != null) {
            for (var b : boards)
                b.tryToMarkNumber(nr);

            Optional<Board> boardOpt = boards.stream().filter(Board::hasWon).findFirst();
            if (boardOpt.isPresent()) {
                unmarkedNumbersOfWinningBoard = boardOpt.get().getUnmarkedNumbers();
                break;
            }
        }

        Integer sum = unmarkedNumbersOfWinningBoard.stream().collect(Collectors.summingInt(Integer::intValue));

        return String.valueOf(nr * sum);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day04.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        Deque<Integer> numbers = new ArrayDeque<>(Arrays.stream(lines.get(0).split(",")).map(Integer::valueOf).toList());
        List<Board> boards = new ArrayList<>();

        lines.remove(0);
        lines = lines.stream().filter(l -> !l.isBlank()).toList();
        for (int i = 0; i < lines.size(); i += 5) {
            List<String> boardInput = lines.subList(i, i + 5);
            boards.add(new Board(boardInput));
        }

        List<Integer> unmarkedNumbersOfWinningBoard = List.of();
        Integer nr;
        while ((nr = numbers.pollFirst()) != null) {
            for (var b : boards)
                b.tryToMarkNumber(nr);

            List<Board> list = boards.stream().filter(Board::hasWon).toList();
            if (!list.isEmpty()) {
                if (boards.size() == 1) {
                    unmarkedNumbersOfWinningBoard = list.get(0).getUnmarkedNumbers();
                    break;
                } else {
                    boards.removeAll(list);
                }
            }
        }

        Integer sum = unmarkedNumbersOfWinningBoard.stream().collect(Collectors.summingInt(Integer::intValue));

        return String.valueOf(nr * sum);
    }

    class Board
    {
        private final List<List<BoardPosition>> grid = new ArrayList<>();

        public Board(List<String> boardInput) {
            boardInput.forEach(line -> {
                var row = Arrays.stream(line.split("\\s+")).filter(nr -> !nr.isBlank())
                        .map(nr -> new BoardPosition(Integer.valueOf(nr), false)).collect(Collectors.toList());
                grid.add(row);
            });
        }

        public void tryToMarkNumber(Integer nr) {
            grid.forEach(row -> row.replaceAll(pos -> {
                if (pos.number.equals(nr))
                    return new BoardPosition(pos.number, true);
                else
                    return new BoardPosition(pos.number, pos.marked);
            }));
        }

        public boolean hasWon() {
            for (var row : grid) {
                if (row.stream().filter(pos -> !pos.marked).count() == 0l)
                    return true;
            }

            for (int col = 0; col < grid.get(0).size(); col++) {
                final int colFinal = col;
                if (grid.stream().filter(row -> !row.get(colFinal).marked).count() == 0l)
                    return true;
            }

            return false;
        }

        public List<Integer> getUnmarkedNumbers() {
            List<Integer> unmarked = new ArrayList<>();
            grid.forEach(row -> unmarked.addAll(row.stream().filter(pos -> !pos.marked).map(pos -> pos.number).toList()));

            return unmarked;
        }
    }

    record BoardPosition(Integer number, boolean marked) {}
}
