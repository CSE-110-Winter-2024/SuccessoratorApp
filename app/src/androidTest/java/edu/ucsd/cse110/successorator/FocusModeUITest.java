package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FocusModeUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void pickingFocusAndCanceling() {
        //Add goals to set up scenario
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Do laundry"));
        onView(withId(R.id.homeContext)).perform((click()));
        onView(withId(R.id.onetime)).perform((click()));
        onView(withText("Create")).perform((click()));

        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Study for midterm"));
        onView(withId(R.id.schoolContext)).perform((click()));
        onView(withId(R.id.onetime)).perform((click()));
        onView(withText("Create")).perform((click()));

        //confirm goals added
        onView(withText("Do laundry")).check(matches(isDisplayed()));
        onView(withText("Study for midterm")).check(matches(isDisplayed()));
        //press hamburger
        onView(withId(R.id.action_bar_menu_focus_mode)).perform(click());

        //confirm 4 modes visible
        onView(withId(R.id.homeFocus)).check(matches(isDisplayed()));
        onView(withId(R.id.workFocus)).check(matches(isDisplayed()));
        onView(withId(R.id.schoolFocus)).check(matches(isDisplayed()));
        onView(withId(R.id.errandFocus)).check(matches(isDisplayed()));

        //go to home focus mode
        onView(withId(R.id.homeFocus)).perform(click());
        onView(withText("OK")).perform(click());

        //check if Do laundry still visible and Study for midterm not visible
        onView(withText("Study for midterm")).check(doesNotExist());
        onView(withText("Do laundry")).check(matches(isDisplayed()));

        //press hamburger
        onView(withId(R.id.action_bar_menu_focus_mode_home)).perform(click());

        //cancel focus mode
        onView(withId(R.id.cancelFocus)).perform(click());
        onView(withText("OK")).perform(click());

        //confirm both goals show
        onView(withText("Do laundry")).check(matches(isDisplayed()));
        onView(withText("Study for midterm")).check(matches(isDisplayed()));
    }

    @Test
    public void switchPagesWithFocusMode(){
        //Add goals to set up scenario *SETUP*
        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Do laundry"));
        onView(withId(R.id.homeContext)).perform((click()));
        onView(withId(R.id.onetime)).perform((click()));
        onView(withText("Create")).perform((click()));

        //confirm goals added
        onView(withText("Do laundry")).check(matches(isDisplayed()));

        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        onView(withText("Tomorrow")).perform(click());
        onView(withText("GO TO")).perform(click());

        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Study"));
        onView(withId(R.id.schoolContext)).perform((click()));
        onView(withId(R.id.onetime)).perform((click()));
        onView(withText("Create")).perform((click()));

        onView(withId(R.id.action_bar_menu_add_goal)).perform(click());
        onView(withId(R.id.addGoalText)).perform(typeText("Wash dishes"));
        onView(withId(R.id.homeContext)).perform((click()));
        onView(withId(R.id.onetime)).perform((click()));
        onView(withText("Create")).perform((click()));

        //confirm goals added
        onView(withText("Study")).check(matches(isDisplayed()));
        onView(withText("Wash dishes")).check(matches(isDisplayed()));

        //go back to today and set focus mode
        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        onView(withText("Today")).perform(click());
        onView(withText("GO TO")).perform(click());

        onView(withId(R.id.action_bar_menu_focus_mode)).perform(click());
        onView(withId(R.id.homeFocus)).perform(click());
        onView(withText("OK")).perform(click());

        //*START*
        //check in home focus mode and it is on today page
        onView(withId(R.id.action_bar_menu_focus_mode_home)).check(matches(isDisplayed()));
        onView(withText(startsWith("Today"))).check(matches(isDisplayed()));

        //go to tomorrow
        onView(withId(R.id.action_bar_menu_dropdown)).perform(click());
        onView(withText("Tomorrow")).perform(click());
        onView(withText("GO TO")).perform(click());

        onView(withId(R.id.action_bar_menu_focus_mode_home)).check(matches(isDisplayed()));
        onView(withText(startsWith("Tomorrow"))).check(matches(isDisplayed()));
    }
}
