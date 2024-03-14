package edu.ucsd.cse110.successorator.lib.domain;

import edu.ucsd.cse110.successorator.lib.util.Constants;

public class GoalFactory {
    public Goal goalFromRecurring(RecurringGoal goal, String state) {
        return new Goal(goal.getTitle(), null, false, -1, state, goal.getId(), goal.getContextId());
    }
}
