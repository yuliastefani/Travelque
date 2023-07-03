package com.example.travelque.UI.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelque.Database.ListHelper;
import com.example.travelque.Database.TravelHelper;
import com.example.travelque.Models.List;
import com.example.travelque.R;
import com.example.travelque.UI.travel.TravelAdapter;

import java.util.Vector;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    Context listContent;
    Vector<List> vList;
    TravelHelper travelHelper;

    public ListAdapter(Context listContent, Vector<List> vList) {
        this.listContent = listContent;
        this.vList = vList;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewList = LayoutInflater.from(listContent).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(viewList);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        List list = vList.get(position);
        travelHelper = new TravelHelper(listContent);
        travelHelper.open();
        holder.travelName.setText(travelHelper.fetchTravel(list.getTravelId()).getName());

        Integer priority = position % 3 + 1;
        holder.listId.setText(" " + String.valueOf(priority));
        Glide.with(listContent).load(travelHelper.fetchTravel(list.getTravelId()).getImage()).into(holder.travelImage);
        travelHelper.close();

    }

    @Override
    public int getItemCount() {
        return vList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView travelImage;
        TextView travelName, listId;
        CardView listCV;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            travelName = itemView.findViewById(R.id.travelName);
            travelImage = itemView.findViewById(R.id.travelImage);
            listId = itemView.findViewById(R.id.listId);
            listCV = itemView.findViewById(R.id.listCV);
        }
    }

    public void deleteList(int position){
        ListHelper listHelper = new ListHelper(listContent);
        listHelper.open();
        listHelper.deleteList(vList.get(position).getId());
        listHelper.close();

        vList.remove(position);
        notifyItemRemoved(position);
    }
}
