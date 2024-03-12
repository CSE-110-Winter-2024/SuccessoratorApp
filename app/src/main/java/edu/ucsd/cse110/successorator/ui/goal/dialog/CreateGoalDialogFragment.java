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

        var id = view.radioGroup.getCheckedRadioButtonId();
        var selectedRadioButton = (RadioButton) view.getRoot().findViewById(id);
        var text = selectedRadioButton.getText().toString();
//        int recurringId;
//        if(text.equals("One-Time")){
//            recurringId = -1;
//        }else if (text.equals("Daily")){
//            recurringId = 1;
//        }else if(text.equals("Weekly")){
//            recurringId = 2;
//        }else if(text.equals("Monthly")){
//            recurringId = 3;
//        }else{
//            recurringId = 4;
//        }
        //sort order is an invalid value here, because append/prepend will replace it

        var card = new Goal(goalText, null, false, -1, Constants.TODAY, -1);

        activityModel.addGoal(card);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }
}
