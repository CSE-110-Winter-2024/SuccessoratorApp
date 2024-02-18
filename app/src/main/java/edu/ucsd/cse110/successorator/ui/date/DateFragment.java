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

/**
 * Referenced https://developer.android.com/guide/fragments/appbar
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentDateBinding view;

    Button adavanceDateButton;

    public DateFragment() {
        // Required empty public constructor
    }

    public static DateFragment newInstance() {
        DateFragment fragment = new DateFragment();
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
        Date date = activityModel.getDateTime().getValue();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = FragmentDateBinding.inflate(inflater, container, false);

        setHasOptionsMenu(true);

        updateDisplay();
        //view.dateText.setText(date.formatDate());
        //view.dateText.setText(date.formatDateTime());
        return view.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.advance_date, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bar_menu_advance_date) {// Navigate to settings screen.
            Date date = activityModel.getDateTime().getValue();
            date.advanceDate();
            activityModel.updateTime(date, false);
            updateDisplay();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void updateDisplay() {
        view.dateText.setText(activityModel.getDateTime()
                .getValue().formatDate());
        //view.dateText.setText(date.formatDateTime());
    }
}