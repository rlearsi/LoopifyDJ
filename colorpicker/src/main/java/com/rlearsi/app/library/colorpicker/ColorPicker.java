package com.rlearsi.app.library.colorpicker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

import java.util.Arrays;

public class ColorPicker {

    Context context;
    LayoutInflater factory;
    View view;
    AlertDialog dialog;
    private ColorPickerListener colorPickerListener;
    //ColorPickerListener colorPickerInterface;
    private String[] colors;
    private String title;
    private boolean cancelable = true;
    private int closeButton = View.VISIBLE;

    public ColorPicker (final Context context) {

        this.context = context;

    }

    public void openColorPicker() {

        factory = LayoutInflater.from(context);
        view = factory.inflate(R.layout.color_picker_alert, null);
        dialog = new AlertDialog.Builder(context).create();
        //dialog.setTitle(title);
        dialog.setView(view);

        ImageButton close = view.findViewById(R.id.close);
        close.setVisibility(closeButton);

        Log.i("xdjx", "Lista de Cores: " + Arrays.toString(colors));

        dialog.show();
        dialog.setCancelable(cancelable);

        close.setOnClickListener(v -> {

            // ação a ser executada quando o botão é clicado
            dialog.dismiss();

            onColorSelected("magenta");

        });

    }

    private void onColorSelected(String color) {

        if (colorPickerListener != null) {

            colorPickerListener.onColorSelected(color);

        }

    }

    public void setColorPickerListener(ColorPickerListener listener) {
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

    /*public void onClick(View id) {

        dialog.dismiss();
        colorPickerInterface.onColorSelected(1, 2, colors);

    }

    public void onClick() {
    }*/
}
