package com.example.UI.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.global:
                        startActivity(new Intent(getApplicationContext()
                                , WorldActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.egypt:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                , CountryWiseDataActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        getSupportActionBar().setTitle("About us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void openInsta(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/_iamvoldemort")));
    }

    public void openLinkedin(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ir.linkedin.com/public-profile/in/alireza-safarzadeh-186a001a3?challengeId=AQEbW0v6yqgUZgAAAXQXWYC8YCoyFG2CF_aWbUmOUDBeunNWi-RsBZoDJnla1tePBVrl7y22g-5U_D5xQxNmNzoyXjI-ZxcCYQ&submissionId=08c99269-49a9-2d16-1d44-653b822ee712")));
    }
}