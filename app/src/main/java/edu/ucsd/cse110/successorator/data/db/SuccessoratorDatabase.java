package edu.ucsd.cse110.successorator.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {GoalEntity.class, RecurringGoalEntity.class}, version = 2)
@TypeConverters({DateTimeConverter.class})
public abstract class SuccessoratorDatabase extends RoomDatabase{
    public abstract GoalDao goalDao();
    public abstract RecurringGoalDao recurringGoalDao();
}
