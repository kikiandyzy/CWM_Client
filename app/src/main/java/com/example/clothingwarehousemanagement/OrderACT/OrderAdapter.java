package com.example.clothingwarehousemanagement.OrderACT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingwarehousemanagement.Data.Order;
import com.example.clothingwarehousemanagement.Data.RecyclerViewAdapterCallBack;
import com.example.clothingwarehousemanagement.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<Order> orders;
    private RecyclerViewAdapterCallBack callBack;

    public OrderAdapter(List<Order> orders,RecyclerViewAdapterCallBack callBack){
        this.orders = orders;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        final OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
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
        Order order = orders.get(position);
        holder.id.setText(order.getID());
        holder.date.setText(order.getDate());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView id;
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout_order_item);
            id = itemView.findViewById(R.id.textview_ID_order_item);
            date = itemView.findViewById(R.id.textview_date_order_item);
        }
    }

    public void dataChange(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }
}
