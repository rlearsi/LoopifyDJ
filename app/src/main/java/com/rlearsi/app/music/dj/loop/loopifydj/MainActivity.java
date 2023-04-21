package com.rlearsi.app.music.dj.loop.loopifydj;

import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.ADD_TO_FAV;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.REMOVE_FAV;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.REMOVE_LOOP;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.SET_LOOP;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.setHTML;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.rlearsi.app.library.colorpicker.ColorPicker;
import com.rlearsi.app.music.dj.loop.loopifydj.databinding.ActivityMainBinding;
import com.rlearsi.app.music.dj.loop.loopifydj.db.Dao;
import com.rlearsi.app.music.dj.loop.loopifydj.events.Adapter;
import com.rlearsi.app.music.dj.loop.loopifydj.events.Items;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements InterfaceUpdates {

    private static final String TAG = "xdjx";
    private Dao dao;
    private ActivityMainBinding main;
    Activity activity;
    Context context;
    InterfaceUpdates interfaceUpdates;
    //ColorPickerListener colorPickerListener;
    Adapter adapter;
    private HashMap<Integer, MediaPlayer> mediaPlayers = new HashMap<>();
    MediaPlayer mediaPlayer;
    ImageView button;
    DisplayMetrics metrics;
    ColorPicker colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(main.getRoot());

        activity = this;
        context = this;
        interfaceUpdates = this;
        //colorPickerListener = this;

        setSupportActionBar(main.toolbar);

        dao = new Dao(context);

        metrics = context.getResources().getDisplayMetrics();

        contentMain();

        colorPicker = new ColorPicker(context);
        //colorPicker.setColorPickerListener(colorPickerListener);

    }

    @Override
    public void alertDialogColor(int position, int id) {

        String[] colors = new String[]{"#0dc75a", "#f90b56", "#f3b919", "#7f00d9", "#1acbf7", "#ff74b7"};

        colorPicker.setColors(colors);
        //colorPicker.showTitle(false);
        //colorPicker.setCancelable(false);
        colorPicker.openColorPicker();

        colorPicker.setColorPickerListener(color -> {

            //IMPLEMENTE SEU CÓDIGO AQUI
            if (dao.setColor(id, color)) {

                adapter.updateRow(dao.returnByID(id));
                colorPicker.setDismiss();

            }

        });

    }

    public void contentMain() {

        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        main.rvListVoices.setLayoutManager(layoutManager);

        new Thread(() -> {

            // Adiciona o adapter que irá anexar os objetos à lista.
            adapter = new Adapter(dao.list(false), interfaceUpdates, context);

            runOnUiThread(() -> {

                main.rvListVoices.setAdapter(adapter);

            });

        }).start();

    }

    @Override
    public void handleItems(int position, int id, String name, String file_name, String color, boolean isLoop, boolean isFav) {

        String path = "android.resource://" + getPackageName() + "/raw/" + file_name;

        if (mediaPlayers.containsKey(id)) {

            try {

                if (Objects.requireNonNull(mediaPlayers.get(id)).isPlaying()) {

                    Objects.requireNonNull(mediaPlayers.get(id)).stop();
                    Objects.requireNonNull(mediaPlayers.get(id)).reset();

                }

            } catch (Exception ignored) {

            }

        }

        mediaPlayer = new MediaPlayer();

        this.mediaPlayers.put(id, mediaPlayer);

        try {

            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(path));
            mediaPlayer.prepare();
            mediaPlayer.setLooping(isLoop);

            mediaPlayer.setOnCompletionListener(player -> {

                player.stop();
                player.reset();

                adapter.updateRow(new Items(id, name, file_name, color, isLoop, isFav));
                dao.setPlay(id);

            });

        } catch (IOException e) {

            e.printStackTrace();

        }

        mediaPlayer.start();

    }

    @Override
    public void alertDialog(int id, int title, String description,
                            int go_text, int neutral_text, int position, int type, Items itens) {

        final int BUTTON_WIDTH = 190;

        //int textActionButtom = android.R.string.ok;
        boolean hasMessage = (!TextUtils.isEmpty(description));
        final boolean[] response = {false};
        final int SPACING_XS = dp(4), SPACING_SM = dp(6), SPACING_MD = dp(10), SPACING_LG = dp(16), ICON_SIZE = dp(34);

        AlertDialog builder = new AlertDialog.Builder(context).create();

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(SPACING_MD, SPACING_LG, SPACING_MD, SPACING_LG);

        //final ImageView closeDialogView = new ImageView(context);
        final TextView titleView = new TextView(context);
        final TextView messageView = new TextView(context);
        final TextView goButton = new TextView(context);
        final TextView neutralButton = new TextView(context);

        titleView.setPadding(SPACING_SM, SPACING_LG, SPACING_SM, SPACING_MD);
        titleView.setTextSize(22);
        titleView.setTextColor(Color.parseColor("#000000"));
        titleView.setText(title);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        layout.addView(titleView);

        if (hasMessage) {

            messageView.setPadding(SPACING_MD, 0, SPACING_MD, SPACING_MD);
            messageView.setTextSize(16);
            messageView.setTextColor(Color.parseColor("#93939e"));
            messageView.setGravity(Gravity.CENTER);
            messageView.setText(setHTML(description));

            layout.addView(messageView);

        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (dp(BUTTON_WIDTH), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(0, SPACING_XS, 0, SPACING_XS);

        if (go_text != 0) {

            goButton.setPadding(0, SPACING_MD, 0, SPACING_MD);
            goButton.setTextSize(14);
            goButton.setTextColor(Color.parseColor("#FFFFFF"));
            goButton.setText(getString(go_text).toUpperCase());
            //goButton.setBackground(ContextCompat.getDrawable(context, R.drawable.button));
            params.gravity = Gravity.CENTER_HORIZONTAL;
            goButton.setLayoutParams(params);
            goButton.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(goButton);

        }

        if (neutral_text != 0) {
            neutralButton.setPadding(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD);
            neutralButton.setTextSize(14);
            neutralButton.setTextColor(Color.parseColor("#000000"));
            neutralButton.setText(getString(neutral_text).toUpperCase());
            neutralButton.setGravity(Gravity.CENTER);
            layout.addView(neutralButton);
        }

        builder.setView(layout);
        builder.setCancelable(false);
        builder.show();

        neutralButton.setOnClickListener(v -> builder.dismiss());

        goButton.setOnClickListener(v -> {

            response[0] = true;
            builder.dismiss();

            //Lidando com o resultado do dialog alert
            handleAlertOptions(position, id, type, itens);

        });

        builder.setOnDismissListener(v -> button.setClickable(false));

    }

    @Override
    public void handleAlertOptions(int position, int id, int type, Items itens) {

        Log.i(MyVars.TAG, "handleAlertOptions: " + id + ", " + type);

        if (type == SET_LOOP) {

            dao.setLoop(id);
            adapter.updateRow(new Items(id, itens.getName(), itens.getFileName(), itens.getColor(), true, itens.isFav()));

        } else if (type == REMOVE_LOOP) {

            dao.removeLoop(id);
            adapter.updateRow(new Items(id, itens.getName(), itens.getFileName(), itens.getColor(), false, itens.isFav()));

        } else if (type == ADD_TO_FAV) {

            dao.setFav(id);
            adapter.updateRow(new Items(id, itens.getName(), itens.getFileName(), itens.getColor(), itens.isLoop(), true));

        } else if (type == REMOVE_FAV) {

            dao.removeFav(id);
            adapter.updateRow(new Items(id, itens.getName(), itens.getFileName(), itens.getColor(), itens.isLoop(), false));

        }

        //adapter.updateRow(itens);

    }

    private int dp(float dp) {

        float fpixels = metrics.density * dp;

        return (int) (fpixels + 0.5f);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }*/

    /*@Override
    public void onBackPressed() {

        if (isHome) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            toast(getString(R.string.press_again_to_exit));

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

        } else {

            showHome();

        }

    }*/

    public void destroy(HashMap<Integer, MediaPlayer> mp) {

        for (Map.Entry<Integer, MediaPlayer> entry : mp.entrySet()) {

            Integer key = entry.getKey();

            Objects.requireNonNull(mp.get(key)).release();

            Log.i(TAG, "o MediaPlayer " + key + " foi liberado");

        }

    }

    @Override
    public void onStop() {

        destroy(mediaPlayers);
        super.onStop();
    }

    @Override
    public void onDestroy() {

        destroy(mediaPlayers);
        super.onDestroy();
    }

}