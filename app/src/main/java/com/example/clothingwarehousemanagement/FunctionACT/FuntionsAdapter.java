package com.example.clothingwarehousemanagement.FunctionACT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingwarehousemanagement.R;
import com.example.clothingwarehousemanagement.Data.RecyclerViewAdapterCallBack;

import java.util.List;

public class FuntionsAdapter extends RecyclerView.Adapter<FuntionsAdapter.ViewHolder> {

    private RecyclerViewAdapterCallBack callBack;
    private List<String> functionNames;

    public FuntionsAdapter( List<String> functionNames,RecyclerViewAdapterCallBack callBack){
        this.functionNames = functionNames;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.function_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                callBack.click(position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewFunctionName.setText(functionNames.get(position));
    }

    @Override
    public int getItemCount() {
        return functionNames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textViewFunctionName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_function_item);
            textViewFunctionName = itemView.findViewById(R.id.text_view_function_name_function_item);
        }
    }
}
