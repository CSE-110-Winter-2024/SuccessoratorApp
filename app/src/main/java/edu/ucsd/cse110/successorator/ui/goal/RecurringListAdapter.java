package edu.ucsd.cse110.successorator.ui.goal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.databinding.ListItemRecurringBinding;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.util.Constants;

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

        var id = recurring.getContextId();
        if(id == 1){
            binding.contextText.setText("H");
            binding.circle.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.home)));
        }else if(id == 2){
            binding.contextText.setText("W");
            binding.circle.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.work)));
        }else if(id == 3){
            binding.contextText.setText("S");
            binding.circle.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.school)));
        }else{
            binding.contextText.setText("E");
            binding.circle.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.errand)));
        }

        // M -> V
        // Populate the view with the goal's data.
        binding.recurringText.setText(getText(recurring));

        // V -> M
        //Set listener for strikethrough
        binding.recurringText.setOnClickListener(v -> {
            onRecurringClicked.accept(recurring);
        });

        return binding.getRoot();
    }

    private String getText(RecurringGoal recurring) {
        int frequency = recurring.getFrequency();
        Date date = new Date(null);
        date.setAdjustedDate(recurring.getStartDate().atStartOfDay());

        int num = date.getWeekOfMonth();
        String dayOfWeek = date.dayOfWeek();
        String text = recurring.getTitle();
        if(frequency == Constants.DAILY) {
            text += ", daily";
        }
        else if(frequency == Constants.WEEKLY) {
            text += ", weekly on " + dayOfWeek;
        }
        else if(frequency == Constants.MONTHLY) {
            text += ", monthly on " + date.getDayOfMonthWithSuffix(num) + " " + dayOfWeek;
        }
        else if(frequency == Constants.YEARLY) {
            text += ", yearly on " + date.getDayAndMonth();
        }
        return text;
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
