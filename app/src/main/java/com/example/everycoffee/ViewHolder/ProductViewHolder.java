package com.example.everycoffee.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.everycoffee.R;
import com.example.everycoffee.Interface.ItemClickListener;

//class ini menyimpan referensi dari view-view yang digunakan pada sebuah item direcycle view.
//viewholder = menyimpan view2 yang nantinya akan digunakan untuk menampikan data
//tujuannya menghemat waktu dibandingkan dengan memakai findbyid() saat update list dengan data yang baru
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtproductPrice;
    public ImageView imageView;
    public ItemClickListener listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.productImage);
        txtProductName = itemView.findViewById(R.id.productName);
        txtproductPrice = itemView.findViewById(R.id.productPrice);
    }
    //ItemClickListener import interface import com.example.ecommerce.Interface.ItemClickListener;
    public void setItemClickListenter(ItemClickListener listner){
        this.listner =listner;
    }
    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(),false);
    }
}
