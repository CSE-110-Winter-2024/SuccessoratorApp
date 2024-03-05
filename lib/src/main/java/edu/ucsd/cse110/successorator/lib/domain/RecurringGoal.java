package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RecurringGoal implements Serializable {
    //Constants
    private final int DAILY = 1;
    private final int WEEKLY = 2;
    private final int MONTHLY = 3;
    private final int YEARLY = 4;

    private final @Nullable Integer id;
    private final @NonNull String title;
    //private final Integer sortOrder; // Changed from 'int' to 'Integer' to allow null values.
    private final @NonNull Integer frequency;
    private final LocalDate startDate;

    private final LocalDate nextDate;

    public RecurringGoal(
            @NonNull String title,
            @Nullable Integer id,
            @NonNull Integer frequency,
            LocalDate startDate,
            LocalDate nextDate
            //Integer sortOrder // Corrected parameter type and removed the semicolon.
    ) {
        this.title = title;
        this.id = id;
        this.frequency = frequency;
        this.startDate = startDate;
        this.nextDate = nextDate;
        //this.sortOrder = sortOrder;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public @Nullable Integer getId() {
        return id;
    }

    @NonNull
    public Integer getFrequency() { return frequency; }

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getNextDate() { return nextDate; }

    //public Integer getSortOrder() {
    //    return sortOrder;
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringGoal that = (RecurringGoal) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(nextDate, that.nextDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, frequency, startDate, nextDate);
    }

    public RecurringGoal withId(int id) {
        return new RecurringGoal(this.title, id, this.frequency, this.startDate, this.nextDate);
    }

//    public RecurringGoal withSortOrder(int sortOrder) {
//        return new RecurringGoal(this.title, this.id, this.frequency, this.startDate, sortOrder);
//    }

    public RecurringGoal withNextDate(LocalDate nextDate) {
        return new RecurringGoal(this.title, id, this.frequency, this.startDate, nextDate);
    }

    public boolean isRecur(LocalDate currDate) {
        return nextDate.isBefore(currDate) || nextDate.isEqual(currDate);
    }

    public RecurringGoal updateNextDate() {
        switch(frequency) {
            case DAILY:
                return withNextDate(nextDate.plusDays(1));
            case WEEKLY:
                return withNextDate(nextDate.plusWeeks(1));
            case MONTHLY:
                return withNextDate(nextDate.plusWeeks(4));
            default:
                return withNextDate(nextDate.plusYears(1));
        }
    }
}
