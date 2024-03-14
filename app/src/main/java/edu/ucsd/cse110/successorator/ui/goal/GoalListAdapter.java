package edu.ucsd.cse110.successorator.ui.goal;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Constants;

/**
 * https://www.tutorialspoint.com/strikethrough-text-in-android
 * Adapter for GoalListFragment
 *
 * Long Hold Logic:
 * https://stackoverflow.com/questions/49712663/how-to-properly-use-setonlongclicklistener-with-kotlin
 * and ChatGPT
 *
 */
public class GoalListAdapter extends ArrayAdapter<Goal> {
    Consumer<Goal> onGoalClicked;
    Consumer<Goal> onGoalLongPressed;
    Consumer<Integer> onDeleteClick;

    public GoalListAdapter(Context context, List<Goal> goals,
                           Consumer<Goal> onGoalClicked,
                           Consumer<Goal> onGoalLongPressed,
                           Consumer<Integer> onDeleteClick) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(goals));
        this.onGoalClicked = onGoalClicked;
        this.onGoalLongPressed = onGoalLongPressed;
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

        var id = goal.getContextId();
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
        binding.goalText.setText(goal.getTitle());
        // setup ST to match
        if (goal.isComplete()) {
            binding.goalText.setPaintFlags(binding.goalText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.circle.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.finish)));
        } else {
            binding.goalText.setPaintFlags(binding.goalText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        binding.goalText.setOnLongClickListener(v-> {
            if (getContext() instanceof Activity && onGoalLongPressed != null) {
                Activity activity = (Activity) getContext();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.pending_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.today){
                        onGoalLongPressed.accept(goal.withState(Constants.TODAY));
                    }else if(item.getItemId() == R.id.tmr){
                        onGoalLongPressed.accept(goal.withState(Constants.TOMORROW));
                    }else if(item.getItemId() == R.id.finish){
                        onGoalLongPressed.accept(goal.withComplete(!goal.isComplete()).withState(Constants.TODAY));
                    }else if(item.getItemId() == R.id.delete){
                        onDeleteClick.accept(goal.getId());
                    }

                    return true;
                });
                popupMenu.show();
            }
            return true;
        });

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
