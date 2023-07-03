package com.example.travelque.UI.list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        listHelper = new ListHelper(getContext());
        listHelper.open();
        vList = listHelper.viewList();
        listHelper.close();

        listRV = view.findViewById(R.id.listRV);
        listAdapter = new ListAdapter(getContext(), vList);
        listRV.setAdapter(listAdapter);

        SharedPreferences layoutPref = requireContext().getSharedPreferences("layout", Context.MODE_PRIVATE);
        int selectedListId = layoutPref.getInt("listLayoutOption", R.id.rbHorizontalList);
        if (selectedListId == R.id.rbHorizontalList)
            listRV.setLayoutManager(new LinearLayoutManager(getContext()));
        else{
            listRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeListCard(listAdapter));
        itemTouchHelper.attachToRecyclerView(listRV);
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