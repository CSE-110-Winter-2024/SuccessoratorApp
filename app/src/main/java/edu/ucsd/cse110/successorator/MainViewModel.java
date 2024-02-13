package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class MainViewModel extends ViewModel {
    private final GoalRepository goalRepository;

    private final MutableSubject<String> displayedText;
    private final MutableSubject<List<Goal>> orderedGoals;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getGoalRepository());
                    });

    public MainViewModel(GoalRepository goalRepository) {
        this.orderedGoals = new SimpleSubject<>();
        this.goalRepository = goalRepository;
        this.displayedText = new SimpleSubject<>();

        goalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedCards = goals.stream().sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            orderedGoals.setValue(newOrderedCards);
        });

        orderedGoals.observe(goals -> {
            if (goals == null || goals.size() == 0) return;
            var card = goals.get(0);
        });

    }

    public void addGoal(Goal goal){
        goalRepository.append(goal);
    }


    public Subject<String> getDisplayedText() {
        return displayedText;
    }
}
