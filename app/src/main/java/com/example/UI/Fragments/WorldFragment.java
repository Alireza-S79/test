package com.example.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.Adapter.WorldAdapter;
import com.example.Models.Country;
import com.example.Utils.NetworkUtils;
import com.example.ViewModels.WorldViewModel;
import com.example.covid19tracker.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldFragment extends Fragment {

    public WorldFragment() {}

    private WorldViewModel viewModel;
    private RecyclerView recyclerView;
    public WorldAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_world, container, false);
        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.refresh);

        FrameLayout progressBar = rootView.findViewById(R.id.progressBar);

        FragmentActivity activity = getActivity();

        recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new WorldAdapter();
        recyclerView.setSaveEnabled(true);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(WorldViewModel.class);

        viewModel.getAllCases();
        viewModel.getAllCountries();

        viewModel.getAllCountries().observe(requireActivity(), (Observer<List<Country>>) countries -> {
            adapter.setCountriesList((ArrayList<Country>) countries);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

           Collections.sort(countries, (o1, o2) -> {
               if (Float.parseFloat(o1.getCases()) > Float.parseFloat(o2.getCases())) {
                   return -1;
               } else {
                   return 1;
               }
           });
        });

        viewModel.getIsFirstLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                refreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getIsLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                if (viewModel.getIsFirstLoading().getValue() != null &&
                        !viewModel.getIsFirstLoading().getValue()) {
                    refreshLayout.setRefreshing(true);
                }
            } else {
                refreshLayout.setRefreshing(false);
            }
        });


        viewModel.getIsError().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                refreshLayout.setRefreshing(false);
                if (NetworkUtils.getConnectivityStatus(
                        activity) != 0) {

                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtils.getConnectivityStatus(
                    activity.getApplicationContext()) != 0) {
                viewModel.refresh();
            } else {
                Toast.makeText(WorldFragment.this.requireActivity().getApplicationContext(),
                        R.string.update_no_connection, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }
}