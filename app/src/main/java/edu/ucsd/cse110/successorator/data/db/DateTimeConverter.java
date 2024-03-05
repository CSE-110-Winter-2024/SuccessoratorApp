package edu.ucsd.cse110.successorator.data.db;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateTimeConverter {
    @TypeConverter
    public static LocalDate fromTimestamp(Long timestamp) {
        if(timestamp == null) { return null; }
        return LocalDate.ofEpochDay(timestamp);
    }

    @TypeConverter
    public static Long toTimeStamp(LocalDate date) {
        if(date == null) { return null; }
        return date.toEpochDay();
    }
}
