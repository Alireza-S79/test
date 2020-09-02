package com.example.UI.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Objects;

public class WorldActivity extends AppCompatActivity {

    TextView tv_tests_global, tv_time_global, tv_country_global,
            active_mil_global, recovered_mil_global, deaths_mil_global, population_global,
            critical_mil_global, cases_mil_global, tests_mil_global;

    SwipeRefreshLayout swipeRefreshLayout_global;

    String str_countryName, str_tests_global, str_country_global, str_active_mil_global, str_recovered_mil_global, str_critical_mil_global,
            str_two_population_global, str_active_global, str_recovered_global, str_death_global, str_deaths_mil_global, str_cases_mil_global, str_tests_mil_global;

    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;
    PieChart pieChart_global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_world);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.global);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.global:
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
        Init();
        FetchWorldData();
        swipeRefreshLayout_global.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchWorldData();
                swipeRefreshLayout_global.setRefreshing(false);
            }
        });
    }

    private void FetchWorldData() {
        ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://corona.lmao.ninja/v2/all";
        pieChart_global.clearChart();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Fetching data from API and storing into string
                        try {
                            str_tests_global = response.getString("tests");
                            str_countryName = response.getString("tests");
                            str_active_global = response.getString("active");
                            str_recovered_global = response.getString("recovered");
                            str_death_global = response.getString("deaths");
                            str_country_global = response.getString("affectedCountries");
                            str_active_mil_global = response.getString("activePerOneMillion");
                            str_deaths_mil_global = response.getString("deathsPerOneMillion");
                            str_recovered_mil_global = response.getString("recoveredPerOneMillion");
                            str_two_population_global = response.getString("population");
                            str_critical_mil_global = response.getString("criticalPerOneMillion");
                            str_cases_mil_global = response.getString("casesPerOneMillion");
                            str_tests_mil_global = response.getString("testsPerOneMillion");


                            android.os.Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // setting up texted in the text view

                                    tv_tests_global.setText(NumberFormat.getInstance().format(Integer.parseInt(str_tests_global)));
                                    tv_country_global.setText(NumberFormat.getInstance().format(Integer.parseInt(str_country_global)));
                                    active_mil_global.setText(str_active_mil_global);
                                    deaths_mil_global.setText(str_deaths_mil_global);
                                    recovered_mil_global.setText(str_recovered_mil_global);
                                    critical_mil_global.setText(str_critical_mil_global);
                                    cases_mil_global.setText(str_cases_mil_global);
                                    tests_mil_global.setText(str_tests_mil_global);
                                    population_global.setText(NumberFormat.getInstance().format(Double.parseDouble(str_two_population_global)));


                                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                                    // textView is the TextView view that should display it
                                    tv_time_global.setText(currentDateTimeString);

                                    pieChart_global.addPieSlice(new PieModel("Active", Integer.parseInt(str_active_global),
                                            Color.parseColor("#fccb88")));
                                    pieChart_global.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered_global),
                                            Color.parseColor("#42f58d")));
                                    pieChart_global.addPieSlice(new PieModel("Deceased",
                                            Integer.parseInt(str_death_global), Color.parseColor("#ff748f")));

                                    pieChart_global.startAnimation();

                                    DismissDialog();

                                }

                                public void ShowDialog(Context context) {
                                    //setting up progress dialog
                                    progressDialog = new ProgressDialog(context);
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_dialog);
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                public void DismissDialog() {
                                    progressDialog.dismiss();
                                    Toast.makeText(WorldActivity.this, "اطلاعات  دریافت  شد", Toast.LENGTH_SHORT).show();
                                }

                            }, 4200);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    public void ShowDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void Init() {
        tv_tests_global = findViewById(R.id.activity_world_data_tests_textView_global);
        swipeRefreshLayout_global = findViewById(R.id.activity_world_data_swipe_refresh_layout_global);
        pieChart_global = findViewById(R.id.global_world_data_piechart);
        tv_time_global = findViewById(R.id.activity_main_time_textview_global);
        tv_country_global = findViewById(R.id.country_textview_global);
        active_mil_global = findViewById(R.id.activePerOneMillion_global);
        recovered_mil_global = findViewById(R.id.recoveredPerOneMillion_global);
        deaths_mil_global = findViewById(R.id.DeathsPerOneMillion_global);
        population_global = findViewById(R.id.population_tv_global);
        critical_mil_global = findViewById(R.id.criticalPerOneMillion_global);
        cases_mil_global = findViewById(R.id.casesPerOneMillion_global);
        tests_mil_global = findViewById(R.id.testsPerOneMillion_global);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            backPressToast.cancel();
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        backPressToast = Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT);
        backPressToast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}

