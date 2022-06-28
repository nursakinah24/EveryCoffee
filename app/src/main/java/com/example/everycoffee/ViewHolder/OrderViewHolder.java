package com.example.everycoffee.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.everycoffee.Interface.ItemClickListener;
import com.example.everycoffee.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtUserName;
    public RecyclerView recyclerView;
    public Button showDetailBtn;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtUserName = itemView.findViewById(R.id.order_username);
        recyclerView = itemView.findViewById(R.id.order_recyclerview);
        showDetailBtn = itemView.findViewById(R.id.show_detail_order);

    }

    @Override
    public void onClick(View v){
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}