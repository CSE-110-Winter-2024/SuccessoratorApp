package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class RecurringGoal implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String title;
    private final Integer sortOrder; // Changed from 'int' to 'Integer' to allow null values.
    private final @NonNull Integer frequency;
    private LocalDate startDate;
    private

    public RecurringGoal(
            @NonNull String title,
            @Nullable Integer id,
            @NonNull Integer frequency,
            LocalDate startDate,
            Integer sortOrder // Corrected parameter type and removed the semicolon.
    ) {
        this.id = id;
        this.title = title;
        this.frequency = frequency;
        this.startDate = startDate;
        this.sortOrder = sortOrder;
    }

    public @Nullable Integer getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    @NonNull
    public Integer getFrequency() { return frequency; }

    public LocalDate getStartDate() { return startDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringGoal that = (RecurringGoal) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(sortOrder, that.sortOrder) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, sortOrder, frequency, startDate);
    }

    public RecurringGoal withId(int id) {
        return new RecurringGoal(this.title, id, this.isComplete, this.sortOrder);
    }

    public RecurringGoal withSortOrder(int sortOrder) {
        return new RecurringGoal(this.title, this.id, this.isComplete, sortOrder);
    }

    public RecurringGoal withComplete(boolean isComplete){
        return new RecurringGoal(this.title, this.id, isComplete, this.sortOrder);
    }

    public void setComplete(boolean b) {
    }

    public enum Frequency {
        DAILY(1),
        WEEKLY(2),
        MONTHLY(3),
        YEARLY(4);
        private final int freq;
        Frequency(int freq) {
            this.freq = freq;
        }
        public int getFreq() {
            return freq;
        }
    }
}
