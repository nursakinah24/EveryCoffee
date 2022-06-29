//package com.example.everycoffee;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
//import com.example.everycoffee.model.AdminOrders;
//import com.example.everycoffee.prevalent.Prevalent;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class DetailOrder extends AppCompatActivity {
//    private TextView txtUsername, txtName, txtPhone, txtTotalPrice, txtAddress, txtDate, txtTime;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.order_detail);
//
//        txtUsername = findViewById(R.id.order_user_name);
//        txtName = findViewById(R.id.order_name);
//        txtPhone = findViewById(R.id.order_phone_number);
//        txtTotalPrice = findViewById(R.id.order_total_price);
//        txtAddress = findViewById(R.id.order_address);
//        txtDate = findViewById(R.id.order_date);
//        txtTime = findViewById(R.id.order_time);
//
//        getOrderDetail();
//    }
//
//    private void getOrderDetail() {
//        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("ViewOrders");
//        productsRef.child(Prevalent.CurrentOnlineUser.getA_username()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    AdminOrders orders = snapshot.getValue(AdminOrders.class);
//                    txtUsername.setText(orders.getUsername());
//                    txtName.setText(orders.getName());
//                    txtPhone.setText(orders.getPhone());
//                    txtTotalPrice.setText(orders.getTotalAmount());
//                    txtAddress.setText(orders.getAddress());
//                    txtDate.setText(orders.getAddress());
//                    txtTime.setText(orders.getTime());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}