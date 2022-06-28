package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.everycoffee.ViewHolder.AdminOrdersViewHolder;
import com.example.everycoffee.ViewHolder.CartViewHolder;
import com.example.everycoffee.model.AdminOrders;
import com.example.everycoffee.model.CartModel;
import com.example.everycoffee.model.Users;
import com.example.everycoffee.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCheckOrder extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference ordersRef, cartRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_order);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("ViewOrders").child("orders");
        cartRef = FirebaseDatabase.getInstance().getReference().child("cart list").child("AdminView");

        orderList = findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef, AdminOrders.class)
                        .build();
        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewholder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final AdminOrders model) {
                        holder.userName.setText("Name = " + model.getName());
                        holder.userPhoneNumber.setText("Phone = " + model.getPhone());
                        holder.userTotalAmount.setText("Total Amount= " + model.getTotalAmount());
                        holder.userDateTime.setText("Order at = " + model.getDate() + " " + model.getTime());
                        holder.userShippingAddress.setText("Shipping address = " + model.getAddress() + " " + model.getCity());
                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(AdminCheckOrder.this, AdminCheckProduct.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckOrder.this);
                                builder.setTitle("Have you shipped this order products?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i == 0){
//                                            String uID = getRef(position).getKey();
                                            removeOrder(model.getUsername());
                                        }   else{
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail,parent,false);
                        return new AdminOrdersViewholder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class AdminOrdersViewholder extends RecyclerView.ViewHolder{
        public TextView userName, userPhoneNumber,userTotalAmount, userDateTime, userShippingAddress;
        public Button showOrdersBtn;
        public AdminOrdersViewholder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber= itemView.findViewById(R.id.order_phone_number);
            userTotalAmount = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date);
            userShippingAddress = itemView.findViewById(R.id.order_address);
            showOrdersBtn = itemView.findViewById(R.id.show_detail_order);

        }
    }
    private void removeOrder(String uID) {
        ordersRef.child(uID).removeValue();
        cartRef.child(uID).removeValue();

    }
}
