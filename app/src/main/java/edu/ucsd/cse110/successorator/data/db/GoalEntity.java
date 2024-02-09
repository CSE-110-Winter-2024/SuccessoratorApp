package edu.ucsd.cse110.successorator.data.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goal")
public class GoalEntity {
    /*
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "front")
    public String front;

    @ColumnInfo(name = "back")
    public String back;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    FlashcardEntity(@NonNull String front, @NonNull String back, int sortOrder){
        this.front = front;
        this.back = back;
        this.sortOrder = sortOrder;
    }

    public static FlashcardEntity fromFlashcard(@NonNull Flashcard flashcard){
        var card = new FlashcardEntity(flashcard.front(), flashcard.back(), flashcard.sortOrder());
        card.id = flashcard.sortOrder();
        return card;
    }

    public @NonNull Flashcard toFLashcard(){
        return new Flashcard(id, front, back, sortOrder);
    }
    */
}
