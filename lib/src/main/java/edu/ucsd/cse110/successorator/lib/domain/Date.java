package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
 * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 */
public class Date {
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;

    public Date(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime.minusHours(2);
    }

    public String formatDate() {
        return formatter.format(dateTime);
    }

    public String formatDateTime() { return formatter.format(dateTime) + " " + dateTime.toLocalTime().toString(); }

    public LocalDateTime getDate() {return this.dateTime; }

    public int getWeekOfMonthForDayOfWeek() { return (this.dateTime.getDayOfMonth() + 6) / 7; }

    public int getDayOfWeek() { return this.dateTime.getDayOfWeek().getValue(); }

    public void advanceDate() { this.dateTime = dateTime.plusDays(1); }
}
