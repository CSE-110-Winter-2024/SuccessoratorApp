package edu.ucsd.cse110.successorator;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.Constants;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * BDD Scenarios for US7
 */
public class TomorrowGoalsTest {
    private InMemoryDataSource dataSource;
    private SimpleGoalRepository repo;
    private MainViewModel model;
    private TimeKeeper timeKeeper;

    @Before
    public void setUp() {
        // Initialize your data source and repository before each test
        dataSource = new InMemoryDataSource();
        repo = new SimpleGoalRepository(dataSource);
        timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 3, 11, 2, 21));
        model = new MainViewModel(repo, timeKeeper);
    }

//    Adding goals to empty Tomorrow view: Given
//    that there are no goals on the Tomorrow page,
//    when Jessica presses the “+”, then the pop-up box
//    displays, when Jessica types in “Turn in paper”
//    and selects the “one-time” button, then the goal
//    “Turn in paper” appears on the list.
    @Test
    public void emptyTomorrow(){
        Goal newGoal = new Goal("Turn in paper", 1, false, 1, Constants.TOMORROW, -1, 1);
        model.addGoal(newGoal);

        var tmrList = model.getTmrGoals().getValue();
        int expectedSize = 1;
        int actualSize = tmrList.size();
        assertEquals(expectedSize, actualSize);

        assertEquals(tmrList.get(0), newGoal);
    }

//    Adding goals to existing Tomorrow view:
//    Given that there is the goal “Turn in paper”
//    on the Tomorrow page, when Jessica presses the “+”,
//    then the pop-up box displays, when Jessica types
//    in “Wash dishes” and selects the “one-time” button,
//    then the goal “Wash dishes” appears on the list
//    below “Turn in paper.”
    @Test
    public void existingTomorrow(){
        dataSource.putGoals(List.of(
                new Goal("Turn in paper", 1, false, 1, Constants.TOMORROW, -1, 1)));

        Goal newGoal = new Goal("Wash dishes", 2, false, 2, Constants.TOMORROW, -1, 1);
        model.addGoal(newGoal);

        var tmrList = model.getTmrGoals().getValue();
        int expectedSize = 2;
        int actualSize = tmrList.size();
        assertEquals(expectedSize, actualSize);

        assertEquals(tmrList.get(1), newGoal);

    }

}
