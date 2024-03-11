package edu.ucsd.cse110.successorator;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class TagGoalsTest {
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

//    Tagging Context: Given that there are no goals
//    on the Today page, when Jessica taps the plus
//    to add a new goal, then a pop up box displays
//    where there are four options with unique colors,
//    when Jessica types in “do laundry” and taps Home,
//    then there is a yellow dot to the left of
//    “do laundry”, when Jessica types in “fill out
//    timesheet” and taps Work, then there is a blue dot
//    to the left of “fill out timesheet”, when Jessica
//    types in “study for midterm” and taps School,
//    then there is a pink dot to the left of “study for
//    midterm”, when Jessica types in “get groceries” and
//    taps Errands, then there is a green dot to the
//    left of “get groceries”.
    @Test
    public void tagContext(){
        // Adds a new goal into the list
        Goal newGoal = new Goal("do laundry", 1, false, 1, "Today", -1, 1);
        model.addGoal(newGoal); //add goal to model
        Goal actual = dataSource.getGoal(1); //retrieve goal from dataSource

        // Verify the goal was added correctly
        assertEquals("do laundry", actual.getTitle());
        assertEquals(Integer.valueOf(1), actual.getId());
        assertEquals(Integer.valueOf(1), actual.getSortOrder());
        assertEquals(Integer.valueOf(1), actual.getContextId());

        // Adds a Second new goal into the list
        Goal newGoal2 = new Goal("fill out timesheet", 2, false, 2, "Today", -1, 2);
        model.addGoal(newGoal2); //add goal to model

        Goal newGoal3 = new Goal("study for midterm", 3, false, 3, "Today", -1, 3);
        model.addGoal(newGoal3);

        Goal newGoal4 = new Goal("get groceries", 4, false, 4, "Today", -1, 4);
        model.addGoal(newGoal4);

        // Verify the size of the goal list in the data source
        int expectedSize = 4; // Initial 2 goals + 1 new goal
        int actualSize = dataSource.getTodayGoals().size();
        assertEquals(expectedSize, actualSize);
    }

    //    Sorting Goals w/ Context: Given that
    //    there are four goals, “do laundry”, “fill out
    //    timesheet”, “study for midterm”, and “get groceries”
    //    for Today,  when Jessica opens the Today view,
    //    then the goals will be displayed in that order,
    //    when Jessica taps to complete “do laundry”, then
    //    the color dot turns gray and “do laundry” gets
    //    struck through and moves to the bottom, when
    //    Jessica adds another Home tagged goal “do dishes”,
    //    then “do dishes” appears above the other goals.
    @Test
    public void sortGoalsContext(){
        dataSource.putGoals(List.of(
                new Goal("do laundry", 1, false, 1, "Today", -1, 1),
                new Goal("fill out timesheet", 2, false, 2, "Today", -1, 2),
                new Goal("study for midterm", 3, false, 3, "Today", -1, 3),
                new Goal("get groceries", 4, false, 4, "Today", -1, 4)
                ));


    }
}



