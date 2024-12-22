package com.example.itcompanies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ServiceAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] services;

    public ServiceAdapter(Context context, ArrayList<String> services) {
        super(context, R.layout.service_item, services);
        this.context = context;
        this.services = services.toArray(new String[0]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.service_item, parent, false);
        }

        TextView serviceTextView = convertView.findViewById(R.id.service_text);
        serviceTextView.setText(services[position]);

        return convertView;
    }
}
