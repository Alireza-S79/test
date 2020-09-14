package com.example.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19tracker.R;

public class Splash extends AppCompatActivity {

    Animation sideAnim;
    TextView covid_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        covid_tv = findViewById(R.id.covid_tv);
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        covid_tv.setAnimation(sideAnim);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Splash.this, Main.class);
            startActivity(intent);
            finish();
        }, 2250);
    }
}