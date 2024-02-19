package edu.ucsd.cse110.successorator;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertEquals;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
public class AddGoalUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void displaysAddGoalFragment() {
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Prepare for midterm"));
        onView(withText("Create")).perform((click()));
        onView(withText("Prepare for midterm")).check(matches(isDisplayed()));
    }

    @Test
    public void displaysNoGoalAfterCancel() {
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Prepare for midterm"));
        onView(withText("Cancel")).perform((click()));
        onView(withText("Prepare for midterm")).check(doesNotExist());
    }

    @Test
    public void displaysMultipleGoals() {
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Prepare for midterm"));
        onView(withText("Create")).perform((click()));
        onView(withText("Prepare for midterm")).check(matches(isDisplayed()));

        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Grocery shopping"));
        onView(withText("Create")).perform((click()));
        onView(withText("Grocery shopping")).check(matches(isDisplayed()));
    }

    //todo: Test for voice dictation
}