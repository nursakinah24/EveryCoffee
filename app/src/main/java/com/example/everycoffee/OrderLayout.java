package com.example.everycoffee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.everycoffee.ViewHolder.CartViewHolder;
import com.example.everycoffee.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderLayout extends AppCompatActivity {
    private TextView txtUsername;
    private RecyclerView productList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        UserID = getIntent().getStringExtra("uid");

        txtUsername = findViewById(R.id.order_user_name);
        productList = findViewById(R.id.order_recyclerview);
        productList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        cartListRef= FirebaseDatabase.getInstance().getReference()
                .child("cart list").child("Admin View").child("phone").child("Product");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(cartListRef, CartModel.class)
                        .build();
        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final CartModel model) {
                        holder.txtProductName.setText(model.getProductName());
                        holder.txtProductQuantity.setText(" quantity = " + model.getQuantity());
                        holder.txtProductPrice.setText("price "+ model.getPrice());
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                        CartViewHolder holder= new CartViewHolder(view);
                        return holder;
                    }
                };
        productList.setAdapter(adapter);
        adapter.startListening();

    }
}
