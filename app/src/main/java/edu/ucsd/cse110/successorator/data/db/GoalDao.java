package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GoalEntity flashcard);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<GoalEntity> flashcards);

    @Query("SELECT * FROM goals WHERE id = :id")
    GoalEntity find(int id);

    @Query("SELECT * FROM goals ORDER BY sort_order")
    List<GoalEntity> findAll();

    @Query("SELECT * FROM goals WHERE id = :id")
    LiveData<GoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM goals ORDER BY sort_order")
    LiveData<List<GoalEntity>> findAllAsLiveData();

    @Query("SELECT * FROM goals WHERE state = 'Today' ORDER BY sort_order")
    LiveData<List<GoalEntity>> findAllTodayAsLiveData();

    @Query("SELECT * FROM goals WHERE state = 'Tomorrow' ORDER BY sort_order")
    LiveData<List<GoalEntity>> findAllTmrAsLiveData();

    @Query("SELECT * FROM goals WHERE state = 'Pending' ORDER BY sort_order")
    LiveData<List<GoalEntity>> findAllPendingAsLiveData();

    @Query("SELECT COUNT(*) FROM goals")
    int count();

    @Query("SELECT MIN(sort_order) FROM goals")
    int getMinSortOrder();

    @Query("SELECT MAX(sort_order) FROM goals")
    int getMaxSortOrder();

    @Query("SELECT MAX(sort_order) FROM goals WHERE isComplete = false")
    int getMaxSortOrderInComplete();

    @Query("UPDATE goals SET sort_order = sort_order + :by " + "WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    @Query("DELETE FROM goals WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM goals WHERE isComplete = 1 and state = 'Today'")
    void deleteCompleted();

    @Query("SELECT EXISTS(SELECT * FROM goals WHERE recurringId = :recurringId AND state = :state)")
    boolean existsRecurringId(int recurringId, String state);

    @Transaction
    default void shiftOver(int from){
        shiftSortOrders(from, getMaxSortOrder(), 1);
    }

    @Transaction
    default int append(GoalEntity goal) {
        var maxSortOrder = getMaxSortOrder();
        var newGoal = new GoalEntity(
                goal.title, goal.isComplete, maxSortOrder + 1, goal.state, goal.recurringId
        );
        return Math.toIntExact(insert(newGoal));
    }

    //Method for appending a complete goal to list
    @Transaction
    default int appendCompleteGoal(GoalEntity goal) {
        var maxSortOrderInComplete = getMaxSortOrderInComplete();
        shiftSortOrders(maxSortOrderInComplete + 1, getMaxSortOrder(), 1);
        var newGoal = new GoalEntity(
                goal.title, goal.isComplete, maxSortOrderInComplete + 1, goal.state, goal.recurringId
        );
        return Math.toIntExact(insert(newGoal));
    }

    @Transaction
    default int prepend(GoalEntity goal) {
        shiftSortOrders(getMinSortOrder(), getMaxSortOrder(), 1);
        var newGoal = new GoalEntity(
                goal.title, goal.isComplete, getMinSortOrder() - 1, goal.state, goal.recurringId
        );
        return Math.toIntExact(insert(newGoal));
    }

    // RECURRING GOALS //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRecur(RecurringGoalEntity recurringGoal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertRecur(List<RecurringGoalEntity> recurringGoals);

    @Query("SELECT * FROM recurringGoals WHERE id = :id")
    RecurringGoalEntity findRecur(int id);

    @Query("SELECT * FROM recurringGoals ORDER BY start_date")
    List<RecurringGoalEntity> findAllRecur();

    @Query("SELECT * FROM recurringGoals WHERE id = :id")
    LiveData<RecurringGoalEntity> findRecurAsLiveData(int id);

    @Query("SELECT * FROM recurringGoals ORDER BY start_date")
    LiveData<List<RecurringGoalEntity>> findAllRecurAsLiveData();

    @Query("SELECT COUNT(*) FROM recurringGoals")
    int countRecur();

    @Query("DELETE FROM recurringGoals WHERE id = :id")
    void deleteRecur(int id);
}
