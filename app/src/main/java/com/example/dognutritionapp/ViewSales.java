package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class ViewSales extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BuyAdapter buyAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sales);

        recyclerView = findViewById(R.id.recyclerViewSales);
        databaseHelper = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchSalesRecords();
    }

    private void fetchSalesRecords() {
        List<Buy> buyList = databaseHelper.getAllSales();
        if (buyList != null && !buyList.isEmpty()) {
            buyAdapter = new BuyAdapter(this, buyList);
            recyclerView.setAdapter(buyAdapter);
        } else {
            Toast.makeText(this, "No sales records found", Toast.LENGTH_SHORT).show();
        }

    }
}