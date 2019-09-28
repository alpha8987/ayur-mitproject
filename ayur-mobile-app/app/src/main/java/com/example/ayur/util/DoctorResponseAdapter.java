package com.example.ayur.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ayur.domain.DoctorResponse;

import java.util.List;

public class DoctorResponseAdapter extends ArrayAdapter<DoctorResponse> {
    List<DoctorResponse> values;

    public DoctorResponseAdapter(Context context, int resource, List<DoctorResponse> objects) {
        super(context, resource, objects);
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(String.format("%s %s", this.values.get(position).getFirstNames(), this.values.get(position).getLastName()));
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(String.format("%s %s", this.values.get(position).getFirstNames(), this.values.get(position).getLastName()));
        return label;
    }
}