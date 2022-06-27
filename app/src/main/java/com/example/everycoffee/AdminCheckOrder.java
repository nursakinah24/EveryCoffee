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
import com.example.everycoffee.model.AdminOrders;
import com.example.everycoffee.model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCheckOrder extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference orderRef;
    private String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_order);

        orderRef = FirebaseDatabase.getInstance().getReference().child("ViewOrders");

        orderList = findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(orderRef, Users.class)
                        .build();
        FirebaseRecyclerAdapter<Users, UsersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UsersViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Users model) {
                        holder.userName.setText("Name = " + model.getM_username());
                        holder.showDetailBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(AdminCheckOrder.this, DetailOrder.class);
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
                                            String uID = getRef(position).getKey();
                                            removeOrder(uID);
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
                    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
                        UsersViewHolder holder = new UsersViewHolder(view);
                        return holder;
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public Button showDetailBtn;
        public UsersViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_username);
            showDetailBtn = itemView.findViewById(R.id.show_detail_order);

        }
    }
    private void removeOrder(String uID) {
        orderRef.removeValue();
    }
}