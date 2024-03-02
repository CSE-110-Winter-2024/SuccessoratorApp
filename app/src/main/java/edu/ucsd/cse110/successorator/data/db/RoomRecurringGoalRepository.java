package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoalRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;

public class RoomRecurringGoalRepository implements RecurringGoalRepository {
    private final RecurringGoalDao recurringGoalDao;

    public RoomRecurringGoalRepository(RecurringGoalDao recurringGoalDao){
        this.recurringGoalDao = recurringGoalDao;
    }

    @Override
    public Subject<RecurringGoal> find(int id){
        var entityLiveData = recurringGoalDao.findAsLiveData(id);
        var recurringGoalLiveData = Transformations.map(entityLiveData, RecurringGoalEntity::toRecurringGoal);
        return new LiveDataSubjectAdapter<>(recurringGoalLiveData);
    }

    @Override
    public Subject<List<RecurringGoal>> findAll(){
        var entitiesLiveData = recurringGoalDao.findAllAsLiveData();
        var recurringGoalsLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(RecurringGoalEntity::toRecurringGoal)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(recurringGoalsLiveData);
    }

    @Override
    public void remove(int id) {
        recurringGoalDao.delete(id);
    }

    @Override
    public void append(RecurringGoal recurringGoal){
        recurringGoalDao.append(RecurringGoalEntity.fromRecurringGoal(recurringGoal));
    }
}
