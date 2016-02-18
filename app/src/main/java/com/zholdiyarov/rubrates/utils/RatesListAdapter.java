package com.zholdiyarov.rubrates.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zholdiyarov.rubrates.R;
import com.zholdiyarov.rubrates.objects.Rates;

import java.util.ArrayList;

/**
 * author: szholdiyarov
 * date:17/02/2016
 * Purpose: This is custom adapter to display rates.
 */
public class RatesListAdapter extends BaseAdapter {
    ArrayList<Rates> rates;
    Context context;
    private static LayoutInflater inflater = null;

    public RatesListAdapter(Context context, ArrayList<Rates> rates) {
        this.rates = rates;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return rates.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView textView_nominal, textView_charCode, textView_rate;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        View rowView = inflater.inflate(R.layout.item_rates_list, null);

        holder.textView_nominal = (TextView) rowView.findViewById(R.id.textView_nominal);
        holder.textView_charCode = (TextView) rowView.findViewById(R.id.textView_charCode);
        holder.textView_rate = (TextView) rowView.findViewById(R.id.textView_rate);

        holder.textView_charCode.setText(rates.get(position).getCharCode());
        holder.textView_nominal.setText(rates.get(position).getNominal());
        holder.textView_rate.setText(rates.get(position).getRate());

        return rowView;
    }

}
