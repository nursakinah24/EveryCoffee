package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.everycoffee.ViewHolder.CartViewHolder;
import com.example.everycoffee.model.AdminOrders;
import com.example.everycoffee.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminOrderDetail extends AppCompatActivity {
    private TextView txtUsername, txtName, txtPhone, txtTotalPrice, txtAddress, txtDateTime;
    private String uID= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        uID = getIntent().getStringExtra("uid");

        txtUsername = findViewById(R.id.order_user_name);
        txtName = findViewById(R.id.order_name);
        txtPhone = findViewById(R.id.order_phone_number);
        txtTotalPrice = findViewById(R.id.order_total_price);
        txtAddress = findViewById(R.id.order_address_city);
        txtDateTime = findViewById(R.id.order_date_time);

        getOrderDetail();
    }

    private void getOrderDetail() {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("ViewOrders").child("orders");
        productsRef.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    AdminOrders orders = snapshot.getValue(AdminOrders.class);
                    txtUsername.setText(orders.getUsername());
                    txtName.setText("Name : " + orders.getName());
                    txtPhone.setText("Phone Number : " + orders.getPhone());
                    txtTotalPrice.setText("Total Price : " + orders.getTotalAmount());
                    txtAddress.setText("Home Address : " + orders.getAddress() + " ," + orders.getCity());
                    txtDateTime.setText("Date : " + orders.getDate() + "Time : " + orders.getTime());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}