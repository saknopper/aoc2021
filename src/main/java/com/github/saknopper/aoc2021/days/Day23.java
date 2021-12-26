/*
 * Based on: https://topaz.github.io/paste/#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
 *       By: u/VeeArr on reddit
 */

package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day23 extends Day
{
    private static final int[] MOVE_COSTS = { 1, 10, 100, 1000 };
    private static int numEachType = 0;

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day23.txt").toURI());

        return String.valueOf(solvePosition(Files.readAllLines(path)));
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day23b.txt").toURI());

        return String.valueOf(solvePosition(Files.readAllLines(path)));
    }

    private static long solvePosition(List<String> input) {
        numEachType = input.size() - 3;
        int[] startingPositions = new int[totalUnits()];
        for (int i = 0; i < numEachType; i++) {
            String line = input.get(i + 2);
            for (int j = 0; j < 4; j++) {
                char c = line.charAt(2 * j + 3);
                int unit = (c - 'A') * numEachType;
                while (startingPositions[unit] != 0)
                    unit++;

                startingPositions[unit] = 4 * i + j + 7;
            }
        }

        return dijkstra(startingPositions);
    }

    private static long dijkstra(int[] startingPositions) {
        Queue<GameState> q = new PriorityQueue<>(Comparator.comparingLong(GameState::cost));
        q.add(new GameState(startingPositions, 0));

        long best = Long.MAX_VALUE;
        Map<String, Long> alreadyProcessed = new HashMap<>();
        while (!q.isEmpty()) {
            GameState toProcess = q.poll();
            if (toProcess.cost >= best)
                break;

            for (int unit = 0; unit < totalUnits(); unit++) {
                boolean[] validPos = findValidPositions(toProcess.positions, unit);
                for (int i = 0; i < validPos.length; i++) {
                    if (!validPos[i])
                        continue;

                    int price = calcPrice(unit, toProcess.positions[unit], i);
                    GameState next = toProcess.moveUnit(unit, i, price);
                    if (next.isComplete()) {
                        best = Math.min(best, next.cost);
                    } else {
                        String repr = next.getRepr();
                        if (next.cost < alreadyProcessed.getOrDefault(repr, Long.MAX_VALUE)) {
                            alreadyProcessed.put(repr, next.cost);
                            q.add(next);
                        }
                    }
                }
            }
        }

        return best;
    }

    private static int getType(int unit) {
        if (unit == -1)
            return -1;

        return unit / numEachType;
    }

    private static int totalUnits() {
        return 4 * numEachType;
    }

    private static boolean[] findValidPositions(int[] positions, int unit) {
        if (positions[unit] < 7)
            return findValidRoomPositions(positions, unit);

        return findValidHallPositions(positions, unit);
    }

    private static boolean[] findValidHallPositions(int[] positions, int unit) {
        int[] occupied = new int[totalUnits() + 7];
        for (int i = 0; i < totalUnits() + 7; i++)
            occupied[i] = -1;

        for (int i = 0; i < totalUnits(); i++)
            occupied[positions[i]] = i;

        boolean[] rv = new boolean[7];

        int cPos = positions[unit];
        int type = getType(unit);
        for (int i = cPos - 4; i > 6; i -= 4)
            if (occupied[i] > -1)
                return rv;

        if ((cPos + 1) % 4 == type) {
            boolean gottaMove = false;
            for (int i = cPos + 4; i < totalUnits() + 7; i += 4) {
                if (getType(occupied[i]) != type) {
                    gottaMove = true;
                    break;
                }
            }

            if (!gottaMove)
                return rv;
        }

        int effPos = cPos;
        while (effPos > 10)
            effPos -= 4;

        for (int i = 0; i < 7; i++)
            if (occupied[i] == -1 && checkHallwayClear(i, effPos, occupied))
                rv[i] = true;

        return rv;
    }

    private static boolean[] findValidRoomPositions(int[] positions, int unit) {
        int[] occupied = new int[totalUnits() + 7];
        for (int i = 0; i < totalUnits() + 7; i++)
            occupied[i] = -1;

        for (int i = 0; i < totalUnits(); i++)
            occupied[positions[i]] = i;

        boolean[] rv = new boolean[totalUnits() + 7];

        int cPos = positions[unit];
        int type = getType(unit);
        int room1 = type + 7;

        if (!checkHallwayClear(cPos, room1, occupied)) {
            return rv;
        }
        int tgt = room1;
        for (int i = 0; i < numEachType; i++) {
            if (occupied[room1 + 4 * i] == -1)
                tgt = room1 + 4 * i;
            else if (getType(occupied[room1 + 4 * i]) != type)
                return rv;
        }

        rv[tgt] = true;

        return rv;
    }

    private static boolean checkHallwayClear(int hallPos, int roomPos, int[] occupied) {
        int min = Math.min(hallPos + 1, roomPos - 5);
        int max = Math.max(hallPos - 1, roomPos - 6);

        for (int i = min; i <= max; i++)
            if (occupied[i] != -1)
                return false;

        return true;
    }

    private static int calcPrice(int unit, int from, int to) {
        if (from > to) {
            int tmp = from;
            from = to;
            to = tmp;
        }

        int depth = (to - 3) / 4;
        int tgtHall = ((to + 1) % 4) * 2 + 3;
        int discount = (from == 0 || from == 6) ? 1 : 0;
        int dist = Math.abs(2 * from - tgtHall) + depth - discount;
        int type = getType(unit);

        return MOVE_COSTS[type] * dist;
    }

    private record GameState(int[] positions, long cost) {
        public GameState moveUnit(int unit, int position, int price) {
            int[] newPositions = Arrays.copyOf(positions, positions.length);
            newPositions[unit] = position;

            return new GameState(newPositions, cost + price);
        }

        public boolean isComplete() {
            for (int i = 0; i < positions.length; i++) {
                int type = getType(i);
                if (positions[i] < 7 || (positions[i] + 1) % 4 != type)
                    return false;
            }

            return true;
        }

        public String getRepr() {
            int[] occupied = new int[totalUnits() + 7];
            for (int i = 0; i < totalUnits() + 7; i++)
                occupied[i] = -1;
            for (int i = 0; i < totalUnits(); i++)
                occupied[positions[i]] = i;

            String rv = "";
            for (int i = 0; i < totalUnits() + 7; i++) {
                int type = getType(occupied[i]);
                if (type == -1)
                    rv += "x";
                else
                    rv += type;
            }

            return rv;
        }
    }
}
