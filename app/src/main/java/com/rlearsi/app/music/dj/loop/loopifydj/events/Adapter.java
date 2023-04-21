package com.rlearsi.app.music.dj.loop.loopifydj.events;

import static android.content.ContentValues.TAG;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.ADD_TO_FAV;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.REMOVE_FAV;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.REMOVE_LOOP;
import static com.rlearsi.app.music.dj.loop.loopifydj.MyVars.SET_LOOP;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rlearsi.app.music.dj.loop.loopifydj.InterfaceUpdates;
import com.rlearsi.app.music.dj.loop.loopifydj.R;

import java.util.EventListener;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Holder> {

    private List<Items> events;
    private int type;
    private Context context;
    private long date;
    private String current_week;
    private int ciclo_menstrual = 0;
    private InterfaceUpdates interfaceUpdates;

    public Adapter(List<Items> events, InterfaceUpdates interfaceUpdates, Context context) {
        this.events = events;
        this.context = context;
        this.interfaceUpdates = interfaceUpdates;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int view = R.layout.list_voices;

        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        final Items topic = events.get(position);

        //Log.i(TAG, "CICLO: " + ciclo_menstrual);

        final String name = topic.getName();
        final int id = topic.getId();
        final String file_name = topic.getFileName();
        final String color = topic.getColor();
        final boolean isLoop = topic.isLoop();
        final boolean isFav = topic.isFav();

        holder.title_voice.setText(name);

        //set default color
        int contextColor = ContextCompat.getColor(context, R.color.black_002);
        //holder.box.setBackground(ContextCompat.getDrawable(context, R.drawable.box_square));

        if (holder.backgroundDrawable instanceof LayerDrawable) {

            LayerDrawable layerDrawable = (LayerDrawable) holder.backgroundDrawable;
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.player_one);
            gradientDrawable.setColors(new int[]{contextColor, contextColor});

        }

        holder.box.setOnClickListener(v -> {

            //holder.box.setAlpha(0.8f);

            if (holder.backgroundDrawable instanceof LayerDrawable) {

                LayerDrawable layerDrawable = (LayerDrawable) holder.backgroundDrawable;
                GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.player_one);
                gradientDrawable.setColors(new int[]{Color.parseColor(color), Color.parseColor(color)});

            }

            interfaceUpdates.handleItems(position, id, name, file_name, color, isLoop, isFav);

        });

        //holder.button_menu.setOnClickListener(v -> holder.itemView.showContextMenu());
        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {

            //menu.setHeaderTitle(title);

            /*menu.add(R.string.add_to_fav).setOnMenuItemClickListener(item -> {

                if (isFav) interfaceUpdates.handleAlertOptions(position, id, REMOVE_FAV, topic);
                else interfaceUpdates.handleAlertOptions(position, id, ADD_TO_FAV, topic);

                return true;

            });*/

            menu.add(R.string.looping).setOnMenuItemClickListener((item) -> {

                if (isLoop) interfaceUpdates.handleAlertOptions(position, id, REMOVE_LOOP, topic);
                else interfaceUpdates.handleAlertOptions(position, id, SET_LOOP, topic);
                return true;

            });

            menu.add(R.string.change_color).setOnMenuItemClickListener((item) -> {

                interfaceUpdates.alertDialogColor(position, id);

                return true;

            });

        });

    }

    public void addTopic(Items event) {

        events.add(getItemCount(), event);
        notifyItemInserted(getItemCount());

    }

    public void updateRow(Items event) {

        int position = events.indexOf(event);
        events.set(position, event);

        notifyItemChanged(position);

    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
