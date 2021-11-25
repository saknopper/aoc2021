package aoc2021;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aoc2021.days.Day;

public class Application
{
    private static final String PACKAGE_NAME = "aoc2021.days";

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IllegalArgumentException, SecurityException {
        LOG.info("---------------------------------------------");
        LOG.info("Advent of Code 2021");
        LOG.info("---------------------------------------------");
        LOG.info("");

        IntStream.rangeClosed(1, 25).boxed().forEach(day -> {
            String dayNr = String.format("%02d", day);

            try {
                @SuppressWarnings("unchecked")
                Class<? extends Day> clazz = (Class<? extends Day>) Class.forName(PACKAGE_NAME + ".Day" + dayNr);
                Day dayInstance = clazz.getDeclaredConstructor().newInstance();
                LOG.info("Day {} part 1: {}", dayNr, dayInstance.getAnswerPartOne());
                LOG.info("Day {} part 2: {}", dayNr, dayInstance.getAnswerPartTwo());
            } catch (ClassNotFoundException e) {
                /* ignore, will be fixed another day :) */
            } catch (Exception e) {
                LOG.error("Error running day", e);
            }
        });
    }
}
