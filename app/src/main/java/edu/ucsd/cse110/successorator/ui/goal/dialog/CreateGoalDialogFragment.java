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
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

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

        int num = activityModel.weekNumber();
        view.Weekly.setText("Weekly, on " + dayOfWeek());
        view.Monthly.setText("Monthly, " + num + "th of Month");
        view.Yearly.setText("Yearly, on " + dayAndYear());

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Enter a goal.")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();

    }

    private String dayOfWeek(){
        return LocalDate.now().getDayOfWeek().toString();
    }

    private String dayAndYear(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d");
        return LocalDate.now().format(formatter);
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which){
        var goalText = view.addGoalText.getText().toString();

        var id = view.radioGroup.getCheckedRadioButtonId();
        var selectedRadioButton = (RadioButton) view.getRoot().findViewById(id);
        var text = selectedRadioButton.getText().toString();

        //sort order is an invalid value here, because append/prepend will replace it

        var card = new Goal(goalText, null, false, -1, "Today", -1);



        activityModel.addGoal(card);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }
}
