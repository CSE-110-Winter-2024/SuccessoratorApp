package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

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
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class MainViewModel extends ViewModel {
    private final GoalRepository goalRepository;
    //private final TimeKeeper timeKeeper;
    // UI state
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableSubject<Boolean> hasGoal;
    //private final MutableSubject<String> currDate;
    private final MutableSubject<String> placeholderText;
    public MainViewModel(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
        //this.timeKeeper = timeKeeper;

        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.hasGoal = new SimpleSubject<>();
        //this.currDate = new SimpleSubject<>();
        this.placeholderText = new SimpleSubject<>();


        // Initialize...
        hasGoal.setValue(true);
        //currDate.setValue("Today!");

        // When the list of cards changes (or is first loaded), reset the ordering.
        goalRepository.findAll().observe(cards -> {
            if (cards == null) return; // not ready yet, ignore

            var newOrderedCards = cards.stream()
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            orderedGoals.setValue(newOrderedCards);
        });
    }

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getGoalRepository());
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


    //public Subject<LocalDateTime> getDateTime() { return timeKeeper.getDateTime(); }
}
