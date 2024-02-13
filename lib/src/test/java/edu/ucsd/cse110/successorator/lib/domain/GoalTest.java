package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoalTest {
    @Test
    public void testGetters() {
        var goal = new Goal(1, "Do Dishes", false, 0);
        assertEquals(Integer.valueOf(1), goal.id());
        assertEquals("Do Dishes", goal.title());
        assertFalse(goal.isComplete());
        assertEquals(0, goal.sortOrder());
    }
}