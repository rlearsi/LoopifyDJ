package com.rlearsi.app.music.dj.loop.loopifydj.events;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rlearsi.app.music.dj.loop.loopifydj.R;

class Holder extends RecyclerView.ViewHolder {

    public ConstraintLayout box;
    public TextView title_voice;
    //public ImageButton button_menu;
    //GradientDrawable gradientDrawable;
    public Drawable backgroundDrawable;

    Holder(View itemView) {

        super(itemView);

        title_voice = itemView.findViewById(R.id.title_voice);
        box = itemView.findViewById(R.id.box);
        //button_menu = itemView.findViewById(R.id.button_menu);

        //gradientDrawable = (GradientDrawable) box.getBackground();
        backgroundDrawable = box.getBackground();


    }

}