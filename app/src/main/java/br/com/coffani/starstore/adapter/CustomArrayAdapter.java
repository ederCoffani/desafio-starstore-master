package br.com.coffani.starstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.domain.SpinnerObject;
import br.com.coffani.starstore.feature.payment.PaymentActivity;

/**
 * Created by Windows on 05/02/2018.
 */

public class CustomArrayAdapter extends ArrayAdapter<String> {

    public Resources res;
    SpinnerObject tempValues;
    LayoutInflater inflater;
    private Context activity;
    private ArrayList data;

    public CustomArrayAdapter(
            PaymentActivity activitySpinner,
            int textViewResourceId,
            ArrayList objects,
            Resources resLocal
    ) {
        super(activitySpinner, textViewResourceId, objects);

        activity = activitySpinner;
        data = objects;
        res = resLocal;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        tempValues = null;
        tempValues = (SpinnerObject) data.get(position);

        TextView txt = (TextView) row.findViewById(R.id.dropdown_txt);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_downarrow, 0);
        txt.setText(tempValues.getname());
        txt.setTextColor(Color.parseColor("#1171d0"));
        txt.setSingleLine(true);
        txt.setEllipsize(TextUtils.TruncateAt.END);
        txt.setSingleLine(true);

        return row;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        tempValues = null;
        tempValues = (SpinnerObject) data.get(position);

        TextView txt = (TextView) row.findViewById(R.id.dropdown_txt);
        txt.setText(tempValues.getname());
        txt.setTextSize(17);
        txt.setPadding(0, 30, 0, 30);
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(Color.parseColor("#1171d0"));
        txt.setBackgroundColor(Color.parseColor("#FFFFFF"));

        return row;
    }
}

