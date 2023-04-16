package com.rlearsi.app.library.colorpicker;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

public class ColorPicker {

    Context context;
    LayoutInflater factory;
    View view;
    AlertDialog dialog;
    private ColorPickerListener colorPickerListener;
    ColorPickerListener colorPickerInterface;
    private String[] colors;
    private String title;
    private boolean cancelable = true;
    private int closeButton = View.VISIBLE;
    private GridView gridView;
    private Button button;

    public ColorPicker (final Context context) {

        this.context = context;

    }

    public void openColorPicker() {

        factory = LayoutInflater.from(context);
        view = factory.inflate(R.layout.color_picker_layout, null);
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);

        gridView = view.findViewById(R.id.rv_colors);

        gridView.setNumColumns(4);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setGravity(Gravity.CENTER);
        gridView.setVerticalSpacing(16);
        gridView.setHorizontalSpacing(16);
        gridView.setFocusable(true);
        gridView.requestFocus();

        ColorAdapter colorAdapter = new ColorAdapter(context, colors);
        gridView.setAdapter(colorAdapter);

        gridView.setOnItemClickListener((parent, view2, position, id) -> {

            if (colorPickerListener != null) {

                colorPickerListener.onColorSelected(colors[position]);

            }

            dialog.dismiss();

        });

        dialog.setView(view);
        dialog.setCancelable(cancelable);

        dialog.show();

        ImageButton close = view.findViewById(R.id.close_button);
        close.setVisibility(closeButton);

        close.setOnClickListener(v -> dialog.dismiss());

    }

    public void setColorPickerListener(ColorPickerListener listener) {
        Log.i("xdjx", "setColorPickerListener");
        this.colorPickerListener = listener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void setCloseButton(boolean closeButton) {
        this.closeButton = closeButton ? View.VISIBLE : View.GONE;
    }

}
