package com.example.clothingwarehousemanagement.StockSearchACT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingwarehousemanagement.FunctionACT.FuntionsAdapter;
import com.example.clothingwarehousemanagement.R;

import java.util.List;

public class ShelvesAdapter extends RecyclerView.Adapter<ShelvesAdapter.ViewHolder>{
    private List<String> message;

    public ShelvesAdapter(List<String> message){this.message = message;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelf_item,parent,false);
        final ShelvesAdapter.ViewHolder viewHolder = new ShelvesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(message.get(position));
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_shelf_item);
        }
    }

    public void dataChange(List<String> message){
        this.message = message;
        notifyDataSetChanged();
    }
}
