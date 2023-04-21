package com.rlearsi.app.library.colorpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AlertDialog;

import java.util.Arrays;

public class ColorAdapter extends BaseAdapter {
    private Context context;
    private String[] colors;
    //private AlertDialog dialog;

    public ColorAdapter(Context context, String[] colors) {

        this.context = context;
        this.colors = colors;
        //this.dialog = dialog;

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

    private int getDrawableRadius(View view) {

        int size = Math.max(view.getWidth(), view.getHeight());
        return size / 2;

    }

    public Drawable getRoundedDrawable(int color, View view) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        drawable.setCornerRadius(getDrawableRadius(view));
        return drawable;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.color_list, parent, false);
        }

        View colorView = view.findViewById(R.id.grid);
        //colorView.setBackgroundColor(Color.parseColor(colors[position]));
        colorView.setBackground(getRoundedDrawable(Color.parseColor(colors[position]), view));
        //colorView.setClickable(false);

        /*colorView.setOnClickListener(v -> {

            //selectedPosition = position;
            colorView.setAlpha(0.7f);
            dialog.dismiss();
            notifyDataSetChanged();

        });*/

        return view;
    }
}

