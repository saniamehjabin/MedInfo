package com.example.medinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnViewProduct = findViewById(R.id.btn_view_product);
        Button btnInsertProduct = findViewById(R.id.btn_insert_product);
        Button btnUpdateProduct = findViewById(R.id.btn_update_product);
        Button btnDeleteProduct = findViewById(R.id.btn_delete_product);

        btnViewProduct.setOnClickListener(v->{
            Intent intent = new Intent(AdminHomeActivity.this,AdminViewProductActivity.class);
            startActivity(intent);

        });

        btnInsertProduct.setOnClickListener(v->{
            Intent intent = new Intent(AdminHomeActivity.this,InsertProductActivity.class);
            startActivity(intent);

        });

        btnUpdateProduct.setOnClickListener(v->{
            Intent intent = new Intent(AdminHomeActivity.this,UpdateProductActivity.class);
            startActivity(intent);

        });

        btnDeleteProduct.setOnClickListener(v->{
            Intent intent = new Intent(AdminHomeActivity.this,DeleteProductActivity.class);
            startActivity(intent);

        });

    }
}