package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private RecyclerView recyclerViewArticles;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        recyclerViewArticles = findViewById(R.id.recyclerViewArticles);
        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));

        articleList = new ArrayList<>();
        articleList.add(new Article(R.drawable.ar1));
        articleList.add(new Article(R.drawable.ar2));
        articleList.add(new Article(R.drawable.ar3));

        articleAdapter = new ArticleAdapter(articleList);
        recyclerViewArticles.setAdapter(articleAdapter);

    }
}