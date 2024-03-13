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
 * BDD Scenarios for US 8 and 13
 */
public class PendingGoalsTest {
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

    // Add Pending Goals: Given that there are no
    // goals in the Pending page, when Jessica presses
    // the plus button to add “Watch lecture” then
    // watch lecture appears on the page, when she
    // adds “Take out trash” then “take out trash
    // appears below “watch lecture”.
    @Test
    public void addPendingGoals(){
        Goal newGoal = new Goal("watch lecture", 1, false, 1, Constants.PENDING, -1, 1);
        model.addGoal(newGoal);
        var pendingList = model.getPendingGoals().getValue();

        int expectedSize = 1;
        int actualSize = pendingList.size();
        assertEquals(expectedSize, actualSize);

        assertEquals(pendingList.get(0), newGoal);

        newGoal = new Goal("take out trash", 2, false, 2, Constants.PENDING, -1, 1);
        model.addGoal(newGoal);
        pendingList = model.getPendingGoals().getValue();

        expectedSize = 2;
        actualSize = pendingList.size();
        assertEquals(expectedSize, actualSize);
    }

//    Modifying Pending Goals: Given I am on the
//    Pending page And there is a pending goal
//    “Do the Dishes” And today’s date is Mon, Feb 26
//    When I long press “Do the Dishes” Then I will see a
//    menu with the options: Move to Today, Move to
//    Tomorrow, Finish, and Delete When I select Move to
//    Today And navigate to the Today page Then I will see
//    Mon, Feb 26 And I will see “Do the Dishes” When
//    I select Move to Tomorrow And navigate to the
//    Tomorrow page Then I will see Tues, Feb 27 And I
//    will see “Do the Dishes” When I select Finish And
//    I navigate to the Today page Then I will see
//    “Do the Dishes” struck through (Piazza #425) When I
//    select Delete Then I will no longer see “Do the Dishes”
    @Test
    public void modifyPendingGoals(){
        dataSource.putGoals(List.of(
                new Goal("do dishes", 1, false, 1, Constants.PENDING, -1, 1)
        ));

        //Change to Today
        Goal pending = dataSource.getGoal(1).withState(Constants.TODAY);
        model.save(pending);
        var pendingList = model.getPendingGoals().getValue();
        int expectedSize = 0;
        int actualSize = pendingList.size();
        assertEquals(expectedSize, actualSize);

        var todayList = model.getOrderedGoals().getValue();
        expectedSize = 1;
        actualSize = todayList.size();
        assertEquals(expectedSize, actualSize);

        // ~ reset ~
        pending = dataSource.getGoal(1).withState(Constants.PENDING);
        model.save(pending);

        //Change to Tomorrow
        pending = dataSource.getGoal(1).withState(Constants.TOMORROW);
        model.save(pending);
        pendingList = model.getPendingGoals().getValue();
        expectedSize = 0;
        actualSize = pendingList.size();
        assertEquals(expectedSize, actualSize);

        var tomorrowList = model.getTmrGoals().getValue();
        expectedSize = 1;
        actualSize = tomorrowList.size();
        assertEquals(expectedSize, actualSize);

        // ~Reset ~
        pending = dataSource.getGoal(1).withState(Constants.PENDING);
        model.save(pending);

        //Change to Finish
        pending = dataSource.getGoal(1).withState(Constants.TODAY).withComplete(!pending.isComplete());
        model.save(pending);

        pendingList = model.getPendingGoals().getValue();
        expectedSize = 0;
        actualSize = pendingList.size();
        assertEquals(expectedSize, actualSize);

        todayList = model.getOrderedGoals().getValue();
        expectedSize = 1;
        actualSize = todayList.size();
        assertEquals(expectedSize, actualSize);

        // ~Reset ~
        pending = dataSource.getGoal(1).withState(Constants.PENDING);
        model.save(pending);

        //Delete
        model.remove(1);

        expectedSize = 0;

        pendingList = model.getPendingGoals().getValue();
        actualSize = pendingList.size();
        assertEquals(expectedSize, actualSize);

        todayList = model.getOrderedGoals().getValue();
        actualSize = todayList.size();
        assertEquals(expectedSize, actualSize);

        tomorrowList = model.getTmrGoals().getValue();
        actualSize = tomorrowList.size();
        assertEquals(expectedSize, actualSize);
    }
}
