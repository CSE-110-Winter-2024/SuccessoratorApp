package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

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

    @Override
    public void save(Goal goal) {
        dataSource.putGoal(goal);
    }

    @Override
    public void save(List<Goal> goals) {
        dataSource.putGoals(goals);
    }

    @Override
    public void remove(int id) {
        dataSource.removeFlashcard(id);
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
    public void appendCompleteGoal(Goal goal){
        //dataSource.shiftSortOrders()
    }
//
//    var goals = this.orderedGoals.getValue();
//        for(int i = 0; i < goals.size(); i++){
//        var thisGoal = goals.get(i);
//        if(thisGoal.isComplete()){
//            return thisGoal.getSortOrder();
//        }
//    }
//        return goals.get(goals.size() - 1).getSortOrder() + 1;

    @Override
    public void shiftOver(int from){

    }
}
