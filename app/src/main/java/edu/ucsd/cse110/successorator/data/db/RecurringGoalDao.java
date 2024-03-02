package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface RecurringGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecurringGoalEntity recurringGoal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<RecurringGoalEntity> recurringGoals);

    @Query("SELECT * FROM recurringGoals WHERE id = :id")
    RecurringGoalEntity find(int id);

    @Query("SELECT * FROM recurringGoals ORDER BY sort_order")
    List<RecurringGoalEntity> findAll();

    @Query("SELECT * FROM recurringGoals WHERE start_date = :start_date")
    List<RecurringGoalEntity> findAllToAdd(String start_date);

    @Query("SELECT * FROM recurringGoals WHERE id = :id")
    LiveData<RecurringGoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM recurringGoals ORDER BY sort_order")
    LiveData<List<RecurringGoalEntity>> findAllAsLiveData();

    @Query("SELECT * FROM recurringGoals WHERE start_date = :start_date")
    LiveData<List<RecurringGoalEntity>> findAllToAddAsLiveData(String start_date);

    @Query("SELECT COUNT(*) FROM recurringGoals")
    int count();

    @Query("SELECT MIN(sort_order) FROM recurringGoals")
    int getMinSortOrder();

    @Query("SELECT MAX(sort_order) FROM recurringGoals")
    int getMaxSortOrder();

    @Query("UPDATE recurringGoals SET sort_order = sort_order + :by " + "WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    @Query("DELETE FROM recurringGoals WHERE id = :id")
    void delete(int id);

    @Transaction
    default void shiftOver(int from){
        shiftSortOrders(from, getMaxSortOrder(), 1);
    }

    @Transaction
    default int append(RecurringGoalEntity recurringGoal) {
        var maxSortOrder = getMaxSortOrder();
        var newRecurringGoal = new RecurringGoalEntity (
                recurringGoal.title, recurringGoal.frequency,
                recurringGoal.startDate, maxSortOrder + 1
        );
        return Math.toIntExact(insert(newRecurringGoal));
    }
}
