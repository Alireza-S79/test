package com.example.UI.Fragments;

import android.animation.ValueAnimator;
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
import com.example.ViewModels.HomeViewModel;
import com.example.covid19tracker.R;
import java.text.NumberFormat;

public class Home extends Fragment {

    public Home() {}

    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home, container, false);

        TextView tv_confirmed = rootView.findViewById(R.id.activity_world_data_confirmed_textView);
        TextView tv_confirmed_new = rootView.findViewById(R.id.activity_world_data_confirmed_new_textView);
        TextView tv_active = rootView.findViewById(R.id.activity_world_data_active_textView);
        TextView tv_critical = rootView.findViewById(R.id.activity_world_data_critical_textView);
        TextView tv_recovered = rootView.findViewById(R.id.activity_world_data_recovered_textView);
        TextView tv_recovered_new = rootView.findViewById(R.id.activity_world_data_recovered_new_textView);
        TextView tv_death = rootView.findViewById(R.id.activity_world_data_death_textView);
        TextView tv_death_new = rootView.findViewById(R.id.activity_world_data_death_new_textView);
        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.activity_world_data_swipe_refresh_layout);

        FragmentActivity activity = getActivity();


        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.getAllCases();

        viewModel.getAllCases().observe(requireActivity(), allCases -> {

            tv_critical.setText(String.valueOf(allCases.getCritical()));
            tv_active.setText(NumberFormat.getInstance().format(Float.parseFloat(allCases.getActive())));
            tv_confirmed.setText(NumberFormat.getInstance().format(Float.parseFloat(allCases.getCases())));
            tv_recovered.setText(NumberFormat.getInstance().format(Float.parseFloat(allCases.getRecovered())));
            tv_death.setText(NumberFormat.getInstance().format(Float.parseFloat(allCases.getDeaths())));

            ValueAnimator valueAnimator = ValueAnimator.ofInt(100, Integer.parseInt(allCases.getTodayDeaths()));
            valueAnimator.setDuration(400);
            valueAnimator.addUpdateListener(valueAnimator1 -> tv_death_new.setText("+" + valueAnimator1.getAnimatedValue().toString()));
            valueAnimator.start();


            ValueAnimator valueAnimatortwo = ValueAnimator.ofInt(100, Integer.parseInt(allCases.getTodayRecovered()));
            valueAnimatortwo.setDuration(400);
            valueAnimatortwo.addUpdateListener(valueAnimator12 -> tv_recovered_new.setText("+" + valueAnimator12.getAnimatedValue().toString()));
            valueAnimatortwo.start();

            ValueAnimator valueAnimatorthree = ValueAnimator.ofInt(100, Integer.parseInt(allCases.getTodayCases()));
            valueAnimatorthree.setDuration(400);
            valueAnimatorthree.addUpdateListener(valueAnimator13 -> tv_confirmed_new.setText("+" + valueAnimator13.getAnimatedValue().toString()));
            valueAnimatorthree.start();


        });

        viewModel.getIsLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        viewModel.getIsError().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                swipeRefreshLayout.setRefreshing(false);

                tv_active.setText("-");
                tv_critical.setText("-");
                tv_death.setText("-");
                tv_death_new.setText("-");
                tv_recovered.setText("-");
                tv_recovered_new.setText("-");
                tv_confirmed.setText("-");
                tv_confirmed_new.setText("-");

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

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtils.getConnectivityStatus(
                    activity.getApplicationContext()) != 0) {
                viewModel.refresh();
            } else {
                Toast.makeText(activity.getApplicationContext(),
                        R.string.network_connection, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                viewModel.reSet();
            }
        });
        return rootView;
    }
}