package edu.ucsd.cse110.successorator.ui.goal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.util.Constants;

/**
 * Fragment associated with the pop up box for creating a new goal
 */
public class CreateTomorrowGoalDialogFragment extends DialogFragment {
    private MainViewModel activityModel;
    private FragmentDialogCreateGoalBinding view;


    public static CreateTomorrowGoalDialogFragment newInstance() {
        var fragment = new CreateTomorrowGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());
        var today = activityModel.getCurrDate().getValue();
        Date tomorrow = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
        tomorrow.setDate(today.getTomorrow());

        // TODO: make sure this works

        int num = tomorrow.getWeekOfMonth();
        String dayOfWeek = tomorrow.dayOfWeek();
        view.Weekly.setText("Weekly, on " + dayOfWeek);
        view.Monthly.setText("Monthly, on " + tomorrow.getDayOfMonthWithSuffix(num) + " " + dayOfWeek);
        view.Yearly.setText("Yearly, on " + tomorrow.getDayAndMonth());

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Enter a goal.")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
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

        var contextId = view.contexts.getCheckedRadioButtonId();
        var contextSelected = (RadioButton) view.getRoot().findViewById(contextId);
        var context = contextSelected.getText().toString();

        if(context.equals("Home")){
            contextId = 1;
        }else if(context.equals("Work")){
            contextId = 2;
        }else if(context.equals("School")){
            contextId = 3;
        }else{
            contextId = 4;
        }

        //sort order is an invalid value here, because append/prepend will replace it

        if (isRecurringGoal) {
            var startDate = activityModel.getCurrDate().getValue().getDate().toLocalDate();
            var card = new RecurringGoal(goalText, null, frequency, startDate, contextId);
            activityModel.addRecurring(card);
        } else {
            var card = new Goal(goalText, null,
                    false, -1,
                    Constants.TOMORROW, -1,
                    contextId);
            activityModel.addGoal(card);
        }

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}