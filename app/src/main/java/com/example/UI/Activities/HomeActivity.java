package com.example.UI.Activities;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    TextView tv_confirmed, tv_confirmed_new, tv_active,
            tv_recovered, tv_recovered_new, tv_death, tv_death_new, tv_critical;

    SwipeRefreshLayout swipeRefreshLayout;

    String str_confirmed, str_confirmed_new, str_active, str_recovered, str_recovered_new,
            str_death, str_death_new, str_critical;

    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.global:
                        startActivity(new Intent(getApplicationContext(), WorldActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.egypt:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchWorldData();
                swipeRefreshLayout.setRefreshing(false);
                //Toast.makeText(WorldActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean FetchWorldData() {
        ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://corona.lmao.ninja/v2/all";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Fetching data from API and storing into string
                        try {
                            str_confirmed = response.getString("cases");
                            str_confirmed_new = response.getString("todayCases");
                            str_active = response.getString("active");
                            str_recovered = response.getString("recovered");
                            str_recovered_new = response.getString("todayRecovered");
                            str_death = response.getString("deaths");
                            str_death_new = response.getString("todayDeaths");
                            str_critical = response.getString("critical");
                            android.os.Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ValueAnimator valueAnimatorone = ValueAnimator.ofInt(100, Integer.parseInt(str_recovered_new));
                                    valueAnimatorone.setDuration(1000);
                                    valueAnimatorone.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            tv_recovered_new.setText("+" + valueAnimator.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimatorone.start();
                                    ValueAnimator valueAnimatortwo = ValueAnimator.ofInt(100, Integer.parseInt(str_confirmed_new));
                                    valueAnimatortwo.setDuration(1000);
                                    valueAnimatortwo.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            tv_confirmed_new.setText("+" + valueAnimator.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimatortwo.start();
                                    ValueAnimator valueAnimatorthree = ValueAnimator.ofInt(100, Integer.parseInt(str_death_new));
                                    valueAnimatorthree.setDuration(1000);
                                    valueAnimatorthree.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            tv_death_new.setText("+" + valueAnimator.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimatorthree.start();
                                    tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                                    tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                                    tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                                    tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                                    tv_critical.setText(NumberFormat.getInstance().format(Integer.parseInt(str_critical)));
                                    DismissDialog();
                                }
                            }, 2000);
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
        return false;
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
    }

    private void Init() {
        tv_confirmed = findViewById(R.id.activity_world_data_confirmed_textView);
        tv_confirmed_new = findViewById(R.id.activity_world_data_confirmed_new_textView);
        tv_active = findViewById(R.id.activity_world_data_active_textView);
        tv_critical = findViewById(R.id.activity_world_data_critical_textView);
        tv_recovered = findViewById(R.id.activity_world_data_recovered_textView);
        tv_recovered_new = findViewById(R.id.activity_world_data_recovered_new_textView);
        tv_death = findViewById(R.id.activity_world_data_death_textView);
        tv_death_new = findViewById(R.id.activity_world_data_death_new_textView);
        swipeRefreshLayout = findViewById(R.id.activity_world_data_swipe_refresh_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            //Toast.makeText(WorldActivity.this, "About menu icon clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
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

    private boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Objects.requireNonNull(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            Toast.makeText(this, "لظفا اینترنت خود را بررسی کنید", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}