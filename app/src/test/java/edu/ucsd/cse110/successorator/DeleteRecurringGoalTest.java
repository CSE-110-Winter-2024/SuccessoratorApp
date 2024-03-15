package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

public class DeleteRecurringGoalTest {
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

    //The testDeleteRecurringGoal deletes "Grocery Shopping" then "Prepare for midterm"
    @Test
    public void testDeleteRecurringGoal() {
        model.removeRecur(2);

        // Verify the size of the recurring goal list in the data source
        int expectedSize = 1; // Initial 2 recurring goals - 1 recurring goal
        int actualSize = dataSource.getRecurringGoals().size();
        assertEquals(expectedSize, actualSize);
        //Done with first delete

        // Delete remaining recurring goal
        model.removeRecur(1); //remove recurring goal from model

        // Verify the size of the recurring goal list in the data source
        expectedSize = 0; // Initial 1 recurring goal - 1 recurring goal
        actualSize = dataSource.getRecurringGoals().size();
        assertEquals(expectedSize, actualSize);
    }
}
