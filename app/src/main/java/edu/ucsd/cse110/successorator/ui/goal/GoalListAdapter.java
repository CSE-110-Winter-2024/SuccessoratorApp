package edu.ucsd.cse110.successorator.ui.goal;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

/**
 * https://www.tutorialspoint.com/strikethrough-text-in-android
 * Adapter for GoalListFragment
 */
public class GoalListAdapter extends ArrayAdapter<Goal> {
    Consumer<Goal> onGoalClicked;
    Consumer<Integer> onDeleteClick;

    Consumer<Integer> removeStrikethruClick;

    public GoalListAdapter(Context context, List<Goal> goals,
                           Consumer<Goal> onGoalClicked,
                           Consumer<Integer> onDeleteClick) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(goals));
        this.onGoalClicked = onGoalClicked;
        this.onDeleteClick = onDeleteClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the goal for this position.
        var goal = getItem(position);
        assert goal != null;

        // Check if a view is being reused...
        ListItemGoalBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = ListItemGoalBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemGoalBinding.inflate(layoutInflater, parent, false);
        }

        binding.goalDeleteButton.setOnClickListener(v -> {
            var id = Objects.requireNonNull(goal.getId());
            onDeleteClick.accept(id);
        });

        // M -> V
        // Populate the view with the goal's data.
        binding.goalText.setText(goal.getTitle());
        // setup ST to match
        if (goal.isComplete()) {
            binding.goalText.setPaintFlags(binding.goalText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.goalText.setPaintFlags(binding.goalText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // V -> M
        //Set listener for strikethrough
        binding.goalText.setOnClickListener(v -> {
            onGoalClicked.accept(goal);
        });

        return binding.getRoot();
    }

    // The below methods aren't strictly necessary, usually.
    // But get in the habit of defining them because they never hurt
    // (as long as you have IDs for each item) and sometimes you need them.

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var goal = getItem(position);
        assert goal != null;

        var id = goal.getId();
        assert id != null;

        return id;
    }
}
