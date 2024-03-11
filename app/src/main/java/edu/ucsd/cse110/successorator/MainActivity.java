package edu.ucsd.cse110.successorator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.DropdownDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.FocusDialogFragment;

public class MainActivity extends AppCompatActivity {
    boolean isEmpty = false;

    ActivityMainBinding view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);

        setContentView(view.getRoot());
        swapFragments();
    }

    @Override
    protected void onResume() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.date_fragment_container, DateFragment.newInstance())
                .commit();
        super.onResume();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.action_bar_menu_dropdown) {
            displayDropDown();
        }

        if (itemId == R.id.action_bar_menu_focus_mode) {
            // Code goes here TODO
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

    private void swapFragments() {
        if (isEmpty) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.goalList, CreateGoalDialogFragment.newInstance())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.goalList, GoalListFragment.newInstance())
                    .commit();
        }
    }
}