package com.example.everycoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everycoffee.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    private EditText Usernamelog, Passwordlog;
    private TextView register_tap;
    private ProgressDialog loadBar;
    private Button button_login;
    private String DbName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usernamelog = findViewById(R.id.logUsername);
        Passwordlog = findViewById(R.id.logPassword);
        loadBar = new ProgressDialog(this);
        register_tap = findViewById(R.id.tap_register);
        button_login = findViewById(R.id.login_button);

        button_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loginUser();
            }
        });

        register_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser()
    {
        String username = Usernamelog.getText().toString();
        String password = Passwordlog.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please Enter Username!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
        }
        else{
            loadBar.setTitle("Login");
            loadBar.setMessage("Please wait");
            loadBar.setCanceledOnTouchOutside(false);
            loadBar.show();
            validate(username, password);
        }
    }

    private void validate(final String username, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child("Admins").child(username).exists())
                {
                    DbName = "Admins";
                    Users UsersData = dataSnapshot.child("Users").child(DbName).child(username).getValue(Users.class);
                    if (UsersData.getA_username().equals(username))
                    {
                        if (UsersData.getA_pass().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                            loadBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, AdminHome.class);
                            startActivity(intent);
                        }
                        else
                        {
                            loadBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is  Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if (dataSnapshot.child("Users").child("Members").child(username).exists())
                {
                    DbName = "Members";
                    Users UsersData = dataSnapshot.child("Users").child(DbName).child(username).getValue(Users.class);
                    if (UsersData.getM_username().equals(username))
                    {
                        if (UsersData.getM_pass().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Login Success...", Toast.LENGTH_SHORT).show();

                            loadBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, UserHome.class);
                            startActivity(intent);
                        }
                        else
                        {
                            loadBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Account with this " + username + " Username does not exist.", Toast.LENGTH_SHORT).show();
                        loadBar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}