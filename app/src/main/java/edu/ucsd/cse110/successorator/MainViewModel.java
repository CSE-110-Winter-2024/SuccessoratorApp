package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

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

    private final MutableSubject<Integer> focusMode;
    // UI state
    private final MutableSubject<List<Goal>> orderedGoals;
    private final MutableSubject<List<Goal>> tmrGoals;

    private final MutableSubject<List<Goal>> pendingGoals;
    private final MutableSubject<Boolean> hasGoal;
    private final MutableSubject<Date> currDate;
    private final MutableSubject<Date> lastLog;
    //private final MutableSubject<String> placeholderText;
    public MainViewModel(GoalRepository goalRepository, TimeKeeper timeKeeper) {
        this.goalRepository = goalRepository;
        this.timeKeeper = timeKeeper;

        this.focusMode = new SimpleSubject<>();
        focusMode.setValue(0);
        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.tmrGoals = new SimpleSubject<>();
        this.pendingGoals = new SimpleSubject<>();

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
        goalRepository.findAllToday().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .filter(goal -> !goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            var complete = goals.stream()
                    .filter(goal -> goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            newOrderedGoals.addAll(complete);

            orderedGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllTmr().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .filter(goal -> !goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            var complete = goals.stream()
                    .filter(goal -> goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            newOrderedGoals.addAll(complete);

            tmrGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllPending().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .sorted(Comparator.comparing(Goal::isComplete)
                            .thenComparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());

            pendingGoals.setValue(newOrderedGoals);
        });

        focusMode.observe(context -> {
            if(context == 0){
                defaultLists();
            }else{
                filterByContext(context);
            }
        });

        currDate.observe(date -> {
            if(date == null || date.getDate() == null) return;

            rollOverGoal(lastLog.getValue(), currDate.getValue());
        });

    }

    private void defaultLists(){
        goalRepository.findAllToday().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore
            var newOrderedGoals = goals.stream()
                    .filter(goal -> !goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            var complete = goals.stream()
                    .filter(goal -> goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            newOrderedGoals.addAll(complete);

            orderedGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllTmr().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .filter(goal -> !goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            var complete = goals.stream()
                    .filter(goal -> goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());
            newOrderedGoals.addAll(complete);

            tmrGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllPending().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .sorted(Comparator.comparing(Goal::isComplete)
                            .thenComparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());

            pendingGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllPending().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore
            var newOrderedGoals = goals.stream()
                    .sorted(Comparator.comparing(Goal::isComplete)
                            .thenComparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .collect(Collectors.toList());

            pendingGoals.setValue(newOrderedGoals);
        });
    }
    private void filterByContext(int context){
        goalRepository.findAllToday().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore
            var newOrderedGoals = goals.stream()
                    .filter(goal -> !goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .filter(goal -> goal.getContextId() == context)
                    .collect(Collectors.toList());
            var complete = goals.stream()
                    .filter(goal -> goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .filter(goal -> goal.getContextId() == context)
                    .collect(Collectors.toList());
            newOrderedGoals.addAll(complete);

            orderedGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllTmr().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .filter(goal -> !goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .filter(goal -> goal.getContextId() == context)
                    .collect(Collectors.toList());
            var complete = goals.stream()
                    .filter(goal -> goal.isComplete())
                    .sorted(Comparator.comparingInt(Goal::getSortOrder))
                    .filter(goal -> goal.getContextId() == context)
                    .collect(Collectors.toList());
            newOrderedGoals.addAll(complete);

            tmrGoals.setValue(newOrderedGoals);
        });

        goalRepository.findAllPending().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                    .sorted(Comparator.comparing(Goal::isComplete)
                            .thenComparingInt(Goal::getContextId)
                            .thenComparingInt(Goal::getSortOrder))
                    .filter(goal -> goal.getContextId() == context)
                    .collect(Collectors.toList());

            pendingGoals.setValue(newOrderedGoals);
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

    public Subject<List<Goal>> getTmrGoals() {
        return tmrGoals;
    }

    public Subject<List<Goal>> getPendingGoals() {
        return pendingGoals;
    }

    public Subject<Integer> getFocusMode() {
        return focusMode;
    }

    public void setFocusMode(Integer context){
        focusMode.setValue(context);
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

    public int weekNumber(){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        int daysUntilFirstOccurrence = today.getDayOfWeek().getValue() - firstDayOfMonth.getDayOfWeek().getValue();
        if(daysUntilFirstOccurrence < 0){
            daysUntilFirstOccurrence += 7;
        }
        int weeks = (today.getDayOfMonth() - daysUntilFirstOccurrence + 6) / 7;
        return weeks;
    }
}
