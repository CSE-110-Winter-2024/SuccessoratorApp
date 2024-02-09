package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

public class RoomGoalRepository {
    /*
    private final FlashcardDao flashcardDao;

    public RoomFlashcardRepository(FlashcardDao flashcardDao){
        this.flashcardDao = flashcardDao;
    }

    @Override
    public Subject<Flashcard> find(int id){
        var entityLiveData = flashcardDao.findAsLiveData(id);
        var flashcardLiveData = Transformations.map(entityLiveData, FlashcardEntity::toFLashcard);
        return new LiveDataSubjectAdapter<>(flashcardLiveData);
    }

    @Override
    public Subject<List<Flashcard>> findAll(){
        var entitiesLiveData = flashcardDao.findAllAsLiveData();
        var flashcardsLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(FlashcardEntity::toFLashcard)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(flashcardsLiveData);
    }

    @Override
    public void save(Flashcard flashcard){
        flashcardDao.insert(FlashcardEntity.fromFlashcard(flashcard));
    }

    public void save(List<Flashcard> flashcards){
        var entities = flashcards.stream()
                .map(FlashcardEntity::fromFlashcard)
                .collect(Collectors.toList());
        flashcardDao.insert(entities);
    }

    @Override
    public void append(Flashcard flashcard){
        flashcardDao.append(FlashcardEntity.fromFlashcard(flashcard));
    }

    @Override
    public void prepend(Flashcard flashcard){
        flashcardDao.prepend(FlashcardEntity.fromFlashcard(flashcard));
    }

    @Override
    public void remove(int id){
        flashcardDao.delete(id);
    }
    */
}
