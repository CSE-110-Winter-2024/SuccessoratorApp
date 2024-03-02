package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

@Entity(tableName = "recurringGoals")
public class RecurringGoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "frequency")
    public int frequency;

    @ColumnInfo(name = "start_date")
    public LocalDate startDate;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    RecurringGoalEntity(@NonNull String title, int frequency, LocalDate startDate, int sortOrder) {
        this.title = title;
        this.frequency = frequency;
        this.startDate = startDate;
        this.sortOrder = sortOrder;
    }

    public static RecurringGoalEntity fromRecurringGoal(@NonNull RecurringGoal recurringGoal) {
        var recurringGoalGoal = new RecurringGoalEntity(recurringGoal.getTitle(), recurringGoal.getFrequency(),
                recurringGoal.getStartDate(), recurringGoal.getSortOrder());
        recurringGoalGoal.id = recurringGoal.getId();
        return recurringGoalGoal;
    }

    public @NonNull RecurringGoal toRecurringGoal() {
        return new RecurringGoal(title, id, frequency, startDate, sortOrder);
    }
}
