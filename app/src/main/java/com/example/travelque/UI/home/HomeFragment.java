package com.example.travelque.UI.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelque.Database.TravelHelper;
import com.example.travelque.Models.Travel;
import com.example.travelque.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;


public class HomeFragment extends Fragment {

    private TravelHelper travelHelper;
    private Vector<Travel> vTravel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String travelURL = "https://mocki.io/v1/bedc01b7-9212-4753-9263-c18415a1c5d5";
        travelHelper = new TravelHelper(getContext());

        JsonObjectRequest travelRequest = new JsonObjectRequest(Request.Method.GET, travelURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(getContext()).add(travelRequest);

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