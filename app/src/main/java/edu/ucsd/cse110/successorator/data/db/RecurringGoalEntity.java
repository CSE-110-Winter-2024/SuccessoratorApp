package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @ColumnInfo(name = "next_date")
    public LocalDate nextDate;

    @ColumnInfo(name = "contextId")
    public int contextId;

    RecurringGoalEntity(@NonNull String title, int frequency, LocalDate startDate, LocalDate nextDate, int contextId) {
        this.title = title;
        this.frequency = frequency;
        this.startDate = startDate;
        this.nextDate = nextDate;
        this.contextId = contextId;
    }

    public static RecurringGoalEntity fromRecurringGoal(@NonNull RecurringGoal recurringGoal) {
        var recurringGoalGoal = new RecurringGoalEntity(recurringGoal.getTitle(), recurringGoal.getFrequency(),
                recurringGoal.getStartDate(), recurringGoal.getNextDate(), recurringGoal.getContextId());
        recurringGoalGoal.id = recurringGoal.getId();
        return recurringGoalGoal;
    }

    public @NonNull RecurringGoal toRecurringGoal() {
        return new RecurringGoal(title, id, frequency, startDate, nextDate, contextId);
    }
}
