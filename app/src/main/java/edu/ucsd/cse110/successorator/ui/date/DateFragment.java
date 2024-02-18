package edu.ucsd.cse110.successorator.ui.date;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDateBinding;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.util.Subject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentDateBinding view;
    private Date date;

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

        //Initialize Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(activityModel.getDateTime().getValue().getDate());
    }

    @Override
    public void onResume() {
        date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        date.setDate(LocalDateTime.now());
        activityModel.getDateTime().setValue(date);
        updateDisplay();
        super.onResume();
    }

    @Override
    public void onPause() {
        activityModel.getLastLog().getValue().setDate(LocalDateTime.now());
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = FragmentDateBinding.inflate(inflater, container, false);
        //view.dateText.setText(date.formatDate());
        view.dateText.setText(date.formatDateTime());
        return view.getRoot();
    }

    public void updateDisplay() {
        //view.dateText.setText(date.formatDate());
        view.dateText.setText(date.formatDateTime());
    }
}