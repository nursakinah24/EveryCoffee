package com.example.everycoffee.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.everycoffee.Interface.ItemClickListener;
import com.example.everycoffee.R;

public class AdminOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView userName, userPhoneNumber,userTotalAmount, userDateTime, userShippingAddress;
    private ItemClickListener itemClickListener;

    public AdminOrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.order_user_name);
        userPhoneNumber= itemView.findViewById(R.id.order_phone_number);
        userTotalAmount = itemView.findViewById(R.id.order_total_price);
        userDateTime = itemView.findViewById(R.id.order_date_time);
        userShippingAddress = itemView.findViewById(R.id.order_address_city);
    }

    @Override
    public void onClick(View v){
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}
