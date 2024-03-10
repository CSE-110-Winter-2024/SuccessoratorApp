package edu.ucsd.cse110.successorator;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.Constants;

public class CopyRecurringGoalTest {
    private InMemoryDataSource dataSource;
    private SimpleGoalRepository repo;
    private MainViewModel model;
    private TimeKeeper timeKeeper;
    private Date date;
    private Date logDate;
    private LocalDate startDate;
    private LocalDate nextDate;

    @Before
    public void setUp() {
        dataSource = new InMemoryDataSource();
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 12, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
    }

    @Test
    public void testCopyRecurringGoalToToday() {
        startDate = LocalDate.of(2024, 2, 13);
        nextDate = LocalDate.of(2024, 2, 20);
        dataSource.putRecurringGoal(new RecurringGoal("Prepare for midterm", 1, Constants.WEEKLY, startDate));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(1, dataSource.getGoals().size());

        //Verify nextDate updated
        var actual = dataSource.getRecurringGoals().get(0);
        assertEquals(nextDate, actual.getNextDate());

        //Verify correct goal copied
        var addedGoal = dataSource.getGoals().get(0);
        assertEquals("Prepare for midterm", actual.getTitle());
        assertEquals(Integer.valueOf(0), addedGoal.getId());
        assertEquals(Integer.valueOf(1), addedGoal.getSortOrder());
        assertEquals(Constants.TODAY, addedGoal.getState());
        assertEquals(Integer.valueOf(1), addedGoal.getRecurringId());
    }

    @Test
    public void testDoNotCopyRecurringGoalToToday() {
        startDate = LocalDate.of(2024, 2, 14);
        dataSource.putRecurringGoal(new RecurringGoal("Prepare for midterm", 1, Constants.WEEKLY, startDate));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 12, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 11, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(0, dataSource.getGoals().size());

        //Verify nextDate not updated
        var actual = dataSource.getRecurringGoals().get(0);
        assertEquals(startDate, actual.getNextDate());
    }

    @Test
    public void testCopyRecurringGoalToTodayAndTomorrow() {
        startDate = LocalDate.of(2024, 2, 13);
        nextDate = LocalDate.of(2024, 2, 15);
        dataSource.putRecurringGoal(new RecurringGoal("Prepare for midterm", 1, Constants.DAILY, startDate));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 12, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(2, dataSource.getGoals().size());

        //Verify nextDate updated
        var actual = dataSource.getRecurringGoals().get(0);
        assertEquals(nextDate, actual.getNextDate());

        //Verify correct goal was copied
        var addedToday = dataSource.getGoals().get(0);
        assertEquals("Prepare for midterm", addedToday.getTitle());
        assertEquals(Integer.valueOf(0), addedToday.getId());
        assertEquals(Constants.TODAY, addedToday.getState());
        assertEquals(Integer.valueOf(1), addedToday.getSortOrder());
        assertEquals(Integer.valueOf(1), addedToday.getRecurringId());

        var addedTomorrow = dataSource.getGoals().get(1);
        assertEquals("Prepare for midterm", addedTomorrow.getTitle());
        assertEquals(Integer.valueOf(1), addedTomorrow.getId());
        assertEquals(Constants.TOMORROW, addedTomorrow.getState());
        assertEquals(Integer.valueOf(2), addedTomorrow.getSortOrder());
        assertEquals(Integer.valueOf(1), addedTomorrow.getRecurringId());
    }

    @Test
    public void testCopyRecurringGoalToTomorrow() {
        startDate = LocalDate.of(2024, 2, 8);
        nextDate = LocalDate.of(2024, 2, 15);
        LocalDate newNextDate = LocalDate.of(2024, 2, 22);
        dataSource.putRecurringGoal(new RecurringGoal("Prepare for midterm", 1, Constants.WEEKLY, startDate, nextDate));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(1, dataSource.getGoals().size());

        //Verify nextDate updated
        var actual = dataSource.getRecurringGoals().get(0);
        assertEquals(newNextDate, actual.getNextDate());

        //Verify correct goal copied
        var addedGoal = dataSource.getGoals().get(0);
        assertEquals("Prepare for midterm", actual.getTitle());
        assertEquals(Integer.valueOf(0), addedGoal.getId());
        assertEquals(Integer.valueOf(1), addedGoal.getSortOrder());
        assertEquals(Constants.TOMORROW, addedGoal.getState());
        assertEquals(Integer.valueOf(1), addedGoal.getRecurringId());
    }
}
