package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BuyActivity extends AppCompatActivity {
    private Spinner spinnerProductIds;
    private EditText editTextQuantity, editTextCustomerName, editTextAddress, editTextMobileNumber, editTextCardNumber, editTextCVV;
    private TextView textViewTotalPrice;
    private Button buttonCalculateTotal;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        spinnerProductIds = findViewById(R.id.spinnerProductIds);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextCVV = findViewById(R.id.editTextCVV);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonCalculateTotal = findViewById(R.id.buttonCalculateTotal);
        databaseHelper = new DatabaseHelper(this);

        loadProductIdsIntoSpinner();

        buttonCalculateTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPrice();
            }
        });
    }

    private void loadProductIdsIntoSpinner() {
        List<Integer> productIds = databaseHelper.getAllProductIds();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductIds.setAdapter(adapter);
    }

    private void calculateTotalPrice() {
        String customerName = editTextCustomerName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String cardNumber = editTextCardNumber.getText().toString().trim();
        String cvv = editTextCVV.getText().toString().trim();
        String quantityText = editTextQuantity.getText().toString().trim();

        if (customerName.isEmpty() || address.isEmpty() || mobileNumber.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty() || quantityText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                Toast.makeText(this, "Quantity must be greater than zero", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedProductId = (int) spinnerProductIds.getSelectedItem();
        double productPrice = databaseHelper.getProductPriceById(selectedProductId);
        double totalPrice = productPrice * quantity;

        textViewTotalPrice.setText("Total Price: LKR" + totalPrice);

        long result = databaseHelper.insertBuyRecord(selectedProductId, customerName, address, mobileNumber, cardNumber, cvv, quantity, totalPrice);
        if (result != -1) {
            Toast.makeText(this, "Purchased successfully", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            Toast.makeText(this, "Failed to add purchase record", Toast.LENGTH_SHORT).show();
            Log.e("BuyActivity", "Failed to add purchase record for product ID: " + selectedProductId);
        }
    }

    private void clearInputFields() {
        editTextCustomerName.setText("");
        editTextAddress.setText("");
        editTextMobileNumber.setText("");
        editTextCardNumber.setText("");
        editTextCVV.setText("");
        editTextQuantity.setText("");

        spinnerProductIds.setSelection(0);
    }
}