package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {
    private TextInputEditText nameEdit, emailEdit, passwordEdit;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        nameEdit = findViewById(R.id.nameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        saveButton = findViewById(R.id.saveButton);
        databaseHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);

        if (email != null) {
            populateUserDetails(email);
        } else {
            Toast.makeText(this, "No email provided in SharedPreferences", Toast.LENGTH_SHORT).show();
        }

        saveButton.setOnClickListener(v -> updateDetails(email));
    }

    private void populateUserDetails(String email) {
        Cursor cursor = databaseHelper.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") String emailFetched = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
            nameEdit.setText(name);
            emailEdit.setText(emailFetched);
            passwordEdit.setText(password);
            cursor.close();
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDetails(String oldEmail) {
        String name = nameEdit.getText().toString();
        String newEmail = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        boolean isUpdated = databaseHelper.updateDetails(oldEmail, name, newEmail, password);

        if (isUpdated) {
            Toast.makeText(this, "Profile details updated successfully", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", newEmail);
            editor.apply();
        } else {
            Toast.makeText(this, "Failed to update profile details", Toast.LENGTH_SHORT).show();
        }
    }
}
