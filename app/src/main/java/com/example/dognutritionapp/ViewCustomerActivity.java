package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomerActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewCustomers;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        recyclerViewCustomers = findViewById(R.id.recyclerViewCustomers);
        databaseHelper = new DatabaseHelper(this);

        List<User> userList = getAllUsers();
        userAdapter = new UserAdapter(userList);
        recyclerViewCustomers.setAdapter(userAdapter);
        recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
                userList.add(new User(id, name, email));
            }
            cursor.close();
        }
        return userList;
    }
}