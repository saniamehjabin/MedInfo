package com.example.medinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertProductActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productDescriptionEditText;
    private EditText productDosageEditText;
    private EditText productSideEffectsEditText;
    private ImageView selectedImageView;
    private Button selectedImageButton;
    private Button insertProductButton;
    private DatabaseHelper databaseHelper;
    private byte[] imageByteArray;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        productNameEditText = findViewById(R.id.et_product_name);
        productPriceEditText = findViewById(R.id.et_product_price);
        productDescriptionEditText = findViewById(R.id.et_product_description);
        productDosageEditText = findViewById(R.id.et_product_dosage);
        selectedImageView = findViewById(R.id.iv_selected_image);
        productSideEffectsEditText = findViewById(R.id.et_product_side_effects);
        selectedImageButton = findViewById(R.id.btn_select_image);
        insertProductButton = findViewById(R.id.btn_insert_product);

        databaseHelper = new DatabaseHelper(this);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        selectedImageButton.setOnClickListener(view -> showImageSelectionDialog());

        insertProductButton.setOnClickListener(view -> {
            boolean inserted = insertProduct();
            if (inserted) {
                Toast.makeText(InsertProductActivity.this, "Successfully Inserted!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(InsertProductActivity.this, "Failed to insert product", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(InsertProductActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        });
    }

    private void showImageSelectionDialog() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private boolean insertProduct() {
        String name = productNameEditText.getText().toString().trim();
        String description = productDescriptionEditText.getText().toString().trim();
        String priceStr = productPriceEditText.getText().toString().trim();
        String dosageStr = productDosageEditText.getText().toString().trim();
        String sideEffects = productSideEffectsEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || sideEffects.isEmpty() || priceStr.isEmpty() || dosageStr.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return false;
        }

        double price;
        int dosage;
        try {
            price = Double.parseDouble(priceStr);
            dosage = Integer.parseInt(dosageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid price and dosage", Toast.LENGTH_SHORT).show();
            return false;
        }

        return databaseHelper.insertProduct(name, price, description, dosage, sideEffects, imageByteArray);


    }
}
