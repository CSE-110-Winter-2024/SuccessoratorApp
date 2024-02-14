package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;

    public Date(LocalDateTime dateTime, DateTimeFormatter formatter) {
        this.dateTime = dateTime;
        this.formatter = formatter;
    }

    public String getDate() {
        return formatter.format(dateTime);
    }
}
