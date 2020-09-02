package com.example.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.Adapter.CountryWiseAdapter;
import com.example.Models.CountyWiseModel;
import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CountryWiseDataActivity extends AppCompatActivity {

    private RecyclerView rv_country_wise;
    private CountryWiseAdapter countryWiseAdapter;
    private ArrayList<CountyWiseModel> countryWiseModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_search;

    private String str_country, str_confirmed, str_confirmed_new, str_active, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests, str_population,str_continent;

    private HomeActivity activity = new HomeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_country_wise_data);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);

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
                        return true;
                }
                return false;
            }
        });

        Init();
        FetchCountryWiseData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchCountryWiseData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });
    }

    private void Filter(String text) {
        ArrayList<CountyWiseModel> filteredList = new ArrayList<>();
        for (CountyWiseModel item : countryWiseModelArrayList) {
            if (item.getCountry().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        countryWiseAdapter.filterList(filteredList, text);
    }


    private boolean FetchCountryWiseData() {
        //Show progress dialog
        activity.ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://corona.lmao.ninja/v2/countries";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            countryWiseModelArrayList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject countryJSONObject = response.getJSONObject(i);

                                str_country = countryJSONObject.getString("country");
                                str_confirmed = countryJSONObject.getString("cases");
                                str_confirmed_new = countryJSONObject.getString("todayCases");
                                str_active = countryJSONObject.getString("active");
                                str_recovered = countryJSONObject.getString("recovered");
                                str_recovered_new = countryJSONObject.getString("todayRecovered");
                                str_death = countryJSONObject.getString("deaths");
                                str_death_new = countryJSONObject.getString("todayDeaths");
                                str_tests = countryJSONObject.getString("tests");
                                str_population = countryJSONObject.getString("population");
                                str_continent = countryJSONObject.getString("continent");

                                JSONObject flagObject = countryJSONObject.getJSONObject("countryInfo");
                                String flagUrl = flagObject.getString("flag");

                                //Creating an object of our country model class and passing the values in the constructor
                                CountyWiseModel countryWiseModel = new CountyWiseModel(str_country, str_confirmed, str_confirmed_new, str_active,
                                        str_death, str_death_new, str_recovered, str_recovered_new, str_tests, str_population,str_continent, flagUrl);
                                //adding data to our arraylist
                                countryWiseModelArrayList.add(countryWiseModel);
                            }

                            Collections.sort(countryWiseModelArrayList, new Comparator<CountyWiseModel>() {
                                @Override
                                public int compare(CountyWiseModel o1, CountyWiseModel o2) {
                                    if (Integer.parseInt(o1.getConfirmed()) > Integer.parseInt(o2.getConfirmed())) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                            });
                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    countryWiseAdapter.notifyDataSetChanged();
                                    activity.DismissDialog();
                                    Toast.makeText(CountryWiseDataActivity.this, "اطلاعات  دریافت  شد", Toast.LENGTH_SHORT).show();
                                }
                            }, 1000);
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
        requestQueue.add(jsonArrayRequest);
        return false;
    }

    private void Init() {
        swipeRefreshLayout = findViewById(R.id.activity_country_wise_swipe_refresh_layout);
        et_search = findViewById(R.id.activity_country_wise_search_editText);
        rv_country_wise = findViewById(R.id.activity_country_wise_recyclerview);
        rv_country_wise.setHasFixedSize(true);
        rv_country_wise.setLayoutManager(new LinearLayoutManager(this));
        countryWiseModelArrayList = new ArrayList<>();
        countryWiseAdapter = new CountryWiseAdapter(CountryWiseDataActivity.this, countryWiseModelArrayList);
        rv_country_wise.setAdapter(countryWiseAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
