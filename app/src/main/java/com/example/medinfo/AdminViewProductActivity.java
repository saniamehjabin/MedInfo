package com.example.medinfo;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminViewProductActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_product);

        listViewProducts = findViewById(R.id.list_view_products);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);
        databaseHelper = new DatabaseHelper(this);

        displayProducts();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Update button click
                handleUpdate();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Delete button click
                handleDelete();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed products
        displayProducts();
    }

    private void displayProducts() {
        Cursor cursor = databaseHelper.getAllProducts();
        ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
        listViewProducts.setAdapter(adapter);
    }

    private void handleUpdate() {
        // Logic for updating a product
        Intent intent = new Intent(AdminViewProductActivity.this, UpdateProductActivity.class);
        startActivity(intent);
    }
    //
    private void handleDelete() {
        Intent intent = new Intent(AdminViewProductActivity.this, DeleteProductActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();
    }
}