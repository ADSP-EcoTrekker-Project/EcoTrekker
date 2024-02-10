package com.ecotrekker.gamification.service;

import com.ecotrekker.gamification.model.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
