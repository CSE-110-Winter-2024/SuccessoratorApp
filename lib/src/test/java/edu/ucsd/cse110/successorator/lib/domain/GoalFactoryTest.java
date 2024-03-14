package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.lib.util.Constants;

public class GoalFactoryTest {
    @Test
    public void testGoalFromRecurring() {
        LocalDate startDate = LocalDate.of(2024, 2, 13);
        RecurringGoal recurringGoal = new RecurringGoal("Do dishes", 1, Constants.WEEKLY, startDate, 1);
        GoalFactory goalFactory = new GoalFactory();
        Goal expected = new Goal("Do dishes", null, false, -1, Constants.TODAY, 1,1);
        assertEquals(expected, goalFactory.goalFromRecurring(recurringGoal, Constants.TODAY));
    }
}
