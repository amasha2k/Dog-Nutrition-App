package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFoodProducts;
    private FoodAdapter foodAdapter;
    private List<Food> foodProductList = new ArrayList<>();
    private DatabaseHelper myDb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerViewFoodProducts = findViewById(R.id.recyclerViewFoodProducts);
        recyclerViewFoodProducts.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(this, foodProductList);
        recyclerViewFoodProducts.setAdapter(foodAdapter);

        myDb = new DatabaseHelper(this);
        loadFoodProducts();
    }

    private void loadFoodProducts() {
        List<Food> foodProducts = myDb.getFoodsByCategory("Food");
        foodProductList.clear();
        foodProductList.addAll(foodProducts);
        foodAdapter.notifyDataSetChanged();
    }
}