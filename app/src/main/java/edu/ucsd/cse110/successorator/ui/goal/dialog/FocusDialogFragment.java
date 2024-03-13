package edu.ucsd.cse110.successorator.ui.goal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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
        if(view.homeFocus.isChecked()){//home
            activityModel.setFocusMode(1);
            View rootView = ((Dialog) dialog).getWindow().getDecorView().getRootView();
            Toolbar toolbar = rootView.findViewById(R.id.action_bar_menu_focus_mode);
            if(toolbar != null){
                Menu menu = toolbar.getMenu();
                if(menu != null){
                    MenuItem menuItem = menu.findItem(R.drawable.ic_waffle);
                    Drawable drawable = menu.findItem(R.drawable.ic_waffle).getIcon();
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.home));
                    menu.findItem(R.drawable.ic_waffle).setIcon(drawable);
//                    if(drawable != null){
//                        drawable.mutate();
//                        drawable.setTint(Color.RED);
//
//                    }

                    //menuItem.setIconTintList(ColorStateList.valueOf(Color.RED));

                }

            }
        }else if(view.workFocus.isChecked()){//work
            activityModel.setFocusMode(2);

        }else if(view.schoolFocus.isChecked()){//school
            activityModel.setFocusMode(3);
        }else if(view.errandFocus.isChecked()){
            activityModel.setFocusMode(4);//errand
        }
        else if(view.cancelFocus.isChecked()){
            activityModel.setFocusMode(0);//cancel
        }
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }

}

