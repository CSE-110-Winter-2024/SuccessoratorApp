package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import androidx.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Constants;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class InMemoryDataSourceTest {
    InMemoryDataSource dataSource;

    @Before
    public void initObjects(){
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(new Goal("1", 1, false, 1,"Today", -1, 1),
                new Goal("2", 2, false, 2,"Today", -1, 1),
                new Goal("3", 3, false, 3,"Today", -1, 1),
                new Goal("4", 4, true, 4,"Today", -1, 1),
                new Goal("5", 5, true, 5,"Tomorrow", -1, 1),
                new Goal("6", 6, true, 6,"Tomorrow", -1, 1),
                new Goal("7", 7, true, 7,"Pending", -1, 1),
                new Goal("8", 8, true, 8,"Pending", -1, 1),
                new Goal("9", 9, true, 9,"Pending", -1, 1)
        ));
        //dataSource = InMemoryDataSource.fromDefault();
    }
    @Test
    public void testGetters(){
        List<Goal> expected = List.of(new Goal("1", 1, false, 1,"Today", -1, 1),
                new Goal("2", 2, false, 2,"Today", -1, 1),
                new Goal("3", 3, false, 3,"Today", -1, 1),
                new Goal("4", 4, true, 4,"Today", -1, 1)
        );
        List<Goal> actual = dataSource.getTodayGoals();
        assertEquals(expected, actual);

        Goal expected1 = new Goal("2", 2, false, 2,"Today", -1, 1);
        Goal actual1 = dataSource.getGoal(2);
        assertEquals(expected1, actual1);

        int expected2 = 9;
        int actual2 = dataSource.getMaxSortOrder();
        assertEquals(expected2, actual2);

        int expected3 = 3;
        int actual3 = dataSource.getMaxSortOrderInComplete();
        assertEquals(expected3, actual3);

        int expected4 = 1;
        int actual4 = dataSource.getMinSortOrder();
        assertEquals(expected4, actual4);

        expected = List.of(new Goal("5", 5, true, 5,"Tomorrow", -1, 1),
                new Goal("6", 6, true, 6,"Tomorrow", -1, 1)
        );
        actual = dataSource.getTomorrowGoals();
        assertEquals(expected, actual);

        expected = List.of(new Goal("7", 7, true, 7,"Pending", -1, 1),
                new Goal("8", 8, true, 8,"Pending", -1, 1),
                new Goal("9", 9, true, 9,"Pending", -1, 1)
        );
        actual = dataSource.getPendingGoals();
        assertEquals(expected, actual);

    }

    @Test
    public void testPutGoalAndPutGoals(){
        dataSource.putGoal(new Goal("10", 10, true, 10,"Today", -1, 1));
        int expectedSize = 10;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);

        Goal expectedGoal = new Goal("10", 10, true, 10,"Today", -1, 1);
        Goal actualGoal = dataSource.getGoal(10);
        assertEquals(expectedGoal, actualGoal);

        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,"Today", -1, 1),
                new Goal("Grocery shopping", 2, true, 2,"Today", -1, 1)));
        expectedSize = 2;
        actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testTmrGetters(){
        dataSource.putGoals(List.of(new Goal("6", 6, false, 6,Constants.TOMORROW, -1),
                new Goal("7", 7, false, 7, Constants.TOMORROW, -1)
        ));

        List<Goal> expected = List.of(
                new Goal("6", 6, false, 6,Constants.TOMORROW, -1),
                new Goal("7", 7, false, 7, Constants.TOMORROW, -1)
        );

        List<Goal> actual = dataSource.getTmrGoals();
        assertEquals(expected, actual);
    }

    @Test
    public void testTmrPutGoalAndPutGoals(){
        dataSource.putGoal(new Goal("6", 6, true, 6, Constants.TOMORROW, -1));
        int expectedSize = 1;
        int actualSize = dataSource.getTmrGoals().size();
        assertEquals(expectedSize, actualSize);

        Goal expectedGoal = new Goal("6", 6, true, 6, Constants.TOMORROW, -1);
        Goal actualGoal = dataSource.getGoal(6);
        assertEquals(expectedGoal, actualGoal);

        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(
                new Goal("Prepare for midterm", 1, false, 1,Constants.TOMORROW, -1),
                new Goal("Grocery shopping", 2, true, 2,Constants.TOMORROW, -1)));
        expectedSize = 2;
        actualSize = dataSource.getTmrGoals().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testShiftSortOrders(){
        dataSource.shiftSortOrders(1, dataSource.getMaxSortOrder(), 1);
        int count = 2;
        for(Goal goal : dataSource.getGoals()){
            int expected = count++;
            int actual = goal.getSortOrder();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testRemove(){
        dataSource.removeGoal(2);
        int expectedSize = 8;
        int actualSize = dataSource.getGoals().size();
        assertEquals(expectedSize, actualSize);
    }
}
