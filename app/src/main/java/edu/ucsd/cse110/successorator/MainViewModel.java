package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final GoalRepository goalRepository;
    private final TimeKeeper timeKeeper;
    //private final SharedPreferences sharedPreferences;
    // UI state
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableSubject<Boolean> hasGoal;
    private final MutableSubject<Date> dateTime;
    private final MutableSubject<Date> lastLog;
    private final MutableSubject<String> placeholderText;
    public MainViewModel(GoalRepository goalRepository, TimeKeeper timeKeeper) {
        this.goalRepository = goalRepository;
        this.timeKeeper = timeKeeper;

        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.hasGoal = new SimpleSubject<>();
        this.placeholderText = new SimpleSubject<>();
        this.dateTime = new SimpleSubject<>();
        this.lastLog = new SimpleSubject<>();


        // Initialize...
        hasGoal.setValue(true);
        Date logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(timeKeeper.getDateTime().getValue());
        lastLog.setValue(logDate);
        Date currDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        currDate.setDate(LocalDateTime.now());
        dateTime.setValue(currDate);

        // When the list of cards changes (or is first loaded), reset the ordering.
        goalRepository.findAll().observe(cards -> {
            if (cards == null) return; // not ready yet, ignore

            var newOrderedCards = cards.stream()
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            orderedGoals.setValue(newOrderedCards);
        });

        dateTime.observe(date -> {
            if(date == null || date.getDate() == null) return;

            rollOver();
        });
    }

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getGoalRepository(), app.getTimeKeeper());
                    });

    public Subject<List<Goal>> getOrderedGoals() {
        return orderedGoals;
    }

    public void addGoal(Goal goal){
        goalRepository.append(goal);
    }

    public void remove(int id){
        goalRepository.remove(id);
    }

    public MutableSubject<Date> getDateTime() { return dateTime; }

    public Subject<Date> getLastLog() { return lastLog; }

    public void rollOver() {
        var lastLogDate = lastLog.getValue().getDate().toLocalDate();
        if(lastLogDate.isBefore(dateTime.getValue().getDate().toLocalDate())) {
            goalRepository.removeCompleted();
        }
    }
}
