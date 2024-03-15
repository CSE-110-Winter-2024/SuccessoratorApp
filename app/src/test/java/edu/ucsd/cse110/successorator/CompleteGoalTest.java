package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

public class CompleteGoalTest {
    /*
        Only One Goal: Given that there is one goal “Prepare for midterm”,
        when Jessica taps on “Prepare for midterm”, then the “Prepare for
        midterm text will be crossed out in strikethrough.
     */
    @Test
    public void onlyOneGoal(){
        //Given...
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoal(new Goal("Prepare for midterm", 1, false, 1,"Today", -1, 1));
        SimpleGoalRepository repo = new SimpleGoalRepository(dataSource);
        TimeKeeper timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        MainViewModel model = new MainViewModel(repo, timeKeeper);

        //When...
        // * tap occurs *
        Goal tapped = dataSource.getGoal(1);
        Goal newGoal = tapped.withComplete(!tapped.isComplete());
        model.save(newGoal);

        //Then...
        // * text gets marked as strikethrough *
        Goal expected = new Goal("Prepare for midterm", 1, true, 2,"Today", -1, 1);
        Goal actual = dataSource.getGoal(1);
        assertEquals(expected, actual);

        int expectedSize = 1;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }


    /*
        Multiple Goals: Given that there are two goals “Prepare for midterm” and
        “Grocery shopping”, when Jessica taps on “Prepare for midterm” to mark the
        goal as complete, then the “Prepare for midterm” text will be crossed out in
        strikethrough and moved to below “Grocery shopping”.
     */
    @Test
    public void multipleGoals(){
        //Given...
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,"Today", -1, 1),
                new Goal("Grocery shopping", 2, false, 2,"Today", -1, 1)));
        SimpleGoalRepository repo = new SimpleGoalRepository(dataSource);
        TimeKeeper timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        MainViewModel model = new MainViewModel(repo, timeKeeper);

        //When...
        // * tap on "Prepare for midterm" *
        Goal tapped = dataSource.getGoal(1);
        Goal newGoal = tapped.withComplete(!tapped.isComplete());
        model.save(newGoal);

        //Then...
        // * "Prepare for midterm" gets marked as strikethrough *
        // * Goals are rearranged *
        Goal expected = new Goal("Prepare for midterm", 1, true, 3,"Today", -1, 1);
        Goal actual = dataSource.getGoal(1);
        assertEquals(expected, actual);
        expected = new Goal("Grocery shopping", 2, false, 2,"Today", -1, 1);
        actual = dataSource.getGoal(2);
        assertEquals(expected, actual);

        int expectedSize = 2;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }


    /*
        Already Completed Goals: Given that there is one unfinished goal “Prepare for
        midterm” and one finished goal “Grocery shopping”, when Jessica taps on “Prepare
        for midterm”, then the “Prepare for midterm” text will be crossed out in strikethrough
        and put above “Grocery shopping”.
     */
    @Test
    public void alreadyCompletedGoals(){
        //Given...
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,"Today", -1, 1),
                new Goal("Grocery shopping", 2, true, 2,"Today", -1, 1)));
        SimpleGoalRepository repo = new SimpleGoalRepository(dataSource);
        TimeKeeper timeKeeper = new SimpleTimeKeeper();
        //timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        timeKeeper.setDateTime(LocalDateTime.now().plusHours(1));
        MainViewModel model = new MainViewModel(repo, timeKeeper);

        //When...
        // * tap on "Prepare for midterm" *
        Goal tapped = dataSource.getGoal(1);
        Goal newGoal = tapped.withComplete(!tapped.isComplete());
        model.save(newGoal);

        //Then...
        // * "Prepare for midterm" gets marked as strikethrough *
        // * Goals are rearranged *
        Goal expected = new Goal("Prepare for midterm", 1, true, 2,"Today", -1, 1);
        Goal actual = dataSource.getGoal(1);
        assertEquals(expected, actual);
        expected = new Goal("Grocery shopping", 2, true, 3,"Today", -1, 1);
        actual = dataSource.getGoal(2);
        assertEquals(expected, actual);

        int expectedSize = 2;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }

}
