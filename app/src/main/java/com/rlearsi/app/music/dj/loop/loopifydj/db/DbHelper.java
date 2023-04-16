package com.rlearsi.app.music.dj.loop.loopifydj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "loopifydb.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SOUND = "sounds";

    private final String C_TABLE_VOICES = "CREATE TABLE IF NOT EXISTS " + TABLE_SOUND + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, file_name TEXT NOT NULL, color TEXT, isLoop INTEGER, played INTEGER, soundType INTEGER, " +
            "fav INTEGER, pid INTEGER, active INTEGER, datetime TEXT, " +
            "UNIQUE(file_name) ON CONFLICT IGNORE);";

    private final String C_IDX = "CREATE INDEX IF NOT EXISTS fav_idx ON " + TABLE_SOUND + "(ID,fav);";
    private final String C_IDX_2 = "CREATE INDEX IF NOT EXISTS played_idx ON " + TABLE_SOUND + "(ID,played);";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

        db.execSQL(C_TABLE_VOICES);
        db.execSQL(C_IDX);
        db.execSQL(C_IDX_2);

        try{

            insertVoice(db, "Clap 1", "clap_1", 1);
            insertVoice(db, "Clap 2", "clap_2", 1);
            insertVoice(db, "Clap 3", "clap_3", 1);
            insertVoice(db, "Clap 4", "clap_4", 1);


        } catch (Exception ignored) {}

    }

    public static void insertVoice(SQLiteDatabase db, String name, String file_name, int type) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("file_name", file_name);
        values.put("color", "#7f00d9");
        values.put("isLoop", "0");
        values.put("played", 0);
        values.put("soundType", type);
        values.put("fav", 0);
        values.put("pid", 0);
        values.put("active", 1);
        values.put("datetime", String.valueOf(new Date()));

        db.insert(TABLE_SOUND, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // create new tables
        onCreate(db);

    }

}