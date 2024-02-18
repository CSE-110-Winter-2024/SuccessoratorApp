package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

public class SuccessoratorApplication extends Application {
    private GoalRepository goalRepository;
    private TimeKeeper timeKeeper;

    @Override
    public void onCreate() {
        super.onCreate();

        var database = Room.databaseBuilder(
                        getApplicationContext(),
                        SuccessoratorDatabase.class,
                        "successorator-database"
                )
                .allowMainThreadQueries()
                .build();

        this.goalRepository = new RoomGoalRepository(database.goalDao());

        var sharedPreferences = getSharedPreferences("successorator", MODE_PRIVATE);
        var isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if(isFirstRun && database.goalDao().count() == 0){
            //goalRepository.save(InMemoryDataSource.DEFAULT_CARDS);

            sharedPreferences.edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }

        sharedPreferences.edit()
                .putString("datetime", LocalDateTime.now().toString())
                .apply();
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.parse(sharedPreferences
                .getString("datetime", LocalDateTime.now().toString())));

        /*sharedPreferences.edit()
                .putString("datetime", LocalDateTime.now().toString())
                .apply();*/
    }



    public GoalRepository getGoalRepository() {
        return goalRepository;
    }

    public TimeKeeper getTimeKeeper() { return timeKeeper; }
}
