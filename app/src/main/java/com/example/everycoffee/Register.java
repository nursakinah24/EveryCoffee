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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity
{
    private EditText Namereg, Usernamereg, Phonereg, Emailreg, Passwordreg;
    private ProgressDialog loadingBar;
    private Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Namereg = findViewById(R.id.regName);
        Usernamereg = findViewById(R.id.regUsername);
        Phonereg = findViewById(R.id.regPhone);
        Emailreg = findViewById(R.id.regEmail);
        Passwordreg = findViewById(R.id.regPassword);
        loadingBar = new ProgressDialog(Register.this);
        button_register = findViewById(R.id.register_button);

        button_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveData();
            }
        });
    }

    private void saveData()
    {
        String m_name = Namereg.getText().toString();
        String m_username = Usernamereg.getText().toString();
        String m_phone = Phonereg.getText().toString();
        String m_email = Emailreg.getText().toString();
        String m_pass = Passwordreg.getText().toString();

        if(TextUtils.isEmpty(m_name))
        {
            Toast.makeText(this, "Please Write name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(m_username))
        {
            Toast.makeText(this, "Please Write username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(m_phone))
        {
            Toast.makeText(this, "Please Write phone", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(m_email))
        {
            Toast.makeText(this, "Please Write email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(m_pass))
        {
            Toast.makeText(this, "Please Write password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait, While We are Check Credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validate(m_name, m_username, m_phone, m_email, m_pass);
        }
    }

    private void validate(final String m_name, final String m_username, final String m_phone, final String m_email, final String m_pass)
    {
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("User").child("Members").child(m_username).exists()))
                {
                    HashMap<String, Object> UserDataMap = new HashMap<>();

                    UserDataMap.put("m_name",m_name);
                    UserDataMap.put("m_username",m_username);
                    UserDataMap.put("m_phone",m_phone);
                    UserDataMap.put("m_email",m_email);
                    UserDataMap.put("m_pass",m_pass);


                    db.child("User").child("Student").child(m_username).updateChildren(UserDataMap).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Register.this, "Congratulation your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(Register.this, "Network error, please try again after some time..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "this "+ m_username + " already exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "please try again using another username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}