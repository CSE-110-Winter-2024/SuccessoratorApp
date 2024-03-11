package edu.ucsd.cse110.successorator.ui.date;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDateBinding;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateTomorrowGoalDialogFragment;

/**
 * Referenced https://developer.android.com/guide/fragments/appbar
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TomorrowDataFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentDateBinding view;

    public TomorrowDataFragment() {
        // Required empty public constructor
    }

    public static TomorrowDataFragment newInstance() {
        TomorrowDataFragment fragment = new TomorrowDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        //Initialize Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        Date date = activityModel.getCurrDate().getValue();
        date.setDate(LocalDateTime.now());
        activityModel.updateTime(date, false);
        updateDisplay();
        super.onResume();
    }

    @Override
    public void onPause() {
        Date date = activityModel.getLastLog().getValue();
        date.setDate(LocalDateTime.now());
        activityModel.updateTime(date, true);
        super.onPause();
    }

    //https://stackoverflow.com/questions/11690504/how-to-use-view-ontouchlistener-instead-of-onclick
    // for onTouchListener
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //this.view = FragmentDateBinding.inflate(inflater, container, false);
        //this.view = FragmentDateBinding.inflate(getLayoutInflater());
        //view = (FragmentDateBinding) getParentFragmentManager().findFragmentById(R.id.date_fragment_container);
        this.view = FragmentDateBinding.inflate(getLayoutInflater());
        setHasOptionsMenu(true);

        view.dateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Date date = activityModel.getCurrDate().getValue();
                date.setDate(LocalDateTime.now());
                activityModel.updateTime(date, false);
                updateDisplay();
                return true;
            }
        });

        updateDisplay();
        return view.getRoot();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bar_menu_advance_date) {// Navigate to settings screen.
            Date date = activityModel.getCurrDate().getValue();
            date.advanceDate();
            activityModel.updateTime(date, false);
            updateDisplay();
            return true;
        }


        if (item.getItemId() == R.id.action_bar_menu_add_goal) {
            displayPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayPopUp() {
        var dialogFragment = CreateTomorrowGoalDialogFragment.newInstance();
        dialogFragment.show(getParentFragmentManager(), "CreateTmrCardDialogFragment");
    }

    public void updateDisplay() {
        Date tmr = activityModel.getCurrDate().getValue();
        tmr.advanceDate();
        view.dateText.setText("Tomorrow, " + activityModel.getCurrDate().getValue().formatDate());

        //view.dateText.setText(activityModel.getCurrDate().getValue().formatDateTime());
    }
}