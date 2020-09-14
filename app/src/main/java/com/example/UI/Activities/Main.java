package com.example.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.ViewModels.IRViewModel;
import com.example.ViewModels.FinalViewModel;
import com.example.ViewModels.HomeViewModel;
import com.example.ViewModels.WorldViewModel;
import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_main);

        IRViewModel egyptViewModel = new ViewModelProvider(this).get(IRViewModel.class);
        WorldViewModel worldViewModel = new ViewModelProvider(this).get(WorldViewModel.class);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        FinalViewModel finalViewModel = new ViewModelProvider(this).get(FinalViewModel.class);

        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getItemId() == R.id.egyptFragment) {
                if (egyptViewModel.getIsLoading().getValue() != null
                        && !egyptViewModel.getIsLoading().getValue()) {
                    egyptViewModel.refresh();
                }
            } else if (item.getItemId() == R.id.worldFragment) {
                if (worldViewModel.getIsLoading().getValue() != null
                        && !worldViewModel.getIsLoading().getValue()) {
                    worldViewModel.refresh();
                }
            } else if (item.getItemId() == R.id.homeFragment) {
                if (homeViewModel.getIsLoading().getValue() != null
                        && !homeViewModel.getIsLoading().getValue()) {
                    homeViewModel.refresh();
                }
            } else if (item.getItemId() == R.id.finalFragment) {
                if (finalViewModel.getIsLoading().getValue() != null
                        && !finalViewModel.getIsLoading().getValue()) {
                    finalViewModel.refresh();
                }
            }
            if (item.getItemId() == R.id.egyptFragment) {
                if (egyptViewModel.getIsLoading().getValue() != null
                        && !egyptViewModel.getIsLoading().getValue()) {
                    egyptViewModel.refresh();
                }
            }
           else {
                item.getItemId();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            backPressToast.cancel();
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        backPressToast = Toast.makeText(this, "برای خروج لطفاً دوباره روی بازگشت کلیک کنید", Toast.LENGTH_SHORT);
        backPressToast.show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fsearch) {
            Intent intent = new Intent(Main.this, CountryWise.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}