package com.example.travelque.UI.travel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelque.Database.TravelHelper;
import com.example.travelque.Models.Travel;
import com.example.travelque.R;

import java.util.Vector;

public class TravelFragment extends Fragment {

    TravelHelper travelHelper;
    RecyclerView travelRV;
    Vector<Travel> vTravel;
    TravelAdapter travelAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        travelHelper = new TravelHelper(getContext());
        travelHelper.open();
        vTravel = travelHelper.viewTravel();
        travelHelper.close();

        travelRV = view.findViewById(R.id.travelRV);
        travelAdapter = new TravelAdapter(getContext(), vTravel);
        travelRV.setAdapter(travelAdapter);
        travelRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View travelView = inflater.inflate(R.layout.fragment_travel, container, false);
        return travelView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}