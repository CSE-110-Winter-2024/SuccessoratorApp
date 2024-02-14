package edu.ucsd.cse110.successorator.ui.goal;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.GoalListFragmentBinding;
//Not created yet
//import edu.ucsd.cse110.successorator.ui.goal.dialog.ConfirmDeleteCardDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateGoalDialogFragment;

public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private GoalListFragmentBinding view;
    private GoalListAdapter adapter;

    public GoalListFragment() {
        // Required empty public constructor
    }

    public static GoalListFragment newInstance() {
        GoalListFragment fragment = new GoalListFragment();
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
        this.adapter = new GoalListAdapter(requireContext(), List.of(), activityModel::remove);
                                            //activityModel::strikethru, activityModel::removeStrikethru);

        //TODO: Delete Confirmation (feature creep for milestone 1)
        // id -> {
        //    var dialogFragment = ConfirmDeleteCardDialogFragment.newInstance(id);
        //    dialogFragment.show(getParentFragmentManager(), "ConfirmDeleteCardDialogFragment");
        //});
        activityModel.getOrderedGoals().observe(cards -> {
            if (cards == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(cards)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = GoalListFragmentBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.cardList.setAdapter(adapter);


        return view.getRoot();
    }
}

