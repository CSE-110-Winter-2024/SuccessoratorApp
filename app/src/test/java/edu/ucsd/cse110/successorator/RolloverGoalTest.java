package edu.ucsd.cse110.successorator;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

public class RolloverGoalTest {
    private InMemoryDataSource dataSource;
    private SimpleGoalRepository repo;
    private MainViewModel model;
    private TimeKeeper timeKeeper;
    private Date date;
    private Date logDate;

    @Test
    public void testRolloverGoal() {
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,"Today", -1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
        dataSource.putGoal(new Goal("Text Maria", 2, true, 2,"Today", -1));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(1, dataSource.getGoals().size());


        //Verify correct goal was removed
        var actual = dataSource.getGoals().get(0);
        assertEquals("Prepare for midterm", actual.getTitle());
        assertEquals(Integer.valueOf(1), actual.getId());
        assertEquals(Integer.valueOf(1), actual.getSortOrder());
    }

    @Test
    public void testNoRolloverGoal() {
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,"Today", -1),
                new Goal("Text Maria", 2, false, 2,"Today", -1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 14, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(2, dataSource.getGoals().size());
    }

    @Test
    public void testRolloverGoalSameDay() {
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,"Today", -1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
        dataSource.putGoal(new Goal("Text Maria", 2, true, 2,"Today", -1));

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));
        logDate = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        logDate.setDate(LocalDateTime.of(2024, 2, 13, 12, 21));

        model.rollOverGoal(logDate, date);
        assertEquals(2, dataSource.getGoals().size());
    }
}
