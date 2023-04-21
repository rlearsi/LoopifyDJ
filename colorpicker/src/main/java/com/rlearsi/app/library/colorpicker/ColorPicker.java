package com.rlearsi.app.library.colorpicker;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.rlearsi.app.library.colorpicker.databinding.ColorPickerLayoutBinding;

public class ColorPicker {

    Context context;
    LayoutInflater factory;
    //public static int LIGHT = 1, DARK = 2;

    View view;
    private AlertDialog dialog;
    private ColorPickerListener colorPickerListener;
    ColorPickerListener colorPickerInterface;
    ColorPickerLayoutBinding layout;
    private String[] colors;
    private String title = "";
    private boolean cancelable = true;
    //private int closeButton = View.VISIBLE;
    private GridView gridView;
    private Button button;
    private boolean showTitle = true;
    private boolean backgroundFromTheme = true;
    //private Dialog mDialog;

    public ColorPicker(final Context context) {

        this.context = context;

    }

    public void openColorPicker() {

        factory = LayoutInflater.from(context);
        layout = ColorPickerLayoutBinding.inflate(factory);
        gridView = layout.rvColors;

        if (this.showTitle) {

            if (this.title.isEmpty()) layout.dialogTitle.setText(context.getString(R.string.choose_a_color));
            else layout.dialogTitle.setText(this.title);

        }

        layout.dialogTitle.setVisibility(showTitle ? View.VISIBLE : View.GONE);

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

        });

        // Cria e configura o diálogo
        dialog = new AlertDialog.Builder(context)
                .setView(layout.getRoot())
                .setCancelable(cancelable)
                .create();

        dialog.show();

        // Obtém a referência ao botão de fechar e define o seu comportamento
        layout.closeButton.setOnClickListener(v -> dialog.dismiss());

    }

    public void setDismiss() {
        this.dialog.dismiss();
    }

    public void setColorPickerListener(ColorPickerListener listener) {
        this.colorPickerListener = listener;
    }

    public void showTitle(boolean visibility) {

        this.showTitle = visibility;

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

}
