package com.example.dognutritionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.ViewHolder> {
    private Context context;
    private List<Buy> buyList;

    public BuyAdapter(Context context, List<Buy> buyList) {
        this.context = context;
        this.buyList = buyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buy buy = buyList.get(position);
        holder.textViewCustomerName.setText(buy.getCustomerName());
        holder.textViewAddress.setText(buy.getAddress());
        holder.textViewMobileNumber.setText(buy.getMobileNumber());
        holder.textViewProductID.setText(String.valueOf(buy.getProductId()));
        holder.textViewQuantity.setText(String.valueOf(buy.getQuantity()));
        holder.textViewTotalPrice.setText(String.valueOf(buy.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return buyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCustomerName, textViewAddress, textViewMobileNumber, textViewProductID, textViewQuantity, textViewTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewMobileNumber = itemView.findViewById(R.id.textViewMobileNumber);
            textViewProductID = itemView.findViewById(R.id.textViewProductID);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewTotalPrice = itemView.findViewById(R.id.textViewTotalPrice);
        }
    }
}
