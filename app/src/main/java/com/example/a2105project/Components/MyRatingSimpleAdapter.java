package com.example.a2105project.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a2105project.Entity.Rating;
import com.example.a2105project.R;

import java.util.List;

public class MyRatingSimpleAdapter extends BaseAdapter {

    List<Rating> Item;
    Context context;
    public MyRatingSimpleAdapter(Context context, List<Rating> list){
        Item = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.client_rate_list, viewGroup, false);

        TextView clientRateContent = (TextView)v.findViewById(R.id.clientRateContent);
        TextView RateClientEmail = (TextView) v.findViewById(R.id.RateClientEmail);
        TextView rateTime = (TextView)v.findViewById(R.id.rateTime);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

        RateClientEmail.setText(Item.get(i).getClientEmail());
        ratingBar.setRating(Item.get(i).getStar());
        clientRateContent.setText(Item.get(i).getContent());
        rateTime.setText(Item.get(i).getTime());

        return v;
    }
}
