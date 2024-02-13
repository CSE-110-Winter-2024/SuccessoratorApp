package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoalTest {
    @Test
    public void testGetters() {
        Goal goal = new Goal("Do Dishes", 1,false, 0);
        assertEquals((Integer)1, goal.getId());
        assertEquals("Do Dishes", goal.getTitle());
        assertFalse(goal.isComplete());
        assertEquals((Integer)0, goal.getSortOrder());
    }
}