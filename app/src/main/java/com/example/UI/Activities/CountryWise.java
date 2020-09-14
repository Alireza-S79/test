package com.example.UI.Activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.Adapter.CountryWiseAdapter;
import com.example.Models.CountyWiseModel;
import com.example.covid19tracker.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CountryWise extends AppCompatActivity {

    private CountryWiseAdapter countryWiseAdapter;
    private ArrayList<CountyWiseModel> countryWiseModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_search;

    private String str_country, str_confirmed, str_confirmed_new, str_active, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests, str_population, str_continent;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_country_wise_data);


        Init();
        FetchCountryWiseData();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            FetchCountryWiseData();
            swipeRefreshLayout.setRefreshing(false);
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

    private void FetchCountryWiseData() {
        //Show progress dialog
      ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://corona.lmao.ninja/v2/countries";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiURL,
                null,
                response -> {
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
                            CountyWiseModel countryWiseModel = new CountyWiseModel(str_country, str_confirmed, str_confirmed_new, str_active,
                                    str_death, str_death_new, str_recovered, str_recovered_new, str_tests, str_population, str_continent, flagUrl);
                            countryWiseModelArrayList.add(countryWiseModel);
                        }

                        Collections.sort(countryWiseModelArrayList, (o1, o2) -> {
                            if (Integer.parseInt(o1.getConfirmed()) > Integer.parseInt(o2.getConfirmed())) {
                                return -1;
                            } else {
                                return 1;
                            }
                        });

                        Handler makeDelay = new Handler();
                        makeDelay.postDelayed(() -> {
                            countryWiseAdapter.notifyDataSetChanged();
                           DismissDialog();
                            Toast.makeText(CountryWise.this, "اطلاعات  دریافت  شد", Toast.LENGTH_SHORT).show();
                        }, 1000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void ShowDialog(Context context) {
        //setting up progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void DismissDialog() {
        progressDialog.dismiss();
    }

    private void Init() {
        swipeRefreshLayout = findViewById(R.id.activity_country_wise_swipe_refresh_layout);
        et_search = findViewById(R.id.activity_country_wise_search_editText);

        RecyclerView rv_country_wise = findViewById(R.id.activity_country_wise_recyclerview);

        rv_country_wise.setHasFixedSize(true);
        rv_country_wise.setLayoutManager(new LinearLayoutManager(this));

        countryWiseModelArrayList = new ArrayList<>();
        countryWiseAdapter = new CountryWiseAdapter(CountryWise.this, countryWiseModelArrayList);
        rv_country_wise.setAdapter(countryWiseAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}