package edu.ucsd.cse110.successorator.data.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

@Entity(tableName = "goals")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "isComplete")
    public boolean isComplete;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    @ColumnInfo(name = "state")
    public String state;

    @ColumnInfo(name = "recurringId")
    public Integer recurringId;

    @ColumnInfo(name = "contextId")
    public int contextId;

    GoalEntity(@NonNull String title, boolean isComplete,
               int sortOrder, String state,
               int recurringId, int contextId){
        this.title = title;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
        this.state = state;
        this.recurringId = recurringId;
        this.contextId = contextId;
    }

    public static GoalEntity fromGoal(@NonNull Goal goal){
        var goalGoal = new GoalEntity(goal.getTitle(),
                goal.isComplete(), goal.getSortOrder(),
                goal.getState(), goal.getRecurringId(),
                goal.getContextId());
        goalGoal.id = goal.getId();
        return goalGoal;
    }

    public @NonNull Goal toGoal(){
        return new Goal(title, id, isComplete,
                sortOrder, state, recurringId, contextId);
    }

}
