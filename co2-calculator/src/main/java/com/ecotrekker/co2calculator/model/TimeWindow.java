package com.ecotrekker.co2calculator.model;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class TimeWindow {
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
