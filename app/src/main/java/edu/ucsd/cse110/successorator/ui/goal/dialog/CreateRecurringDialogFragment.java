package edu.ucsd.cse110.successorator.ui.goal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateRecurringDialogBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalFactory;
import edu.ucsd.cse110.successorator.lib.domain.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.util.Constants;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.date.PendingFragment;
import edu.ucsd.cse110.successorator.ui.date.RecurringFragment;
import edu.ucsd.cse110.successorator.ui.date.TomorrowDataFragment;
import edu.ucsd.cse110.successorator.ui.goal.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goal.PendingGoalFragment;
import edu.ucsd.cse110.successorator.ui.goal.RecurringListFragment;
import edu.ucsd.cse110.successorator.ui.goal.TomorrowGoalListFragment;

/**
 * Fragment associated with the pop up box for creating a new recurring goal
 */
public class CreateRecurringDialogFragment extends DialogFragment {

    private MainViewModel activityModel;
    private FragmentCreateRecurringDialogBinding view;

    public CreateRecurringDialogFragment() {
        // Required empty public constructor
    }

    public static CreateRecurringDialogFragment newInstance() {
        var fragment = new CreateRecurringDialogFragment();
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
        this.view = FragmentCreateRecurringDialogBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Enter a goal.")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();

    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var recurringText = view.addGoalText.getText().toString();
        int frequency = 0;

        if (view.radioButton.isChecked()) {
            frequency = Constants.DAILY;
        } else if (view.radioButton2.isChecked()) {
            frequency = Constants.WEEKLY;
        } else if (view.radioButton3.isChecked()) {
            frequency = Constants.MONTHLY;
        } else if (view.radioButton4.isChecked()) {
            frequency = Constants.YEARLY;
        }

        LocalDate startDate = LocalDateTime.now().minusHours(2).toLocalDate();

        var datePicker = view.datePicker;
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        startDate = LocalDate.of(year, month, day);

        var card = new RecurringGoal(recurringText, null, frequency, startDate);
        activityModel.addRecurring(card);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }
}