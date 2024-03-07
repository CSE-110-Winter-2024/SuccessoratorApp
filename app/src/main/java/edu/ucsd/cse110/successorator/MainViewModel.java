package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.SharedPreferences;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final GoalRepository goalRepository;
    private final TimeKeeper timeKeeper;
    // UI state
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableSubject<Boolean> hasGoal;
    private final MutableSubject<Date> currDate;
    private final MutableSubject<Date> lastLog;
    //private final MutableSubject<String> placeholderText;
    public MainViewModel(GoalRepository goalRepository, TimeKeeper timeKeeper) {
        this.goalRepository = goalRepository;
        this.timeKeeper = timeKeeper;

        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.hasGoal = new SimpleSubject<>();

        //this.placeholderText = new SimpleSubject<>();
        this.currDate = new SimpleSubject<>();
        this.lastLog = new SimpleSubject<>();

        // Initialize...
        hasGoal.setValue(true);
        Date logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(timeKeeper.getDateTime().getValue());
        lastLog.setValue(logDate);
        Date datetime = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        datetime.setDate(LocalDateTime.now());
        currDate.setValue(datetime);

        // When the list of cards changes (or is first loaded), reset the ordering.
        goalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore


            var newOrderedGoals = goals.stream()
                    .sorted(Comparator.comparing(Goal::isComplete)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());

            orderedGoals.setValue(newOrderedGoals);
        });

        currDate.observe(date -> {
            if(date == null || date.getDate() == null) return;

            rollOverGoal(lastLog.getValue(), currDate.getValue());
        });

    }

    // Initialize the view model
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

    public void save(Goal goal) {
        //updateGoal(goal);
        goalRepository.save(goal);
    }

    public void addGoal(Goal goal) {
        goalRepository.appendCompleteGoal(goal);
    }

    public Subject<Date> getCurrDate() { return currDate; }

    public Subject<Date> getLastLog() { return lastLog; }

    public void updateTime(Date date, boolean logTime) {
        if(logTime) {
            lastLog.setValue(date);
        }
        else {
            currDate.setValue(date);
        }
    }

    public void remove(int id) {
        goalRepository.remove(id);
    }

    public void rollOverGoal(Date lastLogDate, Date currentDate) {
        if(lastLogDate.getDate().toLocalDate()
                .isBefore(currentDate.getDate().toLocalDate())) {
            goalRepository.removeCompleted();
            lastLogDate.setDate(LocalDateTime.now());
            updateTime(lastLogDate, true);
        }
    }
}
