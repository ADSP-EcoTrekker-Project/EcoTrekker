package com.ecotrekker.gamification.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RushHourTest {

    private RushHour rushHour;

    @BeforeEach
    public void setUp() {
        rushHour = new RushHour();
    }

    @Test
    public void testApplyRushHourDuringRushHour() {
        // set current time to a rush hour time
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T08:00:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        // assert that the points are reduced during rush hour
        assertEquals(120.0, newPoints);
    }

    @Test
    public void testApplyRushHourOutsideRushHour() {
        // set current time to a non-rush hour time
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T11:00:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        // assert that the points are not reduced outside rush hour
        assertEquals(100.0, newPoints);
    }

    @Test
    public void testApplyRushHourDuringFirstRushHourWindow() {
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T07:30:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        assertEquals(120.0, newPoints);
    }

    @Test
    public void testApplyRushHourDuringSecondRushHourWindow() {
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T17:30:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        assertEquals(120.0, newPoints);
    }

    @Test
    public void testApplyRushHourAtBoundaryOfRushHourWindow() {
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T09:00:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        assertEquals(100.0, newPoints); // Boundary should not be considered rush hour
    }

    @Test
    public void testApplyRushHourOutsideAllRushHourWindows() {
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T12:00:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        assertEquals(100.0, newPoints); // Should not be considered rush hour
    }

    @Test
    public void testApplyRushHourWithOverlappingWindows() {
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T18:30:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        Double newPoints = rushHour.applyRushHour(100.0);

        assertEquals(120.0, newPoints); // Should be considered rush hour despite overlap
    }

    @Test
    public void testApplyRushHourWithInvalidClockConfiguration() {
        // Test applying rush hour factor with an invalid clock configuration
        Clock clock = Clock.fixed(Instant.parse("2024-02-09T08:00:00.00Z"), ZoneId.of("UTC"));
        rushHour.setClock(clock);

        // Simulate setting clock to null
        rushHour.setClock(null);

        // Applying rush hour should throw NullPointerException due to null clock
        assertThrows(NullPointerException.class, () -> rushHour.applyRushHour(100.0));
    }

    @Test
    public void testApplyRushHourWithNullPoints() {
        // Test applying rush hour factor with null points
        assertThrows(NullPointerException.class, () -> rushHour.applyRushHour(null));
    }
}
