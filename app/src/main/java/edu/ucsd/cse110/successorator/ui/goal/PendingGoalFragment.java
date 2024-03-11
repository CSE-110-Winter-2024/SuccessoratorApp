package edu.ucsd.cse110.successorator.ui.goal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.PopupMenu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;

import edu.ucsd.cse110.successorator.databinding.PendingGoalBinding;
import edu.ucsd.cse110.successorator.databinding.TomorrowGoalBinding;

public class PendingGoalFragment extends Fragment {
    private MainViewModel activityModel;
    private PendingGoalBinding view;
    private GoalListAdapter adapter;

    public PendingGoalFragment() {
    }

    public static PendingGoalFragment newInstance() {
        PendingGoalFragment fragment = new PendingGoalFragment();
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
                    //activityModel.save(newGoal);
                },
                goals -> {
                    activityModel.save(goals);
                },
                activityModel::remove
        );

        activityModel.getPendingGoals().observe(cards -> {
            if (cards == null) return;

            int context = activityModel.getFocusMode();
            if(context != 0){
                cards = cards.stream()
                        .filter(goal -> goal.getContextId() == context)
                        .collect(Collectors.toList());
            }

            adapter.clear();
            adapter.addAll(new ArrayList<>(cards)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = PendingGoalBinding.inflate(inflater, container, false);

        view.cardList.setAdapter(adapter);

        return view.getRoot();
    }
}
