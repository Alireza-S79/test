package com.example.Adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.Models.Country;
import com.example.covid19tracker.R;

import java.util.ArrayList;

public class WorldAdapter extends RecyclerView.Adapter<WorldAdapter.WorldViewHolder> {

    //  List<Country> CountrysList;
    private String searchText = "";
    private SpannableStringBuilder sb;
    private Context mContext;
    private ArrayList<Country> CountrysList;
    private ArrayList<Country> countryWiseModelArrayList;

    public WorldAdapter() {
    }

    public void CountryWiseAdapter(Context mContext, ArrayList<Country> CountrysList) {
        this.mContext = mContext;
        this.CountrysList = CountrysList;
    }

    public void clearAll() {
        if (CountrysList != null) {
            this.CountrysList.clear();
            notifyDataSetChanged();
        }
    }

    public void setCountriesList(ArrayList<Country> CountriesList) {
        if (this.CountrysList == null) {
            this.CountrysList = CountriesList;
            notifyDataSetChanged();
        } else {
            int lastFinish = this.CountrysList.size() - 1;
            this.CountrysList.addAll(CountriesList);
            notifyItemInserted(lastFinish);
            //notifyItemRangeInserted(lastFinish, newFinish);
        }
    }

    public void filterList(ArrayList<Country> filteredList, String text) {
        CountrysList = filteredList;
        this.searchText = text;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country, parent, false);
        return new WorldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldViewHolder holder, int position) {
        holder.titleChip.setText(CountrysList.get(position).getCountry());
        holder.totalCases.setText(String.valueOf(CountrysList.get(position).getCases()));
        holder.todayCases.setText(String.valueOf(CountrysList.get(position).getTodayCases()));
        holder.todayDeaths.setText(String.valueOf(CountrysList.get(position).getTodayDeaths()));
        holder.totalDeaths.setText(String.valueOf(CountrysList.get(position).getDeaths()));
        holder.recovered.setText(String.valueOf(CountrysList.get(position).getRecovered()));

        Country currentItem = CountrysList.get(position);
        String countryTotal = currentItem.getCases();
        String countryRank = String.valueOf(position + 1);
        String countryName = currentItem.getCountry();


        int countryTotalInt = Integer.parseInt(countryTotal);
        //String countryTotalInt = String.valueOf(countryTotal);

        Log.d("country rank", countryRank);
        holder.rank.setText(countryRank + ".");
        Log.d("country total cases int", String.valueOf(countryTotalInt));

    }

    @Override
    public int getItemCount() {
        return (CountrysList == null) ? 0 : CountrysList.size();
    }

    public class WorldViewHolder extends RecyclerView.ViewHolder {

        TextView titleChip;
        TextView totalCases;
        TextView todayCases;
        TextView todayDeaths;
        TextView totalDeaths;
        TextView recovered;
        TextView rank;
        LinearLayout lin_country;
        ImageView iv_flagImage_ir;

        public WorldViewHolder(@NonNull View itemView) {
            super(itemView);

            titleChip = itemView.findViewById(R.id.title_chip);
            totalCases = itemView.findViewById(R.id.total_cases_world);
            todayCases = itemView.findViewById(R.id.today_cases_world);
            todayDeaths = itemView.findViewById(R.id.today_deaths_world);
            totalDeaths = itemView.findViewById(R.id.total_deaths_world);
            recovered = itemView.findViewById(R.id.recovered_world);
            rank = itemView.findViewById(R.id.ranktwo);
            lin_country = itemView.findViewById(R.id.lin_country);
            iv_flagImage_ir = itemView.findViewById(R.id.layout_country_wise_flag_imageview_two_ir);
        }
    }
}