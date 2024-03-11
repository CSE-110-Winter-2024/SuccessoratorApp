package edu.ucsd.cse110.successorator;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AddGoalUnitTests {
    @Test
    public void testAddFirstGoal() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Goal1", 0, false, 1,"Today", -1, 1),
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, false, 3,"Today", -1, 1),
                new Goal("Goal4", 3, false, 4,"Today", -1, 1)
        );
        var repo = new SimpleGoalRepository(dataSource);
        TimeKeeper timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        var model = new MainViewModel(repo, timeKeeper);

        var expected = new Goal("Goal1", 0, false, 1,"Today", -1, 1);
        model.addGoal(expected);
        assertEquals(expected, model.getOrderedGoals().getValue().get(0));
    }

    @Test
    public void testAddToListOfGoals() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Prepare for midterm", 0, false, 1,"Today", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);
        var timeKeeper = new SimpleTimeKeeper();
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        var model = new MainViewModel(repo, timeKeeper);

        var addGoal = new Goal("Grocery shopping", 1, false, 2,"Today", -1, 1);
        model.addGoal(addGoal);

        List<Goal> expected = List.of(

                new Goal("Prepare for midterm", 0, false, 1,"Today", -1, 1),
                new Goal("Grocery shopping", 1, false, 2,"Today", -1, 1)
        );

        for(int index = 0; index < expected.size(); index++){
            assertEquals(expected.get(index).getTitle(), model.getOrderedGoals().getValue().get(index).getTitle());
            assertEquals(expected.get(index).getId(), model.getOrderedGoals().getValue().get(index).getId());
            assertEquals(expected.get(index).isComplete(), model.getOrderedGoals().getValue().get(index).isComplete());
            assertEquals(expected.get(index).getSortOrder(), model.getOrderedGoals().getValue().get(index).getSortOrder());
        }
    }


}