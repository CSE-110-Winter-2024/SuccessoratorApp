package edu.ucsd.cse110.successorator;
import edu.ucsd.cse110.successorator.lib.State;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.Subject;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



/* Test for addGoal */
public class AddGoalTest {

    private InMemoryDataSource dataSource;
    private SimpleGoalRepository repo;
    private MainViewModel model;
    private TimeKeeper timeKeeper;

    //Set up dataSource, repo, and model
    @Before
    public void setUp() {
        // Initialize your data source and repository before each test
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1, "Today", -1),
                new Goal("Grocery shopping", 2, false, 2, "Today", -1)
        ));
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        model = new MainViewModel(repo, timeKeeper);
    }
//The testAddGoal adds newGoal "Text Maria"
    @Test
    public void testAddGoal() {
        // Adds a new goal into the list
        Goal newGoal = new Goal("Text Maria", 3, false, 3, "Today", -1);
        model.addGoal(newGoal); //add goal to model
        Goal actual = dataSource.getGoal(3); //retrieve goal from dataSource

        // Verify the goal was added correctly
        assertEquals("Text Maria", actual.getTitle());
        assertEquals(Integer.valueOf(3), actual.getId());
        assertEquals(Integer.valueOf(3), actual.getSortOrder());

        // Verify the size of the goal list in the data source
        int expectedSize = 3; // Initial 2 goals + 1 new goal
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
        //Done with first add


        // Adds a Second new goal into the list
        Goal newGoal2 = new Goal("Finish addGoal Test", 4, false, 4, "Today", -1);
        model.addGoal(newGoal2); //add goal to model
        actual = dataSource.getGoal(3); //retrieve goal from dataSource

        // Verify the goal was added correctly
        assertEquals("Text Maria", actual.getTitle());
        assertEquals(Integer.valueOf(3), actual.getId());
        assertEquals(Integer.valueOf(3), actual.getSortOrder());

        // Verify the size of the goal list in the data source
        expectedSize = 4; // Initial 2 goals + 1 new goal
        actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);

    }
    @Test
    public void testCompleteAndAdd() {

        Goal goalToComplete = dataSource.getGoal(1);
        Goal nG = goalToComplete.withComplete(!goalToComplete.isComplete());
        model.save(nG);
        //assertTrue(dataSource.getGoal(1).isComplete());

        // Adds a new goal into the list
        Goal newGoal = new Goal("Text Maria", 3, false, null, "Today", -1);
        model.addGoal(newGoal); //add goal to model
        Goal actual = dataSource.getGoal(3); //retrieve goal from dataSource

        // Verify the goal was added correctly
        assertEquals("Text Maria", actual.getTitle());
        assertEquals(Integer.valueOf(3), actual.getId());
        assertEquals(Integer.valueOf(3), actual.getSortOrder());

        // Verify the size of the goal list in the data source
        int expectedSize = 3; // Initial 2 goals + 1 new goal
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
        //Done with first add

        /*
         * Marking All goals ("Grocery shopping and Text Maria" as complete)
         */
        goalToComplete = dataSource.getGoal(2);
        nG = goalToComplete.withComplete(!goalToComplete.isComplete());
        model.save(nG);
        goalToComplete = dataSource.getGoal(3);
        nG = goalToComplete.withComplete(!goalToComplete.isComplete());
        model.save(nG);


        // Adds a fourth goal into the list
        newGoal = new Goal("Write addTest", 4, false, null, "Today", -1);
        model.addGoal(newGoal); //add goal to model
        actual = dataSource.getGoal(4); //retrieve goal from dataSource

        // Verify the goal was added correctly
        assertEquals("Write addTest", actual.getTitle());
        assertEquals(Integer.valueOf(4), actual.getId());
        assertEquals(Integer.valueOf(1), actual.getSortOrder());

        // Verify the size of the goal list in the data source
        expectedSize = 4; // Initial 2 goals + 1 new goal
        actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);


        // Adds a fourth goal into the list
        newGoal = new Goal("Push my progress", 5, false, null, "Today", -1);
        model.addGoal(newGoal); //add goal to model
        actual = dataSource.getGoal(5); //retrieve goal from dataSource

        // Verify the goal was added correctly
        assertEquals("Push my progress", actual.getTitle());
        assertEquals(Integer.valueOf(5), actual.getId());
        assertEquals(Integer.valueOf(2), actual.getSortOrder());

        // Verify the size of the goal list in the data source
        expectedSize = 5; // Initial 2 goals + 1 new goal
        actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }

}
