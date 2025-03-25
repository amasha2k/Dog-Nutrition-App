package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTextView = findViewById(R.id.emailTextView);

        Intent intent = getIntent();
        String email = intent.getStringExtra("CUSTOMER_EMAIL");

        if (email != null && !email.isEmpty()) {
            emailTextView.setText("Welcome: " + email);
        } else {
            emailTextView.setText("Welcome");
        }
    }

    public void allProducts(View view){
        Intent intent = new Intent (this,ViewOurProducts.class);
        startActivity(intent);
    }

    public void Exit(View view){
        finish();
    }

    public void viewFood(View view){
        Intent intent = new Intent (this,FoodActivity.class);
        startActivity(intent);
    }

    public void viewNutrition(View view){
        Intent intent = new Intent (this,NutritionActivity.class);
        startActivity(intent);
    }

    public void viewOther(View view){
        Intent intent = new Intent (this,OtherActivity.class);
        startActivity(intent);
    }

    public void Buy(View view){
        Intent intent = new Intent (this,BuyActivity.class);
        startActivity(intent);
    }

    public void Article(View view){
        Intent intent = new Intent (this,ArticleActivity.class);
        startActivity(intent);
    }

    public void Profile(View view){
        Intent intent = new Intent (this,EditProfileActivity.class);
        startActivity(intent);
    }

    public void Review(View view){
        Intent intent = new Intent (this,CustomerReviewActivity.class);
        startActivity(intent);
    }

    public void Guide(View view){
        Intent intent = new Intent (this,GuidelineActivity.class);
        startActivity(intent);
    }

    public void Review1(View view){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    public void Logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}