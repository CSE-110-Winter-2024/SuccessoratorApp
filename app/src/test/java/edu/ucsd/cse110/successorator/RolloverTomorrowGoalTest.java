package edu.ucsd.cse110.successorator;

import static junit.framework.TestCase.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.Constants;

public class RolloverTomorrowGoalTest {
    private InMemoryDataSource dataSource;
    private SimpleGoalRepository repo;
    private MainViewModel model;
    private TimeKeeper timeKeeper;
    private Date date;
    private Date logDate;

    @Test
    public void testRolloverTmrGoal() {
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,Constants.TODAY, -1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
        dataSource.putGoals(List.of(
                new Goal("Tomorrow goal", 2, false, 2, Constants.TOMORROW, -1),
                new Goal("Completed tomorrow goal", 3, true, 3, Constants.TOMORROW, -1)
        ));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(3, dataSource.getGoals().size());


        //Verify correct goal was moved
        var tmrGoal = dataSource.getGoal(2);
        assertEquals("Tomorrow goal", tmrGoal.getTitle());
        assertEquals(Integer.valueOf(2), tmrGoal.getId());
        assertFalse(tmrGoal.isComplete());
        assertEquals(Integer.valueOf(3), tmrGoal.getSortOrder());
        assertEquals(Constants.TODAY, tmrGoal.getState());
        assertEquals(Integer.valueOf(-1), tmrGoal.getRecurringId());

        var completedtmrGoal = dataSource.getGoal(3);
        assertEquals("Completed tomorrow goal", completedtmrGoal.getTitle());
        assertEquals(Integer.valueOf(3), completedtmrGoal.getId());
        assertTrue(completedtmrGoal.isComplete());
        assertEquals(Integer.valueOf(5), completedtmrGoal.getSortOrder());
        assertEquals(Constants.TODAY, completedtmrGoal.getState());
        assertEquals(Integer.valueOf(-1), completedtmrGoal.getRecurringId());
    }

    @Test
    public void testNoRolloverTmrGoal() {
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,Constants.TODAY, -1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(1, dataSource.getGoals().size());
    }

    @Test
    public void testRolloverDuplicateRecurringGoal() {
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,Constants.TODAY, 1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 2, false, 2, Constants.TOMORROW, 1),
                new Goal("Other recurring goal", 3, false, 3, Constants.TOMORROW, 2)
        ));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        var test = !repo.existsRecurringId(dataSource.getGoal(3).getRecurringId(), Constants.TODAY);
        var test1 = repo.existsRecurringId(dataSource.getGoal(2).getRecurringId(), Constants.TODAY);

        model.rollOverGoal(logDate, date);
        assertEquals(2, dataSource.getGoals().size());


        //Verify correct goal was moved
        var completedtmrGoal = dataSource.getGoal(3);
        assertEquals("Other recurring goal", completedtmrGoal.getTitle());
        assertEquals(Integer.valueOf(3), completedtmrGoal.getId());
        assertFalse(completedtmrGoal.isComplete());
        assertEquals(Integer.valueOf(3), completedtmrGoal.getSortOrder());
        assertEquals(Constants.TODAY, completedtmrGoal.getState());
        assertEquals(Integer.valueOf(2), completedtmrGoal.getRecurringId());
    }
}
