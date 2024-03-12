package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDate;
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

    //use when time already adjusted for 2 hours
    public void setAdjustedDate(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String formatDate() {
        return formatter.format(dateTime);
    }

    public String formatTomorrowDate() { return formatter.format(dateTime.plusDays(1)); }

    public String formatDateTime() { return formatter.format(dateTime) + " " + dateTime.toLocalTime().toString(); }

    public LocalDateTime getDate() {return this.dateTime; }

    public int getWeekOfMonth() { return (this.dateTime.getDayOfMonth() + 6) / 7; }

    public int getDayOfWeek() { return this.dateTime.getDayOfWeek().getValue(); }

    // https://stackoverflow.com/a/63511272
    public String dayOfWeek() {
        String str = this.dateTime.getDayOfWeek().toString();
        return str.charAt(0) + str.substring(1).toLowerCase();
    }

    public String getDayAndMonth() {
        return this.dateTime.getMonthValue() + "/" + this.dateTime.getDayOfMonth();
    }

    public String getDayOfMonthWithSuffix(int num) {
        String suffix;

        if (num == 1 || num == 21 || num == 31) { suffix = "st"; }
        else if (num == 2 || num == 22) { suffix = "nd"; }
        else if (num == 3 || num == 23) { suffix = "rd"; }
        else suffix = "th";

        return this.getWeekOfMonth() + suffix;
    }

    public LocalDateTime getTomorrow() { return dateTime.plusDays(1); }

    public void advanceDate() { this.dateTime = dateTime.plusDays(1); }
}
