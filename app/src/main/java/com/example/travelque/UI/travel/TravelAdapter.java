package com.example.travelque.UI.travel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelque.Models.Travel;
import com.example.travelque.R;

import java.util.Vector;

public class TravelAdapter  extends RecyclerView.Adapter<TravelAdapter.TravelViewHolder>{

    Context travelContent;
    Vector<Travel> vTravel;

    public TravelAdapter(Context travelContent, Vector<Travel> vTravel) {
        this.travelContent = travelContent;
        this.vTravel = vTravel;
    }

    @NonNull
    @Override
    public TravelAdapter.TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewTravel = LayoutInflater.from(travelContent).inflate(R.layout.travel_item, parent, false);
        return new TravelViewHolder(viewTravel);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelAdapter.TravelViewHolder holder, int position) {
        Travel travel = vTravel.get(position);
        holder.travelName.setText(travel.getName());
        Glide.with(travelContent).load(travel.getImage()).into(holder.travelImage);

        holder.travelCV.setOnClickListener(v -> {
            Intent detailTravelIntent = new Intent(travelContent, DetailTravelActivity.class);
            detailTravelIntent.putExtra(DetailTravelActivity.detTravel, vTravel.get(position));
            travelContent.startActivity(detailTravelIntent);
        });
    }

    @Override
    public int getItemCount() {
        return vTravel.size();
    }

    public class TravelViewHolder extends RecyclerView.ViewHolder {

        TextView travelName;
        ImageView travelImage;
        CardView travelCV;

        public TravelViewHolder(@NonNull View itemView) {
            super(itemView);
            travelName = itemView.findViewById(R.id.travelName);
            travelImage = itemView.findViewById(R.id.travelImage);
            travelCV = itemView.findViewById(R.id.travelCV);
        }
    }
}
