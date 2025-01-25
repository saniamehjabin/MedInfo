package com.example.medinfo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    private ListView listViewItems;
    private Button btnSearch, btnAdd;
    private EditText editTextSearch;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listViewItems = findViewById(R.id.list_view_items);
        btnSearch = findViewById(R.id.btn_search);

        editTextSearch = findViewById(R.id.edit_text_search);

        databaseHelper = new DatabaseHelper(this);


        displayItems();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle search button click
                searchProduct();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed items
        displayItems();
    }

    private void displayItems() {
        Cursor cursor = databaseHelper.getAllProducts();
        ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
        listViewItems.setAdapter(adapter);
    }

    private void searchProduct() {
        String productName = editTextSearch.getText().toString().trim();

        if (productName.isEmpty()) {
            Toast.makeText(this, "Please enter a product name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query the database for the specific product
        Cursor cursor = databaseHelper.getProductByName(productName);

        if (cursor != null && cursor.moveToFirst()) {
            // Create a new adapter for the search result and set it to the ListView
            ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
            listViewItems.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Medicine not found", Toast.LENGTH_SHORT).show();
        }
    }



}

