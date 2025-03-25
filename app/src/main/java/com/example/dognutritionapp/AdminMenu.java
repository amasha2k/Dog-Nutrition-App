package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMenu extends AppCompatActivity {
    Button btnViewCustomers, btnManageProducts;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        btnViewCustomers = findViewById(R.id.btnViewOurProducts);
        btnManageProducts = findViewById(R.id.btnViewOurProducts);

    }
        public void ViewCustomers (View view){
            Intent intent = new Intent(this, ViewCustomerActivity.class);
            startActivity(intent);
        }


        public void ManageProducts(View view){
            Intent intent = new Intent (this,ManageProducts.class);
            startActivity(intent);
        }

    public void ViewOurProducts(View view){
        Intent intent = new Intent (this,ViewOurProducts.class);
        startActivity(intent);
    }

    public void ViewSales(View view){
        Intent intent = new Intent (this,ViewSales.class);
        startActivity(intent);
    }

    public void Exit(View view){
        this.finish();
    }

    public void ViewReview(View view){
        Intent intent = new Intent (this,ReviewActivity.class);
        startActivity(intent);
    }

    public void Logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
