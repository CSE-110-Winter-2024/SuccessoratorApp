package edu.ucsd.cse110.successorator.ui.goal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.util.Constants;

/**
 * Fragment associated with the pop up box for creating a new goal
 */
public class CreateGoalDialogFragment extends DialogFragment{
    private MainViewModel activityModel;
    private FragmentDialogCreateGoalBinding view;

    CreateGoalDialogFragment(){
        //required empty public constructor
    }

    public static CreateGoalDialogFragment newInstance(){
        var fragment = new CreateGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());
        var today = activityModel.getCurrDate().getValue();

        int num = today.getWeekOfMonth();
        String dayOfWeek = today.dayOfWeek();
        view.Weekly.setText("Weekly, on " + dayOfWeek);
        view.Monthly.setText("Monthly, on " + today.getDayOfMonthWithSuffix(num) + " " + dayOfWeek);
        view.Yearly.setText("Yearly, on " + today.getDayAndMonth());

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Enter a goal.")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which){
        var goalText = view.addGoalText.getText().toString();
        int frequency = 0;
        boolean isRecurringGoal = false;

        if (view.daily.isChecked()) {
            frequency = Constants.DAILY;
            isRecurringGoal = true;
        } else if (view.Weekly.isChecked()) {
            frequency = Constants.WEEKLY;
            isRecurringGoal = true;
        } else if (view.Monthly.isChecked()) {
            frequency = Constants.MONTHLY;
            isRecurringGoal = true;
        } else if (view.Yearly.isChecked()) {
            frequency = Constants.YEARLY;
            isRecurringGoal = true;
        }

        if (isRecurringGoal) {
            var startDate = LocalDateTime.now().minusHours(2).toLocalDate();
            var card = new RecurringGoal(goalText, null, frequency, startDate);
            activityModel.addRecurring(card);
        } else {
            var card = new Goal(goalText, null, false, -1, Constants.TODAY, -1);
            activityModel.addGoal(card);
        }

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }
}
