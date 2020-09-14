package com.example.UI.Fragments;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.Models.Country;
import com.example.Utils.NetworkUtils;
import com.example.ViewModels.IRViewModel;
import com.example.covid19tracker.R;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import java.text.NumberFormat;

public class IR extends Fragment {

    public IR() {}

    private IRViewModel viewModel;

    private TextView critical, totalDeaths, todayDeaths, recovered, totalCases, todayCases, population,
            oneCasePerPeople, OneDeathPerPeople, oneTestPerPeople, tests, todayRecovered, tests_mil;

    private PieChart pieChart_ir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ir, container, false);

        pieChart_ir = rootView.findViewById(R.id.piechart_ir);

        oneCasePerPeople = rootView.findViewById(R.id.tv_oneCasePerPeople);
        OneDeathPerPeople = rootView.findViewById(R.id.OneDeathPerPeople);
        oneTestPerPeople = rootView.findViewById(R.id.oneTestPerPeople);

        population = rootView.findViewById(R.id.population_ir);

        tests = rootView.findViewById(R.id.tests_num_ir);
        tests_mil = rootView.findViewById(R.id.tests_mil_num_ir);

        todayDeaths = rootView.findViewById(R.id.today_deaths_num_ir);
        todayRecovered = rootView.findViewById(R.id.today_recovered_num_ir);
        todayCases = rootView.findViewById(R.id.today_cases_num_ir);

        totalCases = rootView.findViewById(R.id.total_cases_num_ir);
        totalDeaths = rootView.findViewById(R.id.total_deaths_num_ir);
        recovered = rootView.findViewById(R.id.recovered_num_ir);
        critical = rootView.findViewById(R.id.critical_num_ir);

        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.swipe_refresh);

        viewModel = new ViewModelProvider(requireActivity()).get(IRViewModel.class);
        viewModel.getIran();

        FragmentActivity activity = getActivity();

        viewModel.getIran().observe(requireActivity(), new Observer<Country>() {
            @Override
            public void onChanged(Country country) {

                oneCasePerPeople.setText(String.valueOf(country.getOneCasePerPeople()));
                OneDeathPerPeople.setText(String.valueOf(country.getOneDeathPerPeople()));
                oneTestPerPeople.setText(String.valueOf(country.getOneTestPerPeople()));

                population.setText(NumberFormat.getInstance().format(Float.parseFloat(country.getPopulation())));

                tests.setText(NumberFormat.getInstance().format(Float.parseFloat(country.getTests())));
                tests_mil.setText(String.valueOf(country.getTestsPerOneMillion()));

                totalCases.setText(NumberFormat.getInstance().format(Float.parseFloat(country.getCases())));
                totalDeaths.setText(NumberFormat.getInstance().format(Float.parseFloat(country.getDeaths())));
                recovered.setText(NumberFormat.getInstance().format(Float.parseFloat(country.getRecovered())));
                critical.setText(NumberFormat.getInstance().format(Float.parseFloat(country.getCritical())));

                ValueAnimator valueAnimator = ValueAnimator.ofInt(100, Integer.parseInt(country.getTodayDeaths()));
                valueAnimator.setDuration(400);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator1) {
                        todayDeaths.setText("+" + valueAnimator1.getAnimatedValue().toString());
                    }
                });
                valueAnimator.start();

                ValueAnimator valueAnimatortwo = ValueAnimator.ofInt(100, Integer.parseInt(country.getTodayRecovered()));
                valueAnimatortwo.setDuration(400);
                valueAnimatortwo.addUpdateListener(valueAnimator12 -> todayRecovered.setText("+" + valueAnimator12.getAnimatedValue().toString()));
                valueAnimatortwo.start();


                ValueAnimator valueAnimatorthree = ValueAnimator.ofInt(100, Integer.parseInt(country.getTodayCases()));
                valueAnimatorthree.setDuration(400);
                valueAnimatorthree.addUpdateListener(valueAnimator13 -> todayCases.setText("+" + valueAnimator13.getAnimatedValue().toString()));
                valueAnimatorthree.start();


                pieChart_ir.clearChart();

                pieChart_ir.addPieSlice(new PieModel(Integer.parseInt(country.getActive()), Color.parseColor("#fccb88")));
                pieChart_ir.addPieSlice(new PieModel(Integer.parseInt(country.getRecovered()), Color.parseColor("#42f58d")));
                pieChart_ir.addPieSlice(new PieModel(Integer.parseInt(country.getDeaths()), Color.parseColor("#ff748f")));
                pieChart_ir.startAnimation();

            }
        });

        viewModel.getIsLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                refreshLayout.setRefreshing(true);
            } else {
                refreshLayout.setRefreshing(false);
            }
        });

        viewModel.getIsError().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                refreshLayout.setRefreshing(false);

                oneCasePerPeople.setText("-");
                OneDeathPerPeople.setText("-");
                oneTestPerPeople.setText("-");

                population.setText("-");

                tests.setText("-");
                tests_mil.setText("-");

                todayCases.setText("-");
                todayDeaths.setText("-");
                todayRecovered.setText("-");

                totalCases.setText("-");
                totalDeaths.setText("-");
                recovered.setText("-");
                critical.setText("-");

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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.getConnectivityStatus(
                        activity.getApplicationContext()) != 0) {
                    viewModel.refresh();
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            R.string.network_connection, Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                    viewModel.reSet();
                }
            }
        });

        return rootView;
    }
}