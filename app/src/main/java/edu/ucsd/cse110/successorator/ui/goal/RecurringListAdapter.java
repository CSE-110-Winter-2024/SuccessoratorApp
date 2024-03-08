package edu.ucsd.cse110.successorator.ui.goal;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.databinding.ListItemRecurringBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;

/**
 * Adapter for RecurringListFragment
 */
public class RecurringListAdapter extends ArrayAdapter<RecurringGoal>  {
    Consumer<RecurringGoal> onRecurringClicked;
    Consumer<Integer> onDeleteClick;

    public RecurringListAdapter(Context context, List<RecurringGoal> recurring,
                                Consumer<RecurringGoal> onRecurringClicked,
                                Consumer<Integer> onDeleteClick) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(recurring));
        this.onRecurringClicked = onRecurringClicked;
        this.onDeleteClick = onDeleteClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the goal for this position.
        var recurring = getItem(position);
        assert recurring != null;

        // Check if a view is being reused...
        ListItemRecurringBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = ListItemRecurringBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemRecurringBinding.inflate(layoutInflater, parent, false);
        }

        //TODO : changed from onclick to on long click
        binding.goalDeleteButton.setOnClickListener(v -> {
            var id = Objects.requireNonNull(recurring.getId());
            onDeleteClick.accept(id);
        });

        // M -> V
        // Populate the view with the goal's data.
        binding.recurringText.setText(recurring.getTitle());

        // V -> M
        //Set listener for strikethrough
        binding.recurringText.setOnClickListener(v -> {
            onRecurringClicked.accept(recurring);
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
