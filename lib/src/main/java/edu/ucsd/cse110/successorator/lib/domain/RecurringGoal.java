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
    private final LocalDate startDate;

    public RecurringGoal(
            @NonNull String title,
            @Nullable Integer id,
            @NonNull Integer frequency,
            LocalDate startDate,
            Integer sortOrder // Corrected parameter type and removed the semicolon.
    ) {
        this.title = title;
        this.id = id;
        this.frequency = frequency;
        this.startDate = startDate;
        this.sortOrder = sortOrder;
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

    public Integer getSortOrder() {
        return sortOrder;
    }

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
        return new RecurringGoal(this.title, id, this.frequency, this.startDate, this.sortOrder);
    }

    public RecurringGoal withSortOrder(int sortOrder) {
        return new RecurringGoal(this.title, this.id, this.frequency, this.startDate, sortOrder);
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
