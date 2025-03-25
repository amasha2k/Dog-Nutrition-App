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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewID.setText("" + product.getId());
        holder.textViewName.setText("" + product.getName());
        holder.textViewDescription.setText("" + product.getDescription());
        holder.textViewPrice.setText(String.format("%.2f", product.getPrice()));
        holder.textViewCategory.setText("" + product.getCategory());

        if (product.getImage() != null && product.getImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            holder.imageViewProduct.setImageBitmap(bitmap);
        } else {
            holder.imageViewProduct.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewID, textViewName, textViewDescription, textViewPrice, textViewCategory;
        public ImageView imageViewProduct;

        public ProductViewHolder(View view) {
            super(view);
            textViewID = view.findViewById(R.id.textViewID);
            textViewName = view.findViewById(R.id.textViewName);
            textViewDescription = view.findViewById(R.id.textViewDescription);
            textViewPrice = view.findViewById(R.id.textViewPrice);
            textViewCategory = view.findViewById(R.id.textViewCategory);
            imageViewProduct = view.findViewById(R.id.imageViewProduct);
        }
    }
}
