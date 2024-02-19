package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;

public class RoomGoalRepository implements GoalRepository {
    private final GoalDao goalDao;

    public RoomGoalRepository(GoalDao goalDao){
        this.goalDao = goalDao;
    }

    @Override
    public Subject<Goal> find(int id){
        var entityLiveData = goalDao.findAsLiveData(id);
        var goalLiveData = Transformations.map(entityLiveData, GoalEntity::toGoal);
        return new LiveDataSubjectAdapter<>(goalLiveData);
    }

    @Override
    public Subject<List<Goal>> findAll(){
        var entitiesLiveData = goalDao.findAllAsLiveData();
        var goalsLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(goalsLiveData);
    }

    @Override
    public void save(Goal goal){
        if(goal.isComplete()){
            int firstCompleteGoal = goalDao.getMaxSortOrderInComplete() + 1;
            goalDao.shiftSortOrders(firstCompleteGoal, goalDao.getMaxSortOrder(), 1);
            var newGoal = goal.withSortOrder(firstCompleteGoal);
            goalDao.insert(GoalEntity.fromGoal(newGoal));
        }else{
            goalDao.shiftSortOrders(1, goalDao.getMaxSortOrder(), 1);
            var newGoal = goal.withSortOrder(1);
            goalDao.insert(GoalEntity.fromGoal(newGoal));
        }
        //goalDao.insert(GoalEntity.fromGoal(goal));
    }

    @Override
    public void appendCompleteGoal(Goal goal){
        goalDao.appendCompleteGoal(GoalEntity.fromGoal(goal));
    }

    // ----- Unused -----
    public void save(List<Goal> goals){
        var entities = goals.stream()
                .map(GoalEntity::fromGoal)
                .collect(Collectors.toList());
        goalDao.insert(entities);
    }
    @Override
    public void append(Goal goal){
        goalDao.append(GoalEntity.fromGoal(goal));
    }

    @Override
    public void prepend(Goal goal){
        goalDao.prepend(GoalEntity.fromGoal(goal));
    }

    @Override
    public void remove(int id) {
        goalDao.delete(id);
    }

    @Override
    public void removeCompleted() {
        goalDao.deleteCompleted();
    }
}
