package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTest {
    @Test
    public void testGetters() {
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 13, 12, 21);
        LocalDateTime expected = LocalDateTime.of(2024, 2, 13, 10, 21);
        Date date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(testDate);
        assertEquals(expected, date.getDate());
    }

    @Test
    public void testFormatters() {
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 13, 12, 21);
        Date date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(testDate);
        assertEquals("Tuesday 2/13", date.formatDate());
        assertEquals("Tuesday 2/13 10:21", date.formatDateTime());
    }

    @Test
    public void testAdvanceDate(){
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 13, 12, 21);
        Date actual = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        actual.setDate(testDate);
        actual.advanceDate();

        LocalDateTime expectedDate = LocalDateTime.of(2024, 2, 13 + 1, 12, 21);
        Date expected = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        expected.setDate(expectedDate);
        assertEquals(actual.getDate(), expected.getDate());
    }

    @Test
    public void testWeekOfMonthForDayOfWeek() {
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 13, 12, 21);
        Date actual = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        actual.setDate(testDate);
        assertEquals(2, actual.getWeekOfMonth());
    }

    @Test
    public void testDayOfWeek() {
        LocalDateTime testDate = LocalDateTime.of(2024, 2, 13, 12, 21);
        Date actual = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        actual.setDate(testDate);
        assertEquals(2, actual.getDayOfWeek());
    }
}