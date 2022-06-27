package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everycoffee.model.CartModel;
import com.example.everycoffee.ViewHolder.CartViewHolder;
import com.example.everycoffee.model.Product;
import com.example.everycoffee.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalAmount;

    private DatabaseReference cartListRef;

    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn = findViewById(R.id.next_process_btn);
        txtTotalAmount = findViewById(R.id.total_price);

        cartListRef = FirebaseDatabase.getInstance().getReference().child("cart list").child("User View").child(Prevalent.CurrentOnlineUser.getM_username()).child("Product");

        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTotalAmount.setText("Total Price = " + String.valueOf(overTotalPrice));
                Intent intent = new Intent(Cart.this, OrderConfirmation.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(cartListRef, CartModel.class)
                        .build();

        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final CartModel model) {
                holder.txtProductName.setText(model.getProductName());
                holder.txtProductQuantity.setText(" quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("price " + model.getPrice());

                int oneTypeProductPrice = Integer.valueOf(model.getPrice()) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTypeProductPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit", "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //Jika indeks == 0 atau edit maka kita lempar ke Product detail activity dengan pid tertentu
                                if (i == 0) {
                                    Intent intent = new Intent(Cart.this, DetailProduct.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                // Jika indeks == 1 atau Remove maka hapus dengan pid tertentu
                                if (i == 1) {
                                    final DatabaseReference dbproduct = FirebaseDatabase.getInstance().getReference().child("Product").child(model.getPid());
                                    dbproduct.addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot)
                                        {
                                            Product product = snapshot.getValue(Product.class);

                                            String amountproduct = product.getStock();
                                            String amountrcart = model.getQuantity();
                                            int refresh = Integer.parseInt(amountproduct) + Integer.parseInt(amountrcart);
                                            String finalamount = String.valueOf(refresh);

                                            HashMap<String, Object> Map = new HashMap<>();
                                            Map.put("stock", finalamount);

                                            dbproduct.updateChildren(Map).addOnCompleteListener(new OnCompleteListener<Void>()
                                            {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    cartListRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>()
                                                    {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task)
                                                        {
                                                            if (task.isSuccessful())
                                                            {
                                                                Toast.makeText(Cart.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(Cart.this, Cart.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error)
                                        {
                                        }
                                    });
                                }

                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}