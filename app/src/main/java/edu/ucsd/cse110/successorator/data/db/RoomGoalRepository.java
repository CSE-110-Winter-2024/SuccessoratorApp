package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

public class RoomGoalRepository {
    /*
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
        goalDao.insert(GoalEntity.fromGoal(goal));
    }

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
        goalDao.prepend(goalEntity.fromGoal(goal));
    }

    @Override
    public void remove(int id){
        goalDao.delete(id);
    }
    */
}
