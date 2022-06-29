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

import com.example.everycoffee.ViewHolder.CartViewHolder;
import com.example.everycoffee.ViewHolder.OrderViewHolder;
import com.example.everycoffee.model.AdminOrders;
import com.example.everycoffee.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCheckOrder extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference ordersRef, cartRef;
    private String uID= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_order);

        uID = getIntent().getStringExtra("uid");

        ordersRef = FirebaseDatabase.getInstance().getReference().child("ViewOrders").child("orders");
        cartRef = FirebaseDatabase.getInstance().getReference().child("cart list").child("AdminView");


        orderList = findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>().setQuery(ordersRef, AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders, OrderViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, OrderViewHolder>(options)
        {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final AdminOrders model) {
                        holder.txtUserName.setText("Name = " + model.getName());
                        holder.recyclerView.setHasFixedSize(true);
                        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

                        FirebaseRecyclerOptions<CartModel> childOptions = new FirebaseRecyclerOptions.Builder<CartModel>().setQuery(cartRef.child(model.getUsername()).child("Product"), CartModel.class).build();

                        FirebaseRecyclerAdapter<CartModel, CartViewHolder> childAdapter = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(childOptions){

                            @NonNull
                            @Override
                            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                                CartViewHolder holder = new CartViewHolder(view);
                                return holder;
                            }

                            @Override
                            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartModel model) {
                                holder.txtProductName.setText(model.getProductName());
                                holder.txtProductQuantity.setText(" quantity = " + model.getQuantity());
                                holder.txtProductPrice.setText("price " + model.getPrice());
                            }
                        };
                        holder.recyclerView.setAdapter(childAdapter);
                        childAdapter.startListening();
                        holder.showDetailBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(AdminCheckOrder.this, AdminOrderDetail.class);
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
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
                        return new OrderViewHolder(view);
                    }
        };
        orderList.setAdapter(adapter);
        adapter.startListening();

    }

    private void removeOrder(String uID) {
        ordersRef.child(uID).removeValue();
        cartRef.child(uID).removeValue();
    }
}
