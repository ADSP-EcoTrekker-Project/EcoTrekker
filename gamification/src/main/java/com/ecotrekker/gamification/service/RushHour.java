package com.ecotrekker.gamification.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that applies rush hour factors to the points of a route.
 * The rush hour factors are applied based on the current time and predefined rush hour windows.
 */
@Service
public class RushHour {
    // use system default clock by default
    private Clock clock = Clock.systemDefaultZone();

    // define time windows with different factors
    private static final List<TimeWindow> RUSH_HOUR_WINDOWS = new ArrayList<>();

    static {
        // add time windows with start and end times along with rush hour factors
        RUSH_HOUR_WINDOWS.add(new TimeWindow(LocalTime.of(7, 0), LocalTime.of(9, 0), 1.2));
        RUSH_HOUR_WINDOWS.add(new TimeWindow(LocalTime.of(17, 0), LocalTime.of(19, 0), 1.2));
    }

    private static final double NON_RUSH_HOUR_FACTOR = 1.0;

    /**
     * Applies rush hour factors to the points of a route.
     * The points are multiplied by the rush hour factor if the current time is within a rush hour window.
     * If the current time is not within any rush hour window, the points are multiplied by a non-rush hour factor.
     *
     * @param points The base points for the route.
     * @return The new total points for the route after applying rush hour factors.
     */
    public Double applyRushHour(Double points) {
        LocalTime currentTime = LocalTime.now(clock);

        // check if the current time is within any rush hour window
        for (TimeWindow window : RUSH_HOUR_WINDOWS) {
            if (window.isInTimeWindow(currentTime)) {
                return points * window.getFactor();
            }
        }

        // if not in any rush hour window, apply non-rush hour factor
        return points * NON_RUSH_HOUR_FACTOR;
    }

    /**
     * Sets the clock used to determine the current time.
     * This method is mainly used for testing purposes to control the current time.
     *
     * @param clock The clock to set. This clock will be used to determine the current time.
     */
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Represents a time window with a start time, end time, and a factor.
     * The factor is used to adjust the points of a route if the current time falls within this time window.
     */
    @Getter
    private static class TimeWindow {
        private final LocalTime start;
        private final LocalTime end;
        private final double factor;

        /**
         * Constructs a new TimeWindow with the given start time, end time, and factor.
         *
         * @param start The start time of the time window.
         * @param end The end time of the time window.
         * @param factor The factor to apply during this time window.
         */
        public TimeWindow(LocalTime start, LocalTime end, double factor) {
            this.start = start;
            this.end = end;
            this.factor = factor;
        }

        /**
         * Checks if the given time falls within this time window.
         *
         * @param currentTime the time to check
         * @return true if the time is within this time window, false otherwise
         */
        public boolean isInTimeWindow(LocalTime currentTime) {
            // check if the current time is after the start time and before the end time
            return currentTime.isAfter(start) && currentTime.isBefore(end);
        }
    }
}
