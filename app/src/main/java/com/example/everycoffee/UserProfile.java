package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.everycoffee.model.Users;
import com.example.everycoffee.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {
    private TextView txtName, txtUsername, txtPhone, txtEmail;
    private Button logoutUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtName = findViewById(R.id.profileName);
        txtUsername = findViewById(R.id.profileUsername);
        txtPhone = findViewById(R.id.profilePhone);
        txtEmail = findViewById(R.id.profileEmail);
        logoutUser = findViewById(R.id.user_logout_button);

        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        getUserData();

    }

    private void getUserData() {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Members");
        productsRef.child(Prevalent.CurrentOnlineUser.getM_username()).addValueEventListener(new ValueEventListener() {
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