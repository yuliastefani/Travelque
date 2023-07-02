package com.example.travelque.UI.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelque.Database.ListHelper;
import com.example.travelque.Models.List;
import com.example.travelque.R;

import java.util.Vector;

public class ListFragment extends Fragment {

    ListHelper listHelper;
    RecyclerView listRV;
    Vector<List> vList;
    ListAdapter listAdapter;

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
        View listView = inflater.inflate(R.layout.fragment_list, container, false);
        return listView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}