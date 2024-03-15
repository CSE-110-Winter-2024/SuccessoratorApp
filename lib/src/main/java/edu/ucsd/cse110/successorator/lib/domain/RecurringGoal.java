package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.util.Constants;

public class RecurringGoal implements Serializable {

    private final @Nullable Integer id;
    private final @NonNull String title;
    private final @NonNull Integer frequency;
    private final LocalDate startDate;
    private final LocalDate nextDate;
    private final Integer contextId;

    public RecurringGoal(
            @NonNull String title,
            @Nullable Integer id,
            @NonNull Integer frequency,
            LocalDate startDate,
            Integer contextId
    ) {
        this.title = title;
        this.id = id;
        this.frequency = frequency;
        this.startDate = startDate;
        this.nextDate = startDate;
        this.contextId = contextId;
    }

    public RecurringGoal(
            @NonNull String title,
            @Nullable Integer id,
            @NonNull Integer frequency,
            LocalDate startDate,
            LocalDate nextDate,
            Integer contextId
    ) {
        this.title = title;
        this.id = id;
        this.frequency = frequency;
        this.startDate = startDate;
        this.nextDate = nextDate;
        this.contextId = contextId;
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
    public Integer getContextId() { return contextId; }

    public RecurringGoal withId(int id) {
        return new RecurringGoal(this.title, id, this.frequency, this.startDate, this.nextDate, this.contextId);
    }

    public RecurringGoal withNextDate(LocalDate nextDate) {
        return new RecurringGoal(this.title, this.id, this.frequency, this.startDate, nextDate, this.contextId);
    }

    public boolean isRecur(LocalDate currDate) {
        return nextDate.isBefore(currDate) || nextDate.isEqual(currDate);
    }

    public RecurringGoal updateNextDate(LocalDate currDate) {
        switch(frequency) {
            case Constants.DAILY:
                return updateNextDateDaily(currDate);
            case Constants.WEEKLY:
                return updateNextDateWeekly(currDate);
            case Constants.MONTHLY:
                return updateNextDateMonthly(currDate);
            default:
                return updateNextDateYearly(currDate);
        }
    }

    private RecurringGoal updateNextDateDaily(LocalDate currDate) {
        return withNextDate(currDate.plusDays(1));
    }

    private RecurringGoal updateNextDateWeekly(LocalDate currDate) {
        int dayOfWeek = calcDayOfWeek(startDate);
        int currDayOfWeek = calcDayOfWeek(currDate);
        int daysToAdd = dayOfWeek <= currDayOfWeek ?
                7 - (currDayOfWeek - dayOfWeek) : dayOfWeek - currDayOfWeek;
        return withNextDate(currDate.plusDays(daysToAdd));
    }

    private RecurringGoal updateNextDateYearly(LocalDate currDate) {
        LocalDate newDate = startDate.plusYears(currDate.getYear() - startDate.getYear());
        if(newDate.isAfter(currDate)) {
            return withNextDate(newDate);
        }
        return withNextDate(newDate.plusYears(1));
    }

    private RecurringGoal updateNextDateMonthly(LocalDate currDate) {
        int weekOfMonth = calcWeekOfMonth(startDate);
        int dayOfWeek = calcDayOfWeek(startDate);
        int dayOfMonth = getDayOfMonth(dayOfWeek, weekOfMonth, currDate);
        if(currDate.getDayOfMonth() < dayOfMonth) {
            if(dayOfMonth>28) {
                return withNextDate(currDate.withDayOfMonth(dayOfMonth - 7).plusWeeks(1));
            }
            return withNextDate(currDate.withDayOfMonth(dayOfMonth));
        }
        else {
            if(dayOfMonth>28) {
                return withNextDate(currDate.plusMonths(1).withDayOfMonth(getDayOfMonth(
                        dayOfWeek, weekOfMonth, currDate.plusMonths(1)) - 7).plusWeeks(1));
            }
            return withNextDate(currDate.plusMonths(1).withDayOfMonth(getDayOfMonth(
                    dayOfWeek, weekOfMonth, currDate.plusMonths(1))));
        }
    }

    private int getDayOfMonth(int dayOfWeek, int weekOfMonth, LocalDate currDate) {
        LocalDate startOfMonth = currDate.withDayOfMonth(1);
        int firstDayOfWeek = calcDayOfWeek(startOfMonth);
        int daysToAdd = dayOfWeek < firstDayOfWeek ?
                7 - (firstDayOfWeek - dayOfWeek) : dayOfWeek - firstDayOfWeek;

        return 7 * (weekOfMonth - 1) + daysToAdd + 1;
    }

    private int calcWeekOfMonth(LocalDate date) { return (date.getDayOfMonth() + 6) / 7; }

    private int calcDayOfWeek(LocalDate date) { return date.getDayOfWeek().getValue(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringGoal that = (RecurringGoal) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(frequency, that.frequency) && Objects.equals(startDate, that.startDate) && Objects.equals(nextDate, that.nextDate) && Objects.equals(contextId, that.contextId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, frequency, startDate, nextDate, contextId);
    }
}
