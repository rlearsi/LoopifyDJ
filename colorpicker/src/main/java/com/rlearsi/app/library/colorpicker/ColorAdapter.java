package com.rlearsi.app.library.colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;

public class ColorAdapter extends BaseAdapter {
    private Context context;
    private String[] colors;

    public ColorAdapter(Context context, String[] colors) {
        this.context = context;
        this.colors = colors;

        Log.i("xdjx", "cores: " + Arrays.toString(colors));
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.color_list, parent, false);
        }

        View colorView = view.findViewById(R.id.grid);
        //colorView.setBackgroundColor(colors[position]);
        colorView.setBackgroundColor(Color.parseColor(colors[position]));

        return view;
    }
}

