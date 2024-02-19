package edu.ucsd.cse110.successorator.lib.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

/**
 * Class used as a sort of "database" of goals that exist. This
 * will be replaced with a real database in the future, but can also be used
 * for testing.
 */
public class InMemoryDataSource {
    private int nextId = 0;

    private int minSortOrder = Integer.MAX_VALUE;
    private int maxSortOrder = Integer.MIN_VALUE;

    private final Map<Integer, Goal> goals
            = new HashMap<>();
    private final Map<Integer, MutableSubject<Goal>> goalSubjects
            = new HashMap<>();
    private final MutableSubject<List<Goal>> allGoalsSubject
            = new SimpleSubject<>();

    public InMemoryDataSource() {
    }

    public final static List<Goal> DEFAULT_GOALS = List.of();

    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();
        data.putGoals(DEFAULT_GOALS);
        return data;
    }

    public List<Goal> getGoals() {
        return List.copyOf(goals.values());
    }

    public Goal getGoal(int id) {
        return goals.get(id);
    }

    public Subject<Goal> getGoalSubject(int id) {
        if (!goalSubjects.containsKey(id)) {
            var subject = new SimpleSubject<Goal>();
            subject.setValue(getGoal(id));
            goalSubjects.put(id, subject);
        }
        return goalSubjects.get(id);
    }

    public Subject<List<Goal>> getAllGoalsSubject() {
        return allGoalsSubject;
    }

    public int getMinSortOrder() {
        return minSortOrder;
    }

    public int getMaxSortOrder() {
        return maxSortOrder;
    }

    public void putGoal(Goal goal) {
        var fixedGoal = preInsert(goal);

        goals.put(fixedGoal.getId(), fixedGoal);
        postInsert();
        assertSortOrderConstraints();

        if (goalSubjects.containsKey(fixedGoal.getId())) {
            goalSubjects.get(fixedGoal.getId()).setValue(fixedGoal);
        }
        allGoalsSubject.setValue(getGoals());
    }

    public void putGoals(List<Goal> goalList) {
        var fixedGoals = goalList.stream()
                .map(this::preInsert)
                .collect(Collectors.toList());

        fixedGoals.forEach(goal -> goals.put(goal.getId(), goal));
        postInsert();
        assertSortOrderConstraints();

        fixedGoals.forEach(goal -> {
            if (goalSubjects.containsKey(goal.getId())) {
                goalSubjects.get(goal.getId()).setValue(goal);
            }
        });
        allGoalsSubject.setValue(getGoals());
    }

    public void removeGoal(int id) {
        var goal = goals.get(id);
        var sortOrder = goal.getSortOrder();

        goals.remove(id);
        shiftSortOrders(sortOrder, maxSortOrder, -1);

        if (goalSubjects.containsKey(id)) {
            goalSubjects.get(id).setValue(null);
        }
        allGoalsSubject.setValue(getGoals());
    }

    public void shiftSortOrders(int from, int to, int by) {
        var goalList = goals.values().stream()
                .filter(card -> card.getSortOrder() >= from && card.getSortOrder() <= to)
                .map(card -> card.withSortOrder(card.getSortOrder() + by))
                .collect(Collectors.toList());

        putGoals(goalList);
    }


    public int getMaxSortOrderInComplete(){
        var goalList = new ArrayList<>(goals.values());
        int maxSortOrderInComplete = 0;
        for(int i = 0; i < goalList.size(); i++){
            var goal = goalList.get(i);
            if(!goal.isComplete() && goal.getSortOrder() > maxSortOrderInComplete){
                maxSortOrderInComplete = goal.getSortOrder();
            }
        }
        return maxSortOrderInComplete;
    }

    /**
     * Private utility method to maintain state of the fake DB: ensures that new
     * cards inserted have an id, and updates the nextId if necessary.
     */
    private Goal preInsert(Goal card) {
        var id = card.getId();
        if (id == null) {
            // If the card has no id, give it one.
            card = card.withId(nextId++);
        }
        else if (id > nextId) {
            // If the card has an id, update nextId if necessary to avoid giving out the same
            // one. This is important for when we pre-load cards like in fromDefault().
            nextId = id + 1;
        }

        return card;
    }

    /**
     * Private utility method to maintain state of the fake DB: ensures that the
     * min and max sort orders are up to date after an insert.
     */
    private void postInsert() {
        // Keep the min and max sort orders up to date.
        minSortOrder = goals.values().stream()
                .map(Goal::getSortOrder)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);

        maxSortOrder = goals.values().stream()
                .map(Goal::getSortOrder)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
    }

    /**
     * Safety checks to ensure the sort order constraints are maintained.
     * <p></p>
     * Will throw an AssertionError if any of the constraints are violated,
     * which should never happen. Mostly here to make sure I (Dylan) don't
     * write incorrect code by accident!
     */
    private void assertSortOrderConstraints() {
        // Get all the sort orders...
        var sortOrders = goals.values().stream()
                .map(Goal::getSortOrder)
                .collect(Collectors.toList());

        // Non-negative...
        assert sortOrders.stream().allMatch(i -> i >= 0);

        // Unique...
        assert sortOrders.size() == sortOrders.stream().distinct().count();

        // Between min and max...
        assert sortOrders.stream().allMatch(i -> i >= minSortOrder);
        assert sortOrders.stream().allMatch(i -> i <= maxSortOrder);
    }
}
