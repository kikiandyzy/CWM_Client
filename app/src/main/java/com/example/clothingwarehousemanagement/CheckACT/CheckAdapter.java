package com.example.clothingwarehousemanagement.CheckACT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingwarehousemanagement.Data.Shelf;
import com.example.clothingwarehousemanagement.OrderACT.OrderAdapter;
import com.example.clothingwarehousemanagement.R;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder>{

    private List<Shelf> shelfList;

    public CheckAdapter(List<Shelf> shelfList){
        this.shelfList = shelfList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item,parent,false);
        final CheckAdapter.ViewHolder viewHolder = new CheckAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shelf shelf = shelfList.get(position);
        holder.shelf.setText(shelf.getShelfID()+"-"+shelf.getStorageLocation());
        holder.clothing.setText(shelf.getClothingID()+"["+shelf.getQuantity()+"]");
    }

    @Override
    public int getItemCount() {
        return shelfList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shelf;
        TextView clothing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shelf = itemView.findViewById(R.id.textview_shelf_check_item);
            clothing = itemView.findViewById(R.id.textview_clothing_check_item);
        }
    }

    public void dataChange(List<Shelf> shelfList){
        this.shelfList = shelfList;
        notifyDataSetChanged();

    }
}
