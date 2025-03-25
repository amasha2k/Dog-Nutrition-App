package com.example.dognutritionapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder> {
    private List<Other> otherList;
    private Context context;

    public OtherAdapter(Context context, List<Other> otherList) {
        this.context = context;
        this.otherList = otherList;
    }

    @NonNull
    @Override
    public OtherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_other, parent, false);
        return new OtherViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, int position) {
        Other other = otherList.get(position);
        holder.textViewID.setText(String.format("ID: %d", other.getId()));
        holder.textViewName.setText(other.getName());
        holder.textViewDescription.setText(other.getDescription());
        holder.textViewPrice.setText(String.format("%.2f", other.getPrice()));

        if (other.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(other.getImage(), 0, other.getImage().length);
            holder.imageViewOther.setImageBitmap(bitmap);
        } else {
            holder.imageViewOther.setImageResource(android.R.color.transparent);
        }
    }

    @Override
    public int getItemCount() {
        return otherList.size();
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, textViewName, textViewDescription, textViewPrice;
        ImageView imageViewOther;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageViewOther = itemView.findViewById(R.id.imageViewOther);
        }
    }
}
