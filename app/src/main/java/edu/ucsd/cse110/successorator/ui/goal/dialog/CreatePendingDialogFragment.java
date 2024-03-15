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


import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogPendingBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Constants;

public class CreatePendingDialogFragment extends DialogFragment {
    private MainViewModel activityModel;
    private FragmentDialogPendingBinding view;

    CreatePendingDialogFragment(){
        //required empty public constructor
    }

    public static CreatePendingDialogFragment newInstance(){
        var fragment = new CreatePendingDialogFragment();
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
        view = FragmentDialogPendingBinding.inflate(getLayoutInflater());

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

        //sort order is an invalid value here, because append/prepend will replace it
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

        var card = new Goal(goalText, null,
                false, -1,
                Constants.PENDING, -1,
                contextId);

        activityModel.addGoal(card);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }
}
