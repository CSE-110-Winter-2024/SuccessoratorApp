package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeKeeper;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.GoalListFragment;

public class MainActivity extends AppCompatActivity {
    boolean isEmpty = false;
    Date date;
    ActivityMainBinding view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));

        //updateTime();

        setContentView(view.getRoot());
        swapFragments();
    }

    @Override
    protected void onResume() {
        var sharedPreferences = getSharedPreferences("successorator", MODE_PRIVATE);
        //timeKeeper = new SimpleTimeKeeper();
        date.setDate(LocalDateTime.parse(sharedPreferences
                .getString("datetime", LocalDateTime.now().toString())));

        sharedPreferences.edit()
                .putString("datetime", LocalDateTime.now().toString())
                .apply();
        updateTime();
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.action_bar_menu_add_goal) {
            displayPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayPopUp() {
        var dialogFragment = CreateGoalDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "CreateCardDialogFragment");
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

    private void updateTime() {
        view.dateText.setText(date.getDate());
        //view.dateText.setText(date.getDateTime());
        TextView dateText = findViewById(R.id.date_text);
        //Scheduler scheduler = new Scheduler(dateText);
        //scheduler.startTask();
    }
}
