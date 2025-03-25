package com.example.dognutritionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ManageProducts extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 200;
    DatabaseHelper myDb;
    EditText editTextProductName, editTextProductDescription, editTextProductPrice, editTextProductID;
    Spinner spinnerProductCategory;
    Button buttonSelectImage, buttonAddProduct, buttonFindProduct, buttonUpdateProduct, buttonDeleteProduct;
    ImageView imageViewProduct;
    Bitmap selectedImageBitmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        // Check for storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
        }

        myDb = new DatabaseHelper(this);

        editTextProductID = findViewById(R.id.editTextProductID);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        spinnerProductCategory = findViewById(R.id.spinnerProductCategory);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonFindProduct = findViewById(R.id.buttonFindProduct);
        buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);
        buttonDeleteProduct = findViewById(R.id.buttonDeleteProduct);
        imageViewProduct = findViewById(R.id.imageViewProduct);

        setupCategorySpinner();

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        buttonFindProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findProductByID();
            }
        });

        buttonUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

        buttonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Manage Products", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Manage Products", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductCategory.setAdapter(adapter);
    }

    private void clearFields() {
        editTextProductID.setText("");
        editTextProductName.setText("");
        editTextProductDescription.setText("");
        editTextProductPrice.setText("");
        imageViewProduct.setImageResource(0);
        selectedImageBitmap = null;
        spinnerProductCategory.setSelection(0);
    }

    private void addProduct() {
        String name = editTextProductName.getText().toString();
        String description = editTextProductDescription.getText().toString();
        String priceStr = editTextProductPrice.getText().toString();
        String category = spinnerProductCategory.getSelectedItem().toString();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(ManageProducts.this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(ManageProducts.this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        boolean isInserted = myDb.insertProduct(name, description, price, image, category);
        if (isInserted) {
            Toast.makeText(ManageProducts.this, "Product added", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Log.e("ManageProducts", "Failed to add product");
            Toast.makeText(ManageProducts.this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
    }

    private void findProductByID() {
        String idStr = editTextProductID.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Please enter a product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid ID format", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = myDb.getProductByID(id);
        if (product != null) {
            editTextProductName.setText(product.getName());
            editTextProductDescription.setText(product.getDescription());
            editTextProductPrice.setText(String.format("%.2f", product.getPrice()));
            spinnerProductCategory.setSelection(((ArrayAdapter)spinnerProductCategory.getAdapter()).getPosition(product.getCategory()));

            if (product.getImage() != null && product.getImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
                imageViewProduct.setImageBitmap(bitmap);
            } else {
                imageViewProduct.setImageDrawable(null);
            }
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProduct() {
        String idStr = editTextProductID.getText().toString();
        String name = editTextProductName.getText().toString();
        String description = editTextProductDescription.getText().toString();
        String priceStr = editTextProductPrice.getText().toString();
        String category = spinnerProductCategory.getSelectedItem().toString();

        if (idStr.isEmpty() || name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(ManageProducts.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(ManageProducts.this, "Invalid ID format", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(ManageProducts.this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (selectedImageBitmap != null) {
            selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        }
        byte[] image = stream.toByteArray();

        boolean isUpdated = myDb.updateProduct(id, name, description, price, image, category);
        if (isUpdated) {
            Toast.makeText(ManageProducts.this, "Product updated", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Log.e("ManageProducts", "Failed to update product");
            Toast.makeText(ManageProducts.this, "Failed to update product", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                imageViewProduct.setImageBitmap(selectedImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ManageProducts", "Failed to select image", e);
                Toast.makeText(this, "Failed to select image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("ManageProducts", "Error getting selected files: resultCode = " + resultCode + ", data = " + data);
            Toast.makeText(this, "Error getting selected files", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProduct() {
        String idStr = editTextProductID.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(ManageProducts.this, "Please enter a product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(ManageProducts.this, "Invalid ID format", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isDeleted = myDb.deleteProduct(id);
        if (isDeleted) {
            Toast.makeText(ManageProducts.this, "Product deleted", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Log.e("ManageProducts", "Failed to delete product");
            Toast.makeText(ManageProducts.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
        }
    }
}
