package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ViewOurProducts extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_our_products);

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        databaseHelper = new DatabaseHelper(this);

        List<Product> productList = getAllProducts();
        productAdapter = new ProductAdapter(this, productList);
        recyclerViewProducts.setAdapter(productAdapter);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCTS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_PRICE));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_IMAGE));
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COLUMN_CATEGORY));
                productList.add(new Product(id, name, description, price, image, category));
                Log.d("Product", "ID: " + id + ", Name: " + name + ", Description: " + description + ", Price: " + price + ", Category: " + category);
            }
            cursor.close();
        }
        db.close();
        return productList;
    }
}