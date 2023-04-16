package com.rlearsi.app.music.dj.loop.loopifydj;

import com.rlearsi.app.music.dj.loop.loopifydj.events.Items;

public interface InterfaceUpdates {

    void handleItems(int position, int id, String name, String file_name, String color, boolean isLoop, boolean isFav);

    void alertDialog(int id, int title, String description,
                      int go_text, int neutral_text, int position, int type, Items itens);

    void handleAlertOptions(int position, int id, int type, Items itens);

    void alertDialogColor(int position, int id);

}
