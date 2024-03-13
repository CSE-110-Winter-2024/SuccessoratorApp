package edu.ucsd.cse110.successorator.ui.goal.dialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FramentDialogFocusBinding;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.ui.goal.GoalListAdapter;
public class FocusDialogFragment extends DialogFragment  {
    private MainViewModel activityModel;
    private MainActivity activity;

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
        if(view.homeFocus.isChecked()) {
            activityModel.setFocusMode(1);

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateFocusMode(1);
            }

        }else if(view.workFocus.isChecked()){//work
            activityModel.setFocusMode(2);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateFocusMode(2);
            }

        }else if(view.schoolFocus.isChecked()){//school
            activityModel.setFocusMode(3);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateFocusMode(3);
            }

        }else if(view.errandFocus.isChecked()){
            activityModel.setFocusMode(4); //errand
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateFocusMode(4);
            }
        }

        else if(view.cancelFocus.isChecked()){
            activityModel.setFocusMode(0);//cancel
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateFocusMode(0);
            }
        }

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }

}

