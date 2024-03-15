package edu.ucsd.cse110.successorator.ui.date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDateBinding;
import edu.ucsd.cse110.successorator.lib.domain.Date;
import edu.ucsd.cse110.successorator.ui.goal.PendingGoalFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreatePendingDialogFragment;
import edu.ucsd.cse110.successorator.ui.goal.dialog.CreateTomorrowGoalDialogFragment;

public class PendingFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentDateBinding view;

    public PendingFragment() {
        // Required empty public constructor
    }

    public static PendingFragment newInstance() {
        PendingFragment fragment = new PendingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        //Initialize Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = FragmentDateBinding.inflate(getLayoutInflater());
        setHasOptionsMenu(true);
        view.dateText.setText("Pending");
        return view.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bar_menu_add_goal) {
            displayPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayPopUp() {
        var dialogFragment = CreatePendingDialogFragment.newInstance();
        dialogFragment.show(getParentFragmentManager(), "CreatePendingDialogFragment");
    }
}
