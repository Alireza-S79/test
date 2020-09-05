package com.example.UI.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.ViewModels.EgyptViewModel;
import com.example.ViewModels.WorldViewModel;
import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.navigation);

        EgyptViewModel egyptViewModel = new ViewModelProvider(this).get(EgyptViewModel.class);
        WorldViewModel worldViewModel = new ViewModelProvider(this).get(WorldViewModel.class);
        navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.egyptFragment) {
                    if (egyptViewModel.getIsLoading().getValue() != null
                            && !egyptViewModel.getIsLoading().getValue()) {
                        egyptViewModel.refresh();
                    }
                }else if (item.getItemId() == R.id.worldFragment) {
                    if (worldViewModel.getIsLoading().getValue() != null
                            && !worldViewModel.getIsLoading().getValue()) {
                        worldViewModel.refresh();
                    }
                }
            }
        });
    }
}