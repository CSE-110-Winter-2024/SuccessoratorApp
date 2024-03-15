package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

public class AddRecurringGoalTest {
    private InMemoryDataSource dataSource;
    private SimpleGoalRepository repo;
    private MainViewModel model;
    private TimeKeeper timeKeeper;

    //Set up dataSource, repo, and model
    @Before
    public void setUp() {
        // Initialize your data source and repository before each test
        dataSource = new InMemoryDataSource();
        dataSource.putRecurringGoals(List.of(
                new RecurringGoal("Prepare for midterm", 1, 1,
                        LocalDate.of(2024, 2, 13), 1),
                new RecurringGoal("Grocery shopping", 2, 1,
                        LocalDate.of(2024, 2, 13), 1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
    }

    //The testAddRecurringGoal adds newRecurringGoal "Text Maria"
    @Test
    public void testAddRecurringGoal() {
        // Adds a new recurring goal into the list
        RecurringGoal newRecurring = new RecurringGoal("Text Maria", 3, 1,
                LocalDate.of(2024, 2, 13), 1);
        model.addRecurring(newRecurring); //add recurring goal to model
        RecurringGoal actual = dataSource.getRecurringGoal(3); //retrieve recurring goal from dataSource

        // Verify the recurring goal was added correctly
        assertEquals("Text Maria", actual.getTitle());
        assertEquals(Integer.valueOf(3), actual.getId());
        assertEquals(Integer.valueOf(1), actual.getFrequency());
        assertEquals(LocalDate.of(2024, 2, 13), actual.getStartDate());
        assertEquals(Integer.valueOf(1), actual.getContextId());

        // Verify the size of the recurring goal list in the data source
        int expectedSize = 3; // Initial 2 recurring goals + 1 new recurring goal
        int actualSize = dataSource.getRecurringGoals().size();
        assertEquals(expectedSize, actualSize);
        //Done with first add

        // Adds a Second new recurring goal into the list
        RecurringGoal newRecurring2 = new RecurringGoal("Finish AddRecurringGoal Test", 4, 1,
                LocalDate.of(2024, 2, 13), 1);
        model.addRecurring(newRecurring2); //add recurring goal to model
        actual = dataSource.getRecurringGoal(4); //retrieve recurring goal from dataSource

        // Verify the goal was added correctly
        assertEquals("Finish AddRecurringGoal Test", actual.getTitle());
        assertEquals(Integer.valueOf(4), actual.getId());

        // Verify the size of the recurring goal list in the data source
        expectedSize = 4; // Initial 3 recurring goals + 1 new recurring goal
        actualSize = dataSource.getRecurringGoals().size();
        assertEquals(expectedSize, actualSize);
    }
}