package com.example.dognutritionapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class GuidelineActivity extends AppCompatActivity {

    private VideoView videoView1;
    private VideoView videoView2;
    private VideoView videoView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline);

        videoView1 = findViewById(R.id.videoView1);
        videoView2 = findViewById(R.id.videoView2);
        videoView3 = findViewById(R.id.videoView3);

        // Replace with your actual video URIs
        Uri uri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v4);
        Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v6);
        Uri uri3 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v5);

        videoView1.setVideoURI(uri1);
        videoView2.setVideoURI(uri2);
        videoView3.setVideoURI(uri3);

        videoView1.start();
        videoView2.start();
        videoView3.start();
    }
}
