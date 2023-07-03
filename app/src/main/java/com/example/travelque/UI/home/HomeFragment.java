package com.example.travelque.UI.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelque.Database.ListHelper;
import com.example.travelque.Database.TravelHelper;
import com.example.travelque.Models.List;
import com.example.travelque.Models.Travel;
import com.example.travelque.R;
import com.example.travelque.UI.list.ListAdapter;
import com.example.travelque.UI.list.ListFragment;
import com.example.travelque.UI.travel.TravelAdapter;
import com.example.travelque.UI.travel.TravelFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;


public class HomeFragment extends Fragment {

    TextView allTravel, allList;
    RecyclerView travelRV, listRV;
    private TravelHelper travelHelper;
    private ListHelper listHelper;
    private Vector<Travel> vTravel;
    private Vector<List> vList;
    TravelAdapter travelAdapter;
    ListAdapter listAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String travelURL = "https://mocki.io/v1/bedc01b7-9212-4753-9263-c18415a1c5d5";
        travelHelper = new TravelHelper(getContext());

        JsonObjectRequest travelRequest = new JsonObjectRequest(Request.Method.GET, travelURL, null,
                response -> {
                    try {
                        vTravel = new Vector<>();
                        JSONArray travelArray = response.getJSONArray("destinations");
                        travelHelper.open();
                        for (int i = 0; i < travelArray.length(); i++) {
                            JSONObject travelObject = travelArray.getJSONObject(i);
                            String travelName = travelObject.getString("name");
                            String travelDesc = travelObject.getString("description");
                            String travelImage = travelObject.getString("image");
                            String travelLang = travelObject.getString("latitude");
                            String travelLong = travelObject.getString("longitude");
                            if (!travelHelper.validateTravel(travelName)){
                                travelHelper.addTravel(travelName, travelDesc, travelImage, travelLang, travelLong);
                            }

                        }
                        travelHelper.close();

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }

                },
                error -> Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(getContext()).add(travelRequest);

        allTravel = view.findViewById(R.id.allTravel);
        travelRV = view.findViewById(R.id.travelRV);

        travelHelper = new TravelHelper(getContext());
        travelHelper.open();
        vTravel = travelHelper.viewTravel();
        travelHelper.close();

        travelAdapter = new TravelAdapter(getContext(), vTravel);
        travelRV.setAdapter(travelAdapter);

        allList = view.findViewById(R.id.allList);
        listHelper = new ListHelper(getContext());
        listHelper.open();
        vList = listHelper.viewList();
        listHelper.close();

        listRV = view.findViewById(R.id.listRV);
        listAdapter = new ListAdapter(getContext(), vList);
        listRV.setAdapter(listAdapter);


        SharedPreferences layoutPref = requireContext().getSharedPreferences("layout", Context.MODE_PRIVATE);
        int selectedId = layoutPref.getInt("layoutOption", R.id.radioButtonHorizontal);
        if (selectedId == R.id.radioButtonHorizontal) {
            travelRV.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false));
            listRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        } else {
            travelRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
            listRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}