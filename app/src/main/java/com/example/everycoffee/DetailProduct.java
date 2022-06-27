package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everycoffee.model.Product;
import com.example.everycoffee.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailProduct extends AppCompatActivity {
    private Button addToCartBtn;
    private ImageView product_Image;
    private ElegantNumberButton Quantity;
    private TextView productPriceDetail, productDescriptionDetail, productNameDetail, productStockDetail;
    private String productID = "", state = "Normal";
    private String stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        productID = getIntent().getStringExtra("pid");
        Quantity = findViewById(R.id.elegantBtn);
        product_Image = findViewById(R.id.product_detail_image);
        productPriceDetail= findViewById(R.id.product_detail_price);
        productNameDetail = findViewById(R.id.product_detail_name);
        productDescriptionDetail = findViewById(R.id.product_detail_desc);
        productStockDetail = findViewById(R.id.productStock);
        addToCartBtn = findViewById(R.id.add_cart_button);
        
        getProductDetail(productID);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {addingToCartList();
            }
        });
    }
    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("cart list");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("productName", productNameDetail.getText().toString());
        cartMap.put("price", productPriceDetail.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", Quantity.getNumber());

        final String qty1 = Quantity.getNumber();
        int qty2 = Integer.parseInt(stock) -  Integer.parseInt(qty1);
        final String qty3 = String.valueOf(qty2);

        cartListRef.child("User View").child(Prevalent.CurrentOnlineUser.getM_username())
                .child("Product").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin View").child(Prevalent.CurrentOnlineUser.getM_username())
                                    .child("Product").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                DatabaseReference Ref= FirebaseDatabase.getInstance().getReference().child("Product").child(productID);
                                                HashMap<String, Object> Map = new HashMap<>();
                                                Map.put("stock", qty3);
                                                Ref.updateChildren(Map).addOnCompleteListener(new OnCompleteListener<Void>()
                                                {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(DetailProduct.this, "Added to cart list", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(DetailProduct.this, UserHome.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void getProductDetail(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Product");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Product product = dataSnapshot.getValue(Product.class);
                    productNameDetail.setText(product.getProductName());
                    productPriceDetail.setText(product.getPrice());
                    productDescriptionDetail.setText(product.getDescription());
                    productStockDetail.setText("Stock: " + product.getStock());
                    Quantity.setRange(0,Integer.parseInt(product.getStock()));
                    Picasso.get().load(product.getImage()).into(product_Image);
                    stock = product.getStock();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}