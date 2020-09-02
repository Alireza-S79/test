package com.example.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.covid19tracker.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import java.text.NumberFormat;
import java.util.Objects;
import static com.example.Constants.COUNTRY_ACTIVE;
import static com.example.Constants.COUNTRY_CONFIRMED;
import static com.example.Constants.COUNTRY_CONTINENT;
import static com.example.Constants.COUNTRY_CRITICAL;
import static com.example.Constants.COUNTRY_DECEASED;
import static com.example.Constants.COUNTRY_FLAGURL;
import static com.example.Constants.COUNTRY_NAME;
import static com.example.Constants.COUNTRY_NEW_CONFIRMED;
import static com.example.Constants.COUNTRY_NEW_DECEASED;
import static com.example.Constants.COUNTRY_NEW_RECOVERED;
import static com.example.Constants.COUNTRY_POPULATION;
import static com.example.Constants.COUNTRY_RECOVERED;

public class EachCountryDataActivity extends AppCompatActivity {

    private TextView tv_confirmed_c, tv_confirmed_new_c, tv_active_c, tv_death_c, tv_death_new_c,
            tv_recovered_c, tv_recovered_new_c, tv_population_c, tv_critical_c, tv_continent;

    private String str_confirmed_c, str_confirmed_new_c, str_active_c, str_death_c, str_death_new_c,
            str_recovered_c, str_recovered_new_c, str_population_c, flag, str_countryName_c, str_critical_c, str_continent;
    private PieChart pieChart_c;
    private WorldDataActivity activity = new WorldDataActivity();

    private ImageView flag_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_each_country_data);
        GetIntent();

        Objects.requireNonNull(getSupportActionBar()).setTitle(str_countryName_c);

        //back menu icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Init();
        LoadCountryData();
    }

    private void Init() {
        tv_confirmed_c = findViewById(R.id.activity_each_country_data_confirmed_textView);
        tv_confirmed_new_c = findViewById(R.id.activity_each_country_data_confirmed_new_textView);
        tv_active_c = findViewById(R.id.activity_each_country_data_active_textView);
        tv_critical_c = findViewById(R.id.activity_c_data_critical_textView);
        tv_recovered_c = findViewById(R.id.activity_each_country_data_recovered_textView);
        tv_recovered_new_c = findViewById(R.id.activity_each_country_data_recovered_new_textView);
        tv_death_c = findViewById(R.id.activity_each_country_data_death_textView);
        tv_death_new_c = findViewById(R.id.activity_each_country_data_death_new_textView);
        // tv_tests = findViewById(R.id.activity_each_country_data_tests_textView);
        tv_population_c = findViewById(R.id.population);
        pieChart_c = findViewById(R.id.activity_each_country_data_piechart);
        flag_image = findViewById(R.id.layout_country_wise_flag_imageview_two);
        tv_continent = findViewById(R.id.tv_continent);
    }

    private void LoadCountryData() {
        activity.ShowDialog(this);
        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed_c.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_c)));
                tv_confirmed_new_c.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new_c)));
                tv_active_c.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active_c)));
                tv_death_c.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death_c)));
                tv_death_new_c.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(str_death_new_c)));
                tv_recovered_c.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered_c)));
                tv_recovered_new_c.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new_c)));
                tv_population_c.setText(NumberFormat.getInstance().format(Integer.parseInt(str_population_c)));
                tv_critical_c.setText(NumberFormat.getInstance().format(Integer.parseInt(str_critical_c)));
                tv_continent.setText(str_continent);
                Glide.with(getApplicationContext()).load(flag).into(flag_image);

                //setting piechart
                pieChart_c.addPieSlice(new PieModel("Active", Integer.parseInt(str_active_c), Color.parseColor("#fccb88")));
                pieChart_c.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered_c), Color.parseColor("#42f58d")));
                pieChart_c.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death_c), Color.parseColor("#ff748f")));
                pieChart_c.startAnimation();
                activity.DismissDialog();
                Toast.makeText(EachCountryDataActivity.this, "اطلاعات  دریافت  شد", Toast.LENGTH_SHORT).show();
            }
        }, 1000);

    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_countryName_c = intent.getStringExtra(COUNTRY_NAME);
        str_critical_c = intent.getStringExtra(COUNTRY_CRITICAL);
        str_active_c = intent.getStringExtra(COUNTRY_ACTIVE);
        str_death_c = intent.getStringExtra(COUNTRY_DECEASED);
        str_death_new_c = intent.getStringExtra(COUNTRY_NEW_DECEASED);
        str_recovered_c = intent.getStringExtra(COUNTRY_RECOVERED);
        str_recovered_new_c = intent.getStringExtra(COUNTRY_NEW_RECOVERED);
        str_confirmed_c = intent.getStringExtra(COUNTRY_CONFIRMED);
        str_confirmed_new_c = intent.getStringExtra(COUNTRY_NEW_CONFIRMED);
        str_population_c = intent.getStringExtra(COUNTRY_POPULATION);
        str_continent = intent.getStringExtra(COUNTRY_CONTINENT);
        flag = intent.getStringExtra(COUNTRY_FLAGURL);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}