// Code Modified from Lab 5
// May be an unnecessary file, test to see if it's required once we get functional
package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleGoalRepository implements GoalRepository{
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
        return dataSource.getAllFlashcardSubject();
    }

    @Override public void save(Goal goal) {
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
    public void append(Goal goal) {
        dataSource.putGoal(goal.withSortOrder(dataSource.getMaxSortOrder() + 1));
    }

    public void prepend(Goal) {
        //shift all the existing cards up by one
        dataSource.shiftSortOrders(0, dataSource.getMaxSortOrder(),1);
        //then insert new card before the first one
        dataSource.putFlashcard(flashcard.withSortOrder(dataSource.getMinSortOrder() - 1));
    }
}
