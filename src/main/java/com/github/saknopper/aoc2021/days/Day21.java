package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day21 extends Day
{
    private static final Pattern PLAYER_PATTERN = Pattern.compile("Player (\\d+) starting position: (\\d+)");

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day21.txt").toURI());
        List<Player> players = Files.readAllLines(path).stream().map(Day21::createPlayer).collect(Collectors.toList());

        int deterministicDice = 0;

        int diceRolls = 0;
        Player winner = null;
        while (winner == null) {
            for (var p : players) {
                int sum = 0;
                for (int roll = 0; roll < 3; roll++) {
                    diceRolls++;

                    deterministicDice++;
                    deterministicDice = (deterministicDice - 1) % 100 + 1;

                    sum += deterministicDice;
                }

                p.position += sum;
                p.position = (p.position - 1) % 10 + 1;
                p.score += p.position;

                if (p.score >= 1000) {
                    winner = p;
                    break;
                }
            }
        }

        players.remove(winner);

        return String.valueOf(players.get(0).score * diceRolls);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day21.txt").toURI());
        final List<Player> players = Files.readAllLines(path).stream().map(Day21::createPlayer).toList();
        final Map<Integer, Integer> winRollMultipliers = Map.of(9, 1, 8, 3, 7, 6, 6, 7, 5, 6, 4, 3, 3, 1);

        long winsPlayer1 = calculateWinsForPlayer(players, 1, winRollMultipliers);
        long winsPlayer2 = calculateWinsForPlayer(players, 2, winRollMultipliers);

        return String.valueOf(Math.max(winsPlayer1, winsPlayer2));
    }

    private static long calculateWinsForPlayer(List<Player> players, int player, Map<Integer, Integer> winRollMultipliers) {
        var p1Position = players.get(0).position;
        var p2Position = players.get(1).position;
        long wins = 0L;

        for (var entry : winRollMultipliers.entrySet())
            wins += diceRoll(p1Position, p2Position, 0, 0, entry.getKey(), true, player, winRollMultipliers) * entry.getValue();

        return wins;
    }

    private static long diceRoll(int p1Position, int p2Position, int p1Score, int p2Score, int roll, boolean p1Turn, int player,
            Map<Integer, Integer> winRollMultipliers) {
        long wins = 0L;

        if (p1Turn) {
            p1Position += roll;
            p1Score += (p1Position - 1) % 10 + 1;
            if (p1Score >= 21)
                return player == 1 ? 1 : 0;
        } else {
            p2Position += roll;
            p2Score += (p2Position - 1) % 10 + 1;
            if (p2Score >= 21)
                return player != 1 ? 1 : 0;
        }

        p1Turn = !p1Turn;

        for (var entry : winRollMultipliers.entrySet())
            wins += diceRoll(p1Position, p2Position, p1Score, p2Score, entry.getKey(), p1Turn, player, winRollMultipliers)
                    * entry.getValue();

        return wins;
    }

    private static Player createPlayer(String input) {
        Matcher matcher = PLAYER_PATTERN.matcher(input);
        if (matcher.find())
            return new Player(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));

        return null;
    }

    private static final class Player
    {
        public final int id;
        public int position;
        public int score;

        public Player(int id, int position) {
            super();

            this.id = id;
            this.position = position;
            this.score = 0;
        }

        @Override
        public String toString() {
            return "Player [id=" + id + ", position=" + position + ", score=" + score + "]";
        }
    }
}
