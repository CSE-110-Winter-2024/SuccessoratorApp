package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import androidx.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class InMemoryDataSourceTest {
    InMemoryDataSource dataSource;
    @Before
    public void initObjects(){
        dataSource = new InMemoryDataSource();
        dataSource.putGoals(List.of(new Goal("1", 1, false, 1),
                new Goal("2", 2, false, 2),
                new Goal("3", 3, false, 3),
                new Goal("4", 4, true, 4),
                new Goal("5", 5, true, 5)
        ));
        //dataSource = InMemoryDataSource.fromDefault();
    }
    @Test
    public void testGetters(){
        List<Goal> expected = List.of(new Goal("1", 1, false, 1),
                new Goal("2", 2, false, 2),
                new Goal("3", 3, false, 3),
                new Goal("4", 4, true, 4),
                new Goal("5", 5, true, 5)
        );
        List<Goal> actual = dataSource.getGoals();
        assertEquals(expected, actual);

        Goal expected1 = new Goal("2", 2, false, 2);
        Goal actual1 = dataSource.getGoal(2);
        assertEquals(expected1, actual1);


    }
}
