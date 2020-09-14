package com.example.UI.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.covid19tracker.R;

public class AboutUs extends Fragment implements View.OnClickListener {

    public AboutUs() {}
    RelativeLayout insta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_blank, container, false);
        insta = myView.findViewById(R.id.insta);
        insta.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.insta) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/_iamvoldemort")));
        }
    }
}