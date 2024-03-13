package edu.ucsd.cse110.successorator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDateTime;
import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.date.UpdateTimeFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.DropdownDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.FocusDialogFragment;

public class MainActivity extends AppCompatActivity {
    boolean isEmpty = false;
    private int currentFocusMode = 0; // Default or initial focus mode

    ActivityMainBinding view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        setContentView(view.getRoot());
        swapFragments();
        startUpdateTime();
    }

    @Override
    protected void onPause() {
        var sharedPreferences = getSharedPreferences("successorator", MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("datetime", LocalDateTime.now().toString())
                .apply();
        super.onPause();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_goal, menu);
        getMenuInflater().inflate(R.menu.dropdown, menu);
        getMenuInflater().inflate(R.menu.advance_date, menu);
        getMenuInflater().inflate(R.menu.focus_mode, menu);
        return true;
    }

    /* This method is used to update the current Focus mode the user is in
       Which is later used to update the menu. */
    public void updateFocusMode(int focusMode) {
        this.currentFocusMode = focusMode;
        invalidateOptionsMenu(); // This will trigger onPrepareOptionsMenu
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.add_goal, menu);
        getMenuInflater().inflate(R.menu.dropdown, menu);
        getMenuInflater().inflate(R.menu.advance_date, menu);

        //Home
        if (currentFocusMode == 1) { // Assuming 1 represents "Home"
            getMenuInflater().inflate(R.menu.focus_mode_home, menu);
        }
        //Work
        else if (currentFocusMode == 2) {
            getMenuInflater().inflate(R.menu.focus_mode_work, menu);
        }
        //School
        else if (currentFocusMode == 3) { // Assuming 1 represents "School"
            getMenuInflater().inflate(R.menu.focus_mode_school, menu);
        }
        //Errand
        else if (currentFocusMode == 4){
            getMenuInflater().inflate(R.menu.focus_mode_errand, menu);
        //Cancel
        }else if(currentFocusMode == 0){
            getMenuInflater().inflate(R.menu.focus_mode, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.action_bar_menu_dropdown) {
            displayDropDown();

        }else if (itemId == R.id.action_bar_menu_focus_mode ||
                itemId == R.id.action_bar_menu_focus_mode_home ||
                itemId == R.id.action_bar_menu_focus_mode_school ||
                itemId == R.id.action_bar_menu_focus_mode_work ||
                itemId == R.id.action_bar_menu_focus_mode_errand ) {
            displayFocusMode();
        }

        return super.onOptionsItemSelected(item);
    }


    private void displayDropDown(){
        var dialogFragment = DropdownDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "DropdownDialogFragment");
    }
    private void displayFocusMode(){
        var dialogFragment = FocusDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "FocusDialogFragment");
    }

    private void startUpdateTime() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.update_time_container, UpdateTimeFragment.newInstance())
                .commit();
    }
}
