package edu.ucsd.cse110.successorator.lib.domain;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Objects;




public class GoalTest {
    private GoalRepository mockGoal;
    @Test
    public void testGetters() {
        Goal goal = new Goal("Do Dishes", 1,false, 0, "Today", -1, 1);
        assertEquals((Integer)1, goal.getId());
        assertEquals("Do Dishes", goal.getTitle());
        assertFalse(goal.isComplete());
        assertEquals((Integer)0, goal.getSortOrder());
        assertEquals("Today", goal.getState());
        assertEquals((Integer)(-1), goal.getRecurringId());
        assertEquals((Integer)1, goal.getContextId());
    }

    @Test
    public void testEquals(){
        Goal goal1 = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal goal2 = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal goal3 = new Goal("Don't study", 1, true, 1,"Today", -1, 1);
        assertEquals(goal1, goal2);
        assertNotEquals(goal1, goal3);
        assertNotEquals(goal2, goal3);
    }

    @Test
    public void testWithId(){
        Goal goal = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal expected = new Goal("Study midterm", 2, true, 1,"Today", -1, 1);
        Goal actual = goal.withId(2);

        assertEquals(expected, actual);

    }

    @Test
    public void testWithSortOrder(){
        Goal goal = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal expected = new Goal("Study midterm", 1, true, 5,"Today", -1, 1);
        Goal actual = goal.withSortOrder(5);

        assertEquals(expected, actual);
    }


    @Test
    public void testWithComplete(){
        Goal goal = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal expected = new Goal("Study midterm", 1, false, 1,"Today", -1, 1);
        Goal actual = goal.withComplete(false);

        assertEquals(expected, actual);
    }

    @Test
    public void testWithState(){
        Goal goal = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal expected = new Goal("Study midterm", 1, true, 1,"Pending", -1, 1);
        Goal actual = goal.withState("Pending");

        assertEquals(expected, actual);
    }

    @Test
    public void testWithRecurringID(){
        Goal goal = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal expected = new Goal("Study midterm", 1, true, 1,"Today", 3, 1);
        Goal actual = goal.withRecurringId(3);

        assertEquals(expected, actual);
    }

    @Test
    public void testWithContextID(){
        Goal goal = new Goal("Study midterm", 1, true, 1,"Today", -1, 1);
        Goal expected = new Goal("Study midterm", 1, true, 1,"Today", -1, 3);
        Goal actual = goal.withContextId(3);

        assertEquals(expected, actual);
    }


}
