package com.example.dognutritionapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerReviewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etComment;
    private Button btnSubmitReview;
    private DatabaseHelper databaseHelper;
    private String customerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_review);

        etName = findViewById(R.id.etName);
        etComment = findViewById(R.id.etComment);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        databaseHelper = new DatabaseHelper(this);

        customerEmail = getIntent().getStringExtra("CUSTOMER_EMAIL");

        btnSubmitReview.setOnClickListener(view -> submitReview());
    }

    private void submitReview() {
        String name = etName.getText().toString().trim();
        String comment = etComment.getText().toString().trim();

        if (name.isEmpty() || comment.isEmpty()) {
            Toast.makeText(this, "Please enter both name and comment", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = databaseHelper.insertReview(customerEmail, name, comment);

        if (isInserted) {
            Toast.makeText(this, "Submitted successfully and thank you", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etComment.setText("");
        } else {
            Toast.makeText(this, "Failed to submit review", Toast.LENGTH_SHORT).show();
        }
    }
}
