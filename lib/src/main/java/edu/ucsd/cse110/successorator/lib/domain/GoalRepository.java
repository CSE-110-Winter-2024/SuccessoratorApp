package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository {

    Subject<Goal> find(int id);

    Subject<List<Goal>> findAll();

    Subject<List<Goal>> findAllToday();

    Subject<List<Goal>> findAllTmr();

    Subject<List<Goal>> findAllPending();

    void save(Goal flashcard);

    Subject<RecurringGoal> findRecur(int id);

    Subject<List<RecurringGoal>> findAllRecur();

    void remove(int id);

    void appendCompleteGoal(Goal goal);

    boolean existsRecurringId(int recurringId, String state);

    void removeRecur(int id);

    void appendRecur(RecurringGoal recurringGoal);

    void save(List<Goal> goalList);

    void append(Goal goal);

    void prepend(Goal goal);

    void removeCompleted();


}
