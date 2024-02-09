package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository<T> {

    Subject<T> find(int id);

    Subject<List<T>> findAll();

    void save(T flashcard);

    void save(List<T> flashcards);

    void remove(int id);

    void append(T flashcard);

    void prepend(T flashcard);

}
