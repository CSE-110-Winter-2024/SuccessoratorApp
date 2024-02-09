package edu.ucsd.cse110.successorator.data.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    GoalEntity(@NonNull String title, boolean isComplete, int sortOrder){
        this.title = title;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
    }
/*
    public static GoalEntity fromGoal(@NonNull Goal goal){
        var card = new GoalEntity(goal.title(), goal.isComplete(), goal.sortOrder());
        card.id = goal.id();
        return card;
    }

    public @NonNull Goal toGoal(){
        return new Goal(id, title, isComplete, sortOrder);
    }
 */
}
