package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NutritionActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNutritionProducts;
    private NutritionAdapter nutritionAdapter;
    private List<Nutrition> nutritionProductList = new ArrayList<>();
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        recyclerViewNutritionProducts = findViewById(R.id.recyclerViewNutritionProducts);
        recyclerViewNutritionProducts.setLayoutManager(new LinearLayoutManager(this));
        nutritionAdapter = new NutritionAdapter(this, nutritionProductList);
        recyclerViewNutritionProducts.setAdapter(nutritionAdapter);

        myDb = new DatabaseHelper(this);
        loadNutritionProducts();
    }

    private void loadNutritionProducts() {
        List<Nutrition> nutritionProducts = myDb.getNutritionByCategory("Nutrition");
        nutritionProductList.clear();
        nutritionProductList.addAll(nutritionProducts);
        nutritionAdapter.notifyDataSetChanged();
    }
}