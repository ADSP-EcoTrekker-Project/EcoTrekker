package com.ecotrekker.gamification.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import com.ecotrekker.gamification.model.Route;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Contains business logic for applying time based modifiers.
 */
@Service
public class RushHour {
    // define time windows with different factors
    private static final List<TimeWindow> RUSH_HOUR_WINDOWS = new ArrayList<>();

    static {
        // add time windows with start and end times along with rush hour factors
        RUSH_HOUR_WINDOWS.add(new TimeWindow(LocalTime.of(7, 0), LocalTime.of(9, 0), 1.0));
        RUSH_HOUR_WINDOWS.add(new TimeWindow(LocalTime.of(17, 0), LocalTime.of(19, 0), 1.0));
    }

    private static final double NON_RUSH_HOUR_FACTOR = 1.0;

    public Double applyRushHour(Route route, Double points) {
        LocalTime currentTime = LocalTime.now();

        // check if the current time is within any rush hour window
        for (TimeWindow window : RUSH_HOUR_WINDOWS) {
            if (window.isInTimeWindow(currentTime)) {
                return points * window.getFactor();
            }
        }

        // If not in any rush hour window, apply non-rush hour factor
        return points * NON_RUSH_HOUR_FACTOR;
    }

    // time window class
    @Getter
    private static class TimeWindow {
        private final LocalTime start;
        private final LocalTime end;
        private final double factor;

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
            return currentTime.isAfter(start) && currentTime.isBefore(end);
        }
    }
}
