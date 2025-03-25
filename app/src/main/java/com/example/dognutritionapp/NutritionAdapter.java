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

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.NutritionViewHolder> {
    private List<Nutrition> nutritionList;
    private Context context;

    public NutritionAdapter(Context context, List<Nutrition> nutritionList) {
        this.context = context;
        this.nutritionList = nutritionList;
    }

    @NonNull
    @Override
    public NutritionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nutrition, parent, false);
        return new NutritionViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull NutritionViewHolder holder, int position) {
        Nutrition nutrition = nutritionList.get(position);
        holder.textViewID.setText(String.format("ID: %d", nutrition.getId()));
        holder.textViewName.setText(nutrition.getName());
        holder.textViewDescription.setText(nutrition.getDescription());
        holder.textViewPrice.setText(String.format("%.2f", nutrition.getPrice()));

        if (nutrition.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(nutrition.getImage(), 0, nutrition.getImage().length);
            holder.imageViewNutrition.setImageBitmap(bitmap);
        } else {
            holder.imageViewNutrition.setImageResource(android.R.color.transparent);
        }
    }

    @Override
    public int getItemCount() {
        return nutritionList.size();
    }

    public static class NutritionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, textViewName, textViewDescription, textViewPrice;
        ImageView imageViewNutrition;

        public NutritionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageViewNutrition = itemView.findViewById(R.id.imageViewNutrition);
        }
    }
}
