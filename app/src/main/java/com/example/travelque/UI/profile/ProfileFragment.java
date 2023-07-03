package com.example.travelque.UI.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelque.R;
import com.example.travelque.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    RadioGroup radioGroupLayout, radioGroupTravelLayout, radioGroupListLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences layoutPref = requireContext().getSharedPreferences("layout", Context.MODE_PRIVATE);

        radioGroupLayout = profileView.findViewById(R.id.radioGroupLayout);
        radioGroupTravelLayout = profileView.findViewById(R.id.radioGroupTravelLayout);
        radioGroupListLayout = profileView.findViewById(R.id.radioGroupListLayout);

        int selectedId  = layoutPref.getInt("layoutOption", R.id.radioButtonHorizontal);
        int selectedTravelId = layoutPref.getInt("travelLayoutOption", R.id.rbHorizontalTravel);
        int selectedListId = layoutPref.getInt("listLayoutOption", R.id.rbHorizontalList);

        radioGroupLayout.check(selectedId);
        radioGroupTravelLayout.check(selectedTravelId);
        radioGroupListLayout.check(selectedListId);

        radioGroupLayout.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = layoutPref.edit();
            editor.putInt("layoutOption", checkedId);
            editor.apply();
        });

        radioGroupTravelLayout.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = layoutPref.edit();
            editor.putInt("travelLayoutOption", checkedId);
            editor.apply();
        });

        radioGroupListLayout.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = layoutPref.edit();
            editor.putInt("listLayoutOption", checkedId);
            editor.apply();
        });

        return profileView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}