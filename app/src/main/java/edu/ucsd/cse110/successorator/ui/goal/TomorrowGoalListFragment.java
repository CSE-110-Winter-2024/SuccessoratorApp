package edu.ucsd.cse110.successorator.ui.goal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.TomorrowGoalBinding;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateTomorrowGoalDialogFragment;

public class TomorrowGoalListFragment extends Fragment{
    private MainViewModel activityModel;
    private TomorrowGoalBinding view;

    private GoalListAdapter adapter;

    public TomorrowGoalListFragment() {
    }

    public static TomorrowGoalListFragment newInstance() {
        TomorrowGoalListFragment fragment = new TomorrowGoalListFragment();
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


        // Initialize the Adapter (with an empty list for now)
        this.adapter = new GoalListAdapter(
                requireContext(),
                List.of(),
                goal -> {
                    var newGoal = goal.withComplete(!goal.isComplete());
                    activityModel.save(newGoal);
                },
                null,
                activityModel::remove
        );

        activityModel.getTmrGoals().observe(cards -> {
            if (cards == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(cards)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = TomorrowGoalBinding.inflate(inflater, container, false);

        view.cardList.setAdapter(adapter);

        return view.getRoot();
    }
}
