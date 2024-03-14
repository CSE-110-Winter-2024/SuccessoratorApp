package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Constants;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.Subject;

/**
  * Simple Goal Repository for unit testing
  */
public class SimpleGoalRepository implements GoalRepository {
    private final InMemoryDataSource dataSource;

    public SimpleGoalRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject<Goal> find(int id) {
        return dataSource.getGoalSubject(id);
    }

    @Override
    public Subject<List<Goal>> findAll() {
        return dataSource.getAllGoalsSubject();
    }

    public Subject<List<Goal>> findAllToday(){
        return dataSource.getAllTodayGoalsSubject();
    }

    @Override
    public Subject<List<Goal>> findAllTmr(){
        return dataSource.getAllTomorrowGoalsSubject();
    }

    public Subject<List<Goal>> findAllPending(){
        return dataSource.getAllPendingGoalsSubject();
    }

    @Override
    public void save(Goal goal) {
        if(goal.isComplete()){
            int firstCompleteGoal = dataSource.getMaxSortOrderInComplete() + 1;
            dataSource.shiftSortOrders(firstCompleteGoal, dataSource.getMaxSortOrder(), 1);
            var newGoal = goal.withSortOrder(firstCompleteGoal);
            dataSource.putGoal(newGoal);
        }else{
            dataSource.shiftSortOrders(1, dataSource.getMaxSortOrder(), 1);
            var newGoal = goal.withSortOrder(1);
            dataSource.putGoal(newGoal);
        }
    }

    @Override
    public void saveAndAppend(Goal goal) {
        if(!goal.isComplete()){
            int firstCompleteGoal = dataSource.getMaxSortOrderInComplete() + 1;
            dataSource.shiftSortOrders(firstCompleteGoal, dataSource.getMaxSortOrder(), 1);
            var newGoal = goal.withSortOrder(firstCompleteGoal);
            dataSource.putGoal(newGoal);
        }else{
            var newGoal = goal.withSortOrder(dataSource.getMaxSortOrder() + 1);
            dataSource.putGoal(newGoal);
        }
    }

    @Override
    public void appendCompleteGoal(Goal goal){
        int firstComplete = dataSource.getMaxSortOrderInComplete();
        dataSource.shiftSortOrders(firstComplete + 1,
                dataSource.getMaxSortOrder(), 1);
        var newGoal = goal.withSortOrder(firstComplete + 1);
        dataSource.putGoal(newGoal);
    }

    // ------- Unused -------
    @Override
    public void remove(int id) {
        dataSource.removeGoal(id);
    }

    @Override
    public void append(Goal goal){
        dataSource.putGoal(goal.withSortOrder(dataSource.getMaxSortOrder() + 1));
    }

    @Override
    public void prepend(Goal goal){
        //shift all the existing cards up by one
        dataSource.shiftSortOrders(0, dataSource.getMaxSortOrder(), 1);
        //then insert the new card before the first one
        dataSource.putGoal(goal.withSortOrder(dataSource.getMinSortOrder() - 1));
    }

    @Override
    public void removeCompleted() {
        var goals = dataSource.getGoals();
        for(var goal : goals) {
            if(goal.isComplete() && goal.getState().equals(Constants.TODAY)) {
                dataSource.removeGoal(goal.getId());
            }
        }
    }

    @Override
    public void save(List<Goal> goals) {
        dataSource.putGoals(goals);
    }

    @Override
    public boolean existsRecurringId(int recurringId, String state) {
        if(recurringId<0) return false;
        var goals = dataSource.getGoals();
        for(var goal : goals) {
            if(goal.getRecurringId() == recurringId && goal.getState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Subject<RecurringGoal> findRecur(int id){
        return dataSource.getRecurringGoalSubject(id);
    }

    @Override
    public Subject<List<RecurringGoal>> findAllRecur(){
        return dataSource.getAllRecurringGoalsSubject();
    }

    @Override
    public void removeRecur(int id){
        dataSource.removeRecurringGoal(id);
    }

    @Override
    public void appendRecur(RecurringGoal recurringGoal){
        dataSource.putRecurringGoal(recurringGoal);
    }
}
