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
        //this.dateTime = dateTime;
        this.formatter = formatter;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return formatter.format(dateTime);
    }

    public String getDateTime() { return formatter.format(dateTime) + " " + dateTime.toLocalTime().toString(); }
}
