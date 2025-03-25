package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class OtherActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        recyclerView = findViewById(R.id.recyclerViewOther);

        databaseHelper = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchOtherByCategory("Other");
    }

    private void fetchOtherByCategory(String category) {
        List<Other> otherList = databaseHelper.getOtherByCategory(category);
        if (otherList != null && !otherList.isEmpty()) {
            OtherAdapter otherAdapter = new OtherAdapter(this, otherList);
            recyclerView.setAdapter(otherAdapter);
        } else {
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        }
    }
}