package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                Log.d("LoginActivity", "Email: '" + email + "'");
                Log.d("LoginActivity", "Password: '" + password + "'");

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "No email and password provided", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "No email provided", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "No password provided", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAuthenticated = authenticateUser(email, password);
                    if (isAuthenticated) {
                        saveEmailInSharedPreferences(email);
                        Intent intent;
                        if (email.equals("admin@gmail.com")) {
                            intent = new Intent(LoginActivity.this, AdminMenu.class);
                        } else {
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("CUSTOMER_EMAIL", email);
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void SignUp(View view){
        Intent intent = new Intent (this,RegistrationActivity.class);
        startActivity(intent);
    }

    private boolean authenticateUser(String email, String password) {
        if (email.equals("admin@gmail.com") && password.equals("admin")) {
            return true;
        }

        Cursor cursor = null;
        try {
            cursor = myDb.getUserByEmail(email);
            if (cursor != null && cursor.moveToFirst()) {
                String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow("PASSWORD"));
                return dbPassword.equals(password);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    private void saveEmailInSharedPreferences(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }
}
