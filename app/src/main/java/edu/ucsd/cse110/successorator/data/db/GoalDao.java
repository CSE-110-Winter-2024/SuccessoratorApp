package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

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

    @Transaction
    default void shiftOver(int from){
        shiftSortOrders(from, getMaxSortOrder(), 1);
    }
    @Transaction
    default int append(GoalEntity goal) {
        var maxSortOrder = getMaxSortOrder();
        var newGoal = new GoalEntity(
                goal.title, goal.isComplete, maxSortOrder + 1
        );
        return Math.toIntExact(insert(newGoal));
    }

    //Method for appending a complete goal to list
    @Transaction
    default int appendCompleteGoal(GoalEntity goal) {
        var maxSortOrderInComplete = getMaxSortOrderInComplete();
        shiftSortOrders(maxSortOrderInComplete + 1, getMaxSortOrder(), 1);
        var newGoal = new GoalEntity(
                goal.title, goal.isComplete, maxSortOrderInComplete + 1
        );
        return Math.toIntExact(insert(newGoal));
    }

    @Transaction
    default int prepend(GoalEntity goal) {
        shiftSortOrders(getMinSortOrder(), getMaxSortOrder(), 1);
        var newGoal = new GoalEntity(
                goal.title, goal.isComplete, getMinSortOrder() - 1
        );
        return Math.toIntExact(insert(newGoal));
    }
}
