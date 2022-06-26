package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.everycoffee.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {
    private TextView txtName, txtUsername, txtPhone, txtEmail;
    private Button logoutUser;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtName = findViewById(R.id.profileName);
        txtUsername = findViewById(R.id.profileUsername);
        txtPhone = findViewById(R.id.profilePhone);
        txtEmail = findViewById(R.id.profileEmail);
        logoutUser = findViewById(R.id.user_logout_button);

        getUserData(userID);

        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getUserData(String userID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Users");
        productsRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Users users = dataSnapshot.getValue(Users.class);
                    txtName.setText(users.getM_name());
                    txtUsername.setText(users.getM_username());
                    txtPhone.setText(users.getM_phone());
                    txtEmail.setText(users.getM_email());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}