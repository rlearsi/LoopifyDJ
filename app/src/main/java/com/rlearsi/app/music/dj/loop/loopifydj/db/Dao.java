package com.rlearsi.app.music.dj.loop.loopifydj.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rlearsi.app.music.dj.loop.loopifydj.MyVars;
import com.rlearsi.app.music.dj.loop.loopifydj.events.Items;

import java.util.ArrayList;
import java.util.List;

public class Dao {

    private static DbGateway gw;

    public Dao(Context ctx) {
        gw = DbGateway.getInstance(ctx);
    }

    public void setFav(int id) {

        ContentValues cv = new ContentValues();

        cv.put("fav", 1);

        gw.getDatabase().update(DbHelper.TABLE_SOUND, cv, "ID=?", new String[]{id + ""});

    }

    public void removeFav(int id) {

        ContentValues cv = new ContentValues();

        cv.put("fav", 0);

        gw.getDatabase().update(DbHelper.TABLE_SOUND, cv, "ID=?", new String[]{id + ""});

    }

    public void setLoop(int id) {

        ContentValues cv = new ContentValues();

        cv.put("isLoop", 1);

        gw.getDatabase().update(DbHelper.TABLE_SOUND, cv, "ID=?", new String[]{id + ""});

    }

    public void removeLoop(int id) {

        ContentValues cv = new ContentValues();

        cv.put("isLoop", 0);

        gw.getDatabase().update(DbHelper.TABLE_SOUND, cv, "ID=?", new String[]{id + ""});

    }

    public boolean setColor(int id, String color) {

        ContentValues cv = new ContentValues();

        cv.put("color", color);

        return (gw.getDatabase().update(DbHelper.TABLE_SOUND, cv, "ID=?", new String[]{id + ""}) > 0);

    }

    public void setPlay(int id) {

        int value = getPlays(id);

        ContentValues cv = new ContentValues();

        cv.put("played", value + 1);

        gw.getDatabase().update(DbHelper.TABLE_SOUND, cv, "ID=?", new String[]{id + ""});

    }

    @SuppressLint("Range")
    public List<Items> list(boolean isFav) {

        List<Items> list = new ArrayList<>();

        int intValue = isFav ? 1 : 0;

        Cursor cursor = gw.getDatabase().rawQuery("SELECT ID, name, file_name, color, isLoop FROM "
                + DbHelper.TABLE_SOUND + " WHERE fav = " + intValue
                + " ORDER BY ID ASC", null);
        
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String file_name = cursor.getString(cursor.getColumnIndex("file_name"));
            String color = cursor.getString(cursor.getColumnIndex("color"));
            int isLoop = cursor.getInt(cursor.getColumnIndex("isLoop"));
            //int isFav = cursor.getInt(cursor.getColumnIndex("isFav"));

            list.add(new Items(id, name, file_name, color, isLoop == 1, intValue == 1));

        }

        cursor.close();

        return list;

    }

    @SuppressLint("Range")
    public Items returnByID(int id) {

        //List<Items> list = new ArrayList<>();
        String name = "", file_name = "", color = "";
        int isLoop = 0, isFav = 0;

        Cursor cursor = gw.getDatabase().rawQuery("SELECT ID, name, file_name, color, isLoop, fav FROM "
                + DbHelper.TABLE_SOUND + " WHERE ID = " + id + " LIMIT 1", null);

        if (cursor.moveToFirst()) {

            name = cursor.getString(cursor.getColumnIndex("name"));
            file_name = cursor.getString(cursor.getColumnIndex("file_name"));
            color = cursor.getString(cursor.getColumnIndex("color"));
            isLoop = cursor.getInt(cursor.getColumnIndex("isLoop"));
            isFav = cursor.getInt(cursor.getColumnIndex("fav"));

        }

        cursor.close();

        return new Items(id, name, file_name, color, isLoop == 1, isFav == 1);

    }

    @SuppressLint("Range")
    public int returnLastId() {

        int id = 0;

        Cursor cursor = gw.getDatabase().rawQuery("SELECT ID FROM " + DbHelper.TABLE_SOUND + " ORDER BY ID DESC LIMIT 1", null);

        if (cursor.moveToFirst()) {

            id = cursor.getInt(cursor.getColumnIndex("ID"));

            cursor.close();

        }

        return id;

    }

    @SuppressLint("Range")
    private int getPlays(int id) {

        Cursor cursor = gw.getDatabase().query(DbHelper.TABLE_SOUND, new String[] { "played" }, "ID = ?",
                new String[] { id + "" }, null, null, null);

        int contador = 0;

        if (cursor.moveToFirst()) {

            contador = cursor.getInt(cursor.getColumnIndex("played"));

        }

        cursor.close();

        return contador;

    }

}