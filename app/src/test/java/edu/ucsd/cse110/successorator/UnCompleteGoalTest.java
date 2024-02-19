package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;

public class UnCompleteGoalTest {
    /*
        Only One Completed Goal: Given that the only goal “Prepare for midterm”
        has been marked as completed, when Jessica taps on “Prepare for midterm”
        then the strikethrough on “Prepare for midterm” is removed.
     */
    @Test
    public void oneCompletedGoal(){
        //Given...
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoal(new Goal("Prepare for midterm", 1, true, 2));
        SimpleGoalRepository repo = new SimpleGoalRepository(dataSource);
        MainViewModel model = new MainViewModel(repo);

        //When...
        // * tap occurs *
        Goal tapped = dataSource.getGoal(1);
        Goal newGoal = tapped.withComplete(!tapped.isComplete());
        model.save(newGoal);

        //Then...
        // * text gets un-strikethrough *
        Goal expected = new Goal("Prepare for midterm", 1, false, 1);
        Goal actual = dataSource.getGoal(1);
        assertEquals(expected, actual);

        int expectedSize = 1;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }

    /*
        Completed and Uncompleted Goals: Given that the goal “Prepare for midterm”
        has been marked as completed and the goal “Grocery shopping” has not been
        marked as completed, when Jessica taps on “Prepare for midterm” then the
        strikethrough on “Prepare for midterm” is removed and “Prepare for midterm”
        is moved above “Grocery shopping”.
     */
    @Test
    public void mixedGoals(){
        //Given...
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, true, 3),
                new Goal("Grocery shopping", 2, false, 2)));
        SimpleGoalRepository repo = new SimpleGoalRepository(dataSource);
        MainViewModel model = new MainViewModel(repo);

        //When...
        // * tap occurs *
        Goal tapped = dataSource.getGoal(1);
        Goal newGoal = tapped.withComplete(!tapped.isComplete());
        model.save(newGoal);

        //Then...
        // * text gets un-strikethrough *
        Goal expected = new Goal("Prepare for midterm", 1, false, 1);
        Goal actual = dataSource.getGoal(1);
        assertEquals(expected, actual);
        expected = new Goal("Grocery shopping", 2, false, 3);
        actual = dataSource.getGoal(2);
        assertEquals(expected, actual);

        int expectedSize = 2;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }

    /*
        Only Uncompleted Goals: Given that the goal “Prepare for midterm” has been
        marked as completed and the goal “Grocery shopping” has been marked as
        completed, when Jessica taps on “Grocery shopping” then the strikethrough
        on “Grocery shopping” is removed and “Grocery shopping” is moved above
        “Prepare for midterm”.
     */
    @Test
    public void uncompletedGoals(){
        //Given...
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, true, 2),
                new Goal("Grocery shopping", 2, true, 4)));
        SimpleGoalRepository repo = new SimpleGoalRepository(dataSource);
        MainViewModel model = new MainViewModel(repo);

        //When...
        // * tap occurs *
        Goal tapped = dataSource.getGoal(2);
        Goal newGoal = tapped.withComplete(!tapped.isComplete());
        model.save(newGoal);

        //Then...
        // * text gets un-strikethrough *
        Goal expected = new Goal("Prepare for midterm", 1, true, 3);
        Goal actual = dataSource.getGoal(1);
        assertEquals(expected, actual);
        expected = new Goal("Grocery shopping", 2, false, 1);
        actual = dataSource.getGoal(2);
        assertEquals(expected, actual);

        int expectedSize = 2;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }
}
