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

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentUpdateTimeBinding;
import edu.ucsd.cse110.successorator.databinding.GoalListFragmentBinding;
import edu.ucsd.cse110.successorator.lib.domain.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateTimeFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentUpdateTimeBinding view;

    public UpdateTimeFragment() {
        // Required empty public constructor
    }

    public static UpdateTimeFragment newInstance() {
        UpdateTimeFragment fragment = new UpdateTimeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentUpdateTimeBinding.inflate(inflater, container, false);
        return view.getRoot();
    }
}