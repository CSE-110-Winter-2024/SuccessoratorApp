package edu.ucsd.cse110.successorator.ui.goal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.PendingGoalBinding;
import edu.ucsd.cse110.successorator.databinding.TomorrowGoalBinding;

public class PendingGoalListFragment extends Fragment{
    private MainViewModel activityModel;
    private PendingGoalBinding view;

    public PendingGoalListFragment() {
    }

    public static PendingGoalListFragment newInstance() {
        PendingGoalListFragment fragment = new PendingGoalListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = PendingGoalBinding.inflate(inflater, container, false);
        return view.getRoot();
    }
}
