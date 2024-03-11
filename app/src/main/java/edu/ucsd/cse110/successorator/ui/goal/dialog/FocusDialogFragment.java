package edu.ucsd.cse110.successorator.ui.goal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogDropdownBinding;
import edu.ucsd.cse110.successorator.databinding.FramentDialogFocusBinding;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.date.PendingFragment;
import edu.ucsd.cse110.successorator.ui.date.TomorrowDataFragment;
import edu.ucsd.cse110.successorator.ui.goal.GoalListAdapter;
import edu.ucsd.cse110.successorator.ui.goal.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goal.PendingGoalFragment;
import edu.ucsd.cse110.successorator.ui.goal.TomorrowGoalListFragment;

public class FocusDialogFragment extends DialogFragment {
    private MainViewModel activityModel;

    private FramentDialogFocusBinding view;

    private GoalListAdapter adapter;

    FocusDialogFragment(){ }

    public static FocusDialogFragment newInstance(){
        var fragment = new FocusDialogFragment();
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
            this.view = FramentDialogFocusBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                .setPositiveButton("OK", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();

    }

    private void onPositiveButtonClick(DialogInterface dialog, int which){
        //todo: Add color changes to focus mode (hamburger)
        //Home
        if(view.homeContext.isChecked()){
            activityModel.setFocusMode(1);
            //Work
        }else if(view.schoolContext.isChecked()){
            activityModel.setFocusMode(2);
            //School
        }else if(view.errandContext.isChecked()){
            activityModel.setFocusMode(3);
        }else{
            //Errands
            activityModel.setFocusMode(4);
        }
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }

}

