package com.example.UI.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Utils.NetworkUtils;
import com.example.ViewModels.FinalViewModel;
import com.example.covid19tracker.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.Date;

public class Global extends Fragment {

    public Global() {
    }

    private FinalViewModel viewModel;
    private PieChart pieChart_d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.global, container, false);

        pieChart_d = rootView.findViewById(R.id.piechart);
        //PieChart pieChart_global = rootView.findViewById(R.id.piechart);
        TextView deaths_mil_global = rootView.findViewById(R.id.DeathsPerOneMillion_global);
        TextView critical_mil_global = rootView.findViewById(R.id.criticalPerOneMillion_global);
        TextView active_mil_global = rootView.findViewById(R.id.activePerOneMillion_global);
        TextView recovered_mil_global = rootView.findViewById(R.id.recoveredPerOneMillion_global);
        TextView cases_mil_global = rootView.findViewById(R.id.casesPerOneMillion_global);
        TextView tests_mil_global = rootView.findViewById(R.id.testsPerOneMillion_global);
        TextView tv_country_global = rootView.findViewById(R.id.country_textview_global);
        TextView tv_tests_global = rootView.findViewById(R.id.activity_world_data_tests_textView_global);
        TextView tv_time_global = rootView.findViewById(R.id.activity_main_time_textview_global);
        TextView population_global = rootView.findViewById(R.id.population_tv_global);
        SwipeRefreshLayout refresh = rootView.findViewById(R.id.refresh_me);

        FragmentActivity activity = getActivity();


        viewModel = new ViewModelProvider(requireActivity()).get(FinalViewModel.class);
        viewModel.getFinalCountry();

        viewModel.getFinalCountry().observe(requireActivity(), finalCountry -> {

            deaths_mil_global.setText(String.valueOf(finalCountry.getDeathsPerOneMillion()));
            critical_mil_global.setText(String.valueOf(finalCountry.getCriticalPerOneMillion()));
            active_mil_global.setText(String.valueOf(finalCountry.getActivePerOneMillion()));
            recovered_mil_global.setText(String.valueOf(finalCountry.getRecoveredPerOneMillion()));
            cases_mil_global.setText(String.valueOf(finalCountry.getCasesPerOneMillion()));
            tests_mil_global.setText(String.valueOf(finalCountry.getTestsPerOneMillion()));
            tv_country_global.setText(String.valueOf(finalCountry.getAffectedCountries()));
            tv_tests_global.setText(NumberFormat.getInstance().format(Float.parseFloat(finalCountry.getTests())));
            population_global.setText(NumberFormat.getInstance().format(Float.parseFloat(finalCountry.getPopulation())));

            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            tv_time_global.setText(currentDateTimeString);

            pieChart_d.clearChart();

            pieChart_d.addPieSlice(new PieModel((float) finalCountry.getActive(), Color.parseColor("#fccb88")));
            pieChart_d.addPieSlice(new PieModel((float) finalCountry.getRecovered(), Color.parseColor("#42f58d")));
            pieChart_d.addPieSlice(new PieModel((float) finalCountry.getDeaths(), Color.parseColor("#ff748f")));
            pieChart_d.startAnimation();

        });

        viewModel.getIsLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                refresh.setRefreshing(true);
            } else {
                refresh.setRefreshing(false);
            }
        });

        viewModel.getIsError().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                refresh.setRefreshing(false);
                deaths_mil_global.setText("-");
                critical_mil_global.setText("-");
                active_mil_global.setText("-");
                recovered_mil_global.setText("-");
                cases_mil_global.setText("-");
                tests_mil_global.setText("-");
                tv_country_global.setText("-");
                tv_tests_global.setText("-");
                tv_time_global.setText("-");
                population_global.setText("-");

                if (NetworkUtils.getConnectivityStatus(
                        activity) != 0) {

                    Toast.makeText(activity.getApplicationContext(),
                            R.string.wrong, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            R.string.network_connection, Toast.LENGTH_SHORT).show();
                }
                viewModel.reSet();
            }
        });

        refresh.setOnRefreshListener(() -> {
            if (NetworkUtils.getConnectivityStatus(
                    activity.getApplicationContext()) != 0) {
                viewModel.refresh();

            } else {
                Toast.makeText(Global.this.requireActivity().getApplicationContext(),
                        R.string.update_no_connection, Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
                viewModel.reSet();
            }
        });
        return rootView;
    }
}