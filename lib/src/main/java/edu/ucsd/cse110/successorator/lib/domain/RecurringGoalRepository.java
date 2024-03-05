package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface RecurringGoalRepository {
    Subject<RecurringGoal> find(int id);

    Subject<List<RecurringGoal>> findAll();

//    void save(RecurringGoal recurringGoal);

    void remove(int id);

//    void save(List<RecurringGoal> recurringGoalList);

    void add(RecurringGoal recurringGoal);
}
