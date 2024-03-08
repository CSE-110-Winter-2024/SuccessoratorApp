package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.lib.util.Constants;

public class RecurringGoalTest {
    private LocalDate startDate;
    private LocalDate nextDate;
    private LocalDate currDate;
    private RecurringGoal goal;
    private RecurringGoal goalDaily;
    private RecurringGoal goalWithNextDate;
    private RecurringGoal goalMonthly;
    @Before
    public void setUp() {
        startDate = LocalDate.of(2024, 2, 13);
        nextDate = LocalDate.of(2024, 2, 20);
        currDate = LocalDate.of(2024, 2, 16);
        goal = new RecurringGoal("Do dishes", 1, Constants.WEEKLY, startDate);
        goalDaily = new RecurringGoal("Do dishes", 1, Constants.DAILY, startDate);
        goalWithNextDate = new RecurringGoal("Do laundry", 1, Constants.WEEKLY, startDate, nextDate);
        goalMonthly = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate);
    }

    @Test
    public void testGetters() {
        assertEquals("Do dishes", goal.getTitle());
        assertEquals((Integer)1, goal.getId());
        assertEquals((Integer)2, goal.getFrequency());
        assertEquals(startDate, goal.getStartDate());
        assertEquals(startDate, goal.getNextDate());
    }

    @Test
    public void testWithId() {
        RecurringGoal expected = new RecurringGoal("Do dishes", 2, Constants.WEEKLY, startDate);
        assertEquals(expected, goal.withId(2));
    }

    @Test
    public void testWithNextDate() {
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.WEEKLY, startDate, nextDate);
        assertEquals(expected, goal.withNextDate(nextDate));
    }

    @Test
    public void testIsRecurTrue() {
        currDate = LocalDate.of(2024, 2, 21);
        assertTrue(goalWithNextDate.isRecur(currDate));

        assertTrue(goalWithNextDate.isRecur(currDate.minusDays(1)));
    }

    @Test
    public void testIsRecurFalse() {
        assertFalse(goalWithNextDate.isRecur(currDate));
    }

    @Test
    public void testUpdateNextDateDaily() {
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.DAILY, startDate, currDate.plusDays(1));
        assertEquals(expected, goalDaily.updateNextDate(currDate));
    }

    @Test
    public void testUpdateNextDateWeeklyCurrBeforeNext() {
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.WEEKLY, startDate, nextDate);
        assertEquals(expected, goal.updateNextDate(currDate));
    }

    @Test
    public void testUpdateNextDateWeeklyCurrAfterNext() {
        currDate = LocalDate.of(2024, 2, 19);
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.WEEKLY, startDate, nextDate);
        assertEquals(expected, goal.updateNextDate(currDate));

        assertEquals(expected, goal.updateNextDate(startDate));
    }

    @Test
    public void testUpdateNextDateYearly() {
        goal = new RecurringGoal("Do dishes", 1, Constants.YEARLY, startDate);
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.YEARLY, startDate, startDate.plusYears(2));
        assertEquals(expected, goal.updateNextDate(currDate.plusYears(1)));

        assertEquals(expected.withNextDate(startDate.plusYears(1)),
                goal.updateNextDate(currDate.plusYears(1).minusMonths(1)));
    }

    @Test
    public void testUpdateNextDateMonthlyNextMonth() {
        nextDate = LocalDate.of(2024, 3, 12);
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate, nextDate);
        assertEquals(expected, goalMonthly.updateNextDate(currDate));

        //nextDate is first date of month
        startDate = LocalDate.of(2024, 2, 2);
        nextDate = LocalDate.of(2024, 3, 1);
        currDate = LocalDate.of(2024, 2, 16);
        goal = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate);
        expected = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate, nextDate);
        assertEquals(expected, goal.updateNextDate(currDate));
    }

    @Test
    public void testUpdateNextDateMonthlyCurrMonth() {
        startDate = LocalDate.of(2024, 2, 4);
        nextDate = LocalDate.of(2024, 3, 3);
        currDate = LocalDate.of(2024, 3, 2);

        goal = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate);
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate, nextDate);
        assertEquals(expected, goal.updateNextDate(currDate));
    }

    @Test
    public void testUpdateNextDateMonthlyFifthWeek() {
        startDate = LocalDate.of(2024, 2, 29);
        nextDate = LocalDate.of(2024, 4, 4);
        currDate = LocalDate.of(2024, 3, 5);

        goal = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate);
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate, nextDate);
        assertEquals(expected, goal.updateNextDate(currDate));
    }

    @Test
    public void testUpdateNextDateMonthlyFifthWeekCurrBefore() {
        startDate = LocalDate.of(2024, 3, 29);
        nextDate = LocalDate.of(2024, 5, 3);
        currDate = LocalDate.of(2024, 4, 30);

        goal = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate);
        RecurringGoal expected = new RecurringGoal("Do dishes", 1, Constants.MONTHLY, startDate, nextDate);
        assertEquals(expected, goal.updateNextDate(currDate));
    }
}
