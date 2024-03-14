package edu.ucsd.cse110.successorator;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.lib.domain.TimeKeeper;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import android.util.Log;

public class FocusGoalTest {
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
        timeKeeper.setDateTime(LocalDateTime.of(2024, 3, 13, 8, 10));
        model = new MainViewModel(repo, timeKeeper);
    }

    @Test
    public void AddAllContextThenTestEachFocusMode() {
        //Give that I want to add a goal AND order by context
        model.addGoal(new Goal("Clean room", 1, false, 1, "Today", -1, 1));
        model.addGoal(new Goal("Wake up at 9am", 2, false, 2, "Today", 1, 2));
        model.addGoal(new Goal("Clock In", 3, false, 3, "Today", 1, 2));
        model.addGoal(new Goal("Clock Out", 4, false, 4, "Today", 1, 2));
        model.addGoal(new Goal("Write test", 5, false, 5, "Today", -1, 3));
        model.addGoal(new Goal("Debug code", 6, false, 6, "Today", 2, 3));
        model.addGoal(new Goal("Prepare for midterm", 7, false, 7, "Today", 2, 3));
        model.addGoal(new Goal("Buy groceries", 8, false, 8, "Today", 2, 4));
        //WHEN I click on Focus Mode and select the Home context
        model.setFocusMode(1); // Focus Mode set to Home
        List<Goal> orderedGoals = model.getOrderedGoals().getValue();
        int[] GoalsCount = countGoalsByContextId(orderedGoals, 1,2,3,4);
        //THEN I should only get the home goals
        assertEquals("Testing for Home size:", 1, GoalsCount[1]);

        //WHEN I click on Focus Mode and select the Work context
        model.setFocusMode(2); // Focus Mode set to Work
        orderedGoals = model.getOrderedGoals().getValue();
        GoalsCount = countGoalsByContextId(orderedGoals, 1,2,3,4);
        //THEN I should only get the Work goals
        assertEquals("Testing for Work size:", 3, GoalsCount[2]);
        assertEquals("Testing for Home size:", 0, GoalsCount[1]);

        //WHEN I click on Focus Mode and select the School context
        model.setFocusMode(3); // Focus Mode set to Work
        orderedGoals = model.getOrderedGoals().getValue();
        GoalsCount = countGoalsByContextId(orderedGoals, 1,2,3,4);
        //THEN I should only get the School goals
        assertEquals("Testing for Errand size:", 0, GoalsCount[4]);
        assertEquals("Testing for Work size:", 0, GoalsCount[2]);
        assertEquals("Testing for School size:", 3, GoalsCount[3]);
        assertEquals("Testing for Home size:", 0, GoalsCount[1]);


        //WHEN I click on Focus Mode and select the School context
        model.setFocusMode(4); // Focus Mode set to Work
        orderedGoals = model.getOrderedGoals().getValue();
        GoalsCount = countGoalsByContextId(orderedGoals, 1,2,3,4);
        //THEN I should only get the Errand goals
        assertEquals("Testing for Errand size:", 1, GoalsCount[4]);
        assertEquals("Testing for Work size:", 0, GoalsCount[2]);
        assertEquals("Testing for School size:", 0, GoalsCount[3]);
        assertEquals("Testing for Home size:", 0, GoalsCount[1]);

        //WHEN I click on Focus Mode and select the CANCEL
        model.setFocusMode(0);
        orderedGoals = model.getOrderedGoals().getValue();
        GoalsCount = countGoalsByContextId(orderedGoals, 1,2,3,4);
        //THEN I should only get the Errand goals
        assertEquals("Testing for Home size:", 1, GoalsCount[1]);
        assertEquals("Testing for Work size:", 3, GoalsCount[2]);
        assertEquals("Testing for School size:", 3, GoalsCount[3]);
        assertEquals("Testing for Errand size:", 1, GoalsCount[4]);

    }
    @Test
    public void checkForID() {
        model.addGoal(new Goal("Buy Water", 4, false, 4, "Today", 1, 4));
        model.addGoal(new Goal("Wake up at 9am", 2, false, 2, "Today", 1, 2));
        model.addGoal(new Goal("Turn in HW", 3, false, 3, "Today", 1, 3));
        model.addGoal(new Goal("Clean room", 1, false, 1, "Today", -1, 1));

        assertEquals(0,model.getOrderedGoals().getValue().get(0).withContextId(0).getContextId().intValue());
        assertEquals(1,model.getOrderedGoals().getValue().get(1).withContextId(1).getContextId().intValue());
        assertEquals(2,model.getOrderedGoals().getValue().get(2).withContextId(2).getContextId().intValue());
        assertEquals(3,model.getOrderedGoals().getValue().get(3).withContextId(3).getContextId().intValue());

    }


    @Test
    public void checkForErrandThenSchool(){
        //Give that I want to add a goal AND select to add my goal inside the Errand Context and click on Monthly, 2nd of Month
        model.addGoal(new Goal("Write test", 1, false, 1, "Today", 1, 3));
        model.addGoal(new Goal("Prepare for midterm", 2, false, 2, "Today", 1, 3));
        model.addGoal(new Goal("Buy groceries", 3, false, 3, "Today", 1, 4));
        //When I press on Focus Mode menu option AND press the Errand Context
        model.setFocusMode(4);
        List<Goal> orderedGoals = model.getOrderedGoals().getValue();

        //User now changes to Errand Mode
        int[] ContextCount  = countGoalsByContextId(orderedGoals,1,2,3,4);
        //THEN I will be presented with ONLY the Errand Goals
        assertEquals(0,ContextCount[1]);
        assertEquals(0,ContextCount[2]);
        assertEquals(0,ContextCount[3]);
        assertEquals(1,ContextCount[4]); //Should only have one goal added

        //User now changes to School Mode
        model.setFocusMode(3);
        orderedGoals = model.getOrderedGoals().getValue();
        ContextCount  = countGoalsByContextId(orderedGoals,1,2,3,4);
        //Counts the size of context


        //Test to see it's the correct size is set for each ID
        assertEquals(0,ContextCount[1]);
        assertEquals(0,ContextCount[2]);
        assertEquals(2,ContextCount[3]); //Should only have 2 goals added
        assertEquals(0,ContextCount[4]);
    }

    @Test
    public void whenFocusModeIsSchool_thenOnlySchoolGoalsArePresent() {
        // Arrange
        model.addGoal(new Goal("Write test", 1, false, 1, "Today", 1, 3));
        model.addGoal(new Goal("Prepare for midterm", 2, false, 2, "Tomorrow", 1, 3));
        model.addGoal(new Goal("Buy groceries", 3, false, 3, "Tomorrow", 1, 4));
        model.setFocusMode(3); // Focus mode set to School


        // Act
        List<Goal> orderedGoals = model.getOrderedGoals().getValue();
        int[] GoalsCount = countGoalsByContextId(orderedGoals, 1,2,3,4);

        // Assert
        assertEquals("Only school goals for today should be present when focus mode is set to School", 1, GoalsCount[3]);
    }


    @Test
    public void checkForContextSize(){
        model.addGoal(new Goal("Write test", 1, false, 1, "Today", 1, 3));
        model.addGoal(new Goal("Prepare for midterm", 2, false, 2, "Tomorrow", 1, 3));
        model.addGoal(new Goal("Buy groceries", 3, false, 3, "Pending", 1, 4));


        List<Goal> orderedGoals = model.getOrderedGoals().getValue();
        //Counts the size of context
        int[] ContextCount  = countGoalsByContextId(orderedGoals,1,2,3,4);


        //Test to see it's the correct size is set for each ID
        assertEquals(0,ContextCount[1]);
        assertEquals(0,ContextCount[2]);
        assertEquals(1,ContextCount[3]);
        assertEquals(0,ContextCount[4]);
    }




    //Method that Counts the Number of Index
    private int[] countGoalsByContextId(List<Goal> goals, Integer home_contextId, Integer work_contextId, Integer school_contextId, Integer errand_contextId) {
        int[] size = new int[5];
        int home_count = 0, work_count  = 0, school_count = 0, errand_count = 0;
        for (Goal goal : goals) {
            if (goal.getContextId().equals(home_contextId)) {
                home_count++;
            }
            else if (goal.getContextId().equals(work_contextId)) {
                work_count++;
            }
            else if (goal.getContextId().equals(school_contextId)) {
                school_count++;
            }
            else if(goal.getContextId().equals(errand_contextId)) {
                errand_count++;
            }
        }
        size[1] = home_count;
        size[2] = work_count;
        size[3] = school_count;
        size[4] = errand_count;

        return size;
    }
}










