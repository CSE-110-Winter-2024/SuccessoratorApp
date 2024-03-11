package edu.ucsd.cse110.successorator;

        import static androidx.test.espresso.Espresso.onView;
        import static org.hamcrest.Matchers.containsString;
        import static org.junit.Assert.assertEquals;

        import java.time.LocalDate;
        import java.time.LocalDateTime;

        import edu.ucsd.cse110.successorator.databinding.TomorrowGoalBinding;
        import edu.ucsd.cse110.successorator.lib.domain.Date;
        import edu.ucsd.cse110.successorator.ui.goal.GoalListAdapter;
        import edu.ucsd.cse110.successorator.ui.goal.TomorrowGoalListFragment;
        import static androidx.test.espresso.action.ViewActions.click;
        import static androidx.test.espresso.action.ViewActions.typeText;
        import static androidx.test.espresso.assertion.ViewAssertions.matches;
        import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static androidx.test.espresso.matcher.ViewMatchers.withId;
        import static androidx.test.espresso.matcher.ViewMatchers.withText;
        import androidx.test.ext.junit.rules.ActivityScenarioRule;
        import androidx.test.ext.junit.runners.AndroidJUnit4;
        import org.junit.Assert;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SwitchViewsUITest extends TomorrowGoalListFragment{
    private MainViewModel activityModel;
    private TomorrowGoalBinding view;
    private GoalListAdapter adapter;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    public SwitchViewsUITest() {
    }


    @Test
    public void displaysTomorrowFragment() {
        /* Switch VIEW To Tomorrow */

        /* As a User I want to be able to click on the dropdown bar */
        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());

        /* So that I can add goals for tomorrow */
        onView(withId(R.id.radioButton3)).perform(click()); //'Press The Option For Tomorrow'
        onView(withText("GO TO")).perform((click()));

        //todo: Check if the date has changed
// Today Fragment: "Today, Sunday 3/10" -> Tomorrow Fragment: Tomorrow, "Monday 3/11"
        onView(withId(R.id.date_text))
                .check(matches(withText(containsString("Tomorrow")))); //Must be on Tomorrow page


        /* Add a Goal: "Prepare for Midterm */
       onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
       onView(withId(R.id.addGoalText)).perform(typeText("Prepare for midterm"));
        onView(withText("Create")).perform((click()));

        /* Additional testing: Checks if Prepare for midterm is on the list */
        onView(withText("Prepare for midterm")).check(matches(isDisplayed()));


        //todo: check for pending changes

        /* Switch VIEW To Pending */

        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        /* Press The Option For Pending Goals */
        onView(withId(R.id.radioButton4)).perform(click());
        onView(withText("GO TO")).perform((click()));
        onView(withId(R.id.date_text))
                .check(matches(withText(containsString("Pending")))); //Must be on pending page

        /* Add new goal */
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Write test for CSE 110"));
        onView(withText("Create")).perform((click()));
        /* Checks if Prepare for midterm is on the list */
        onView(withText("Write test for CSE 110")).check(matches(isDisplayed()));




        //Switch VIEW BACK To Today

        /* Checks if Prepare for midterm is on the list */
        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        /* Press The Option For Tomorrow */
        onView(withId(R.id.radioButton3)).perform(click());
        onView(withText("GO TO")).perform((click()));
        onView(withId(R.id.date_text))
                .check(matches(withText(containsString("Tomorrow")))); //Checks for Tomorrow
        /* Add a Goal: "Prepare for Midterm */
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Add a Recurring Goal"));
        onView(withText("Create")).perform((click()));
        onView(withText("Add a Recurring Goal")).check(matches(isDisplayed()));


        //todo: Check for recurring changes

        //Switch VIEW To Recurring
        /* Checks if Prepare for midterm is on the list */
        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        /* Press The Option For Tomorrow */
        onView(withId(R.id.radioButton5)).perform(click());
        onView(withText("GO TO")).perform((click()));
        onView(withId(R.id.date_text))
                .check(matches(withText(containsString("Recurring")))); //Checks for recurring


        //Switch VIEW BACK To Today
        /* Checks if Prepare for midterm is on the list */
        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        /* Press The Option For Tomorrow */
        onView(withId(R.id.radioButton2)).perform(click());
        onView(withText("GO TO")).perform((click()));
        onView(withId(R.id.date_text))
                .check(matches(withText(containsString("Today")))); //Checks for Today

    }

}
