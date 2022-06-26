package com.example.everycoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminManageProduct extends AppCompatActivity {

    private String Product, Description, Price, Stock, saveDate, saveTime, productRandomKey, downloadImageUrl;
    private Button btnSubmit;
    private ImageView selectImage;
    private EditText edName, edDesc, edPrice, edStock;
    private Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private ProgressDialog loadingBar;

    private static final int galleryPick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_product);

        storageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Product");
        loadingBar = new ProgressDialog(this);

        btnSubmit = findViewById(R.id.add_product_button);
        selectImage = findViewById(R.id.select_image);
        edName = findViewById(R.id.add_name);
        edDesc = findViewById(R.id.addDesc);
        edPrice = findViewById(R.id.addPrice);
        edStock = findViewById(R.id.addStock);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProduct();
            }
        });
    }

    private void openGallery() {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent, galleryPick);
    }

    private void validateProduct() {
        Product = edName.getText().toString();
        Description = edDesc.getText().toString();
        Price = edPrice.getText().toString();
        Stock = edStock.getText().toString();

        if(imageUri == null){
            Toast.makeText(this, "Product Image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Product)){
            Toast.makeText(this, "Please write product name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please write product description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please write product price", Toast.LENGTH_SHORT).show();
        }
        else{
            storeProduct();
        }
    }

    private void storeProduct() {
        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("please wait, while we are adding the new product..");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(calendar.getTime());

        productRandomKey = saveDate + saveTime;

        final StorageReference filePath = storageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask UploadTask = filePath.putFile(imageUri);
        UploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminManageProduct.this, "ERROR:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(com.google.firebase.storage.UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminManageProduct.this, "Image Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = UploadTask.continueWithTask(new Continuation<com.google.firebase.storage.UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<com.google.firebase.storage.UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }else{
                            downloadImageUrl =filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminManageProduct.this, "got the product Image url succcesfully...", Toast.LENGTH_SHORT).show();
                            saveProduct();
                        }
                    }
                });
            }
        });
    }

    private void saveProduct() {
        HashMap<String, Object> ProductMap = new HashMap<>();
        ProductMap.put("pid", productRandomKey);
        ProductMap.put("date",saveDate);
        ProductMap.put("time",saveTime);
        ProductMap.put("description", Description);
        ProductMap.put("image", downloadImageUrl);
        ProductMap.put("price",Price);
        ProductMap.put("productName", Product);
        ProductMap.put("stock", Stock);

        databaseRef.child(productRandomKey).updateChildren(ProductMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(AdminManageProduct.this, AdminMenu.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminManageProduct.this, "Product is added succesfully", Toast.LENGTH_SHORT).show();
                        }else{
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminManageProduct.this, "Error " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == galleryPick && resultCode == RESULT_OK && data!= null){
            imageUri = data.getData();
            selectImage.setImageURI(imageUri);
        }
    }
}