package com.rlearsi.app.music.dj.loop.loopifydj;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Objects;

public class MyVars {

    public static final String TAG = "xdjx";
    public static String MY_LOCALE = Locale.getDefault().toString();
    public static String MY_LANG = Locale.getDefault().getLanguage();
    public static String EMAIL = "learsifranca@gmail.com";
    public static String APP_PACKAGE_BABY_NAME = "com.rlearsi.app.xxx";

    public static final int BUTTON_WIDTH = 160,
            REQUEST_CODE = 1, REQUEST_CODE_IMPORT = 2,
            SHOW_STORE = 3, GO_APP_STORE = 4, RESTART_APP = 5, CREATE_FILE = 6, EXPORT_DRIVE = 7,
            GO_DEV_STORE = 8, GO_MAIL = 9, GO_PRIVACY = 10,
            OPEN_DIALOG_ALERT = 11, SET_LOOP = 12, REMOVE_LOOP = 13, ADD_TO_FAV = 14, REMOVE_FAV = 15, CHANGE_COLOR = 16;

    private String mydir = "";

    public static boolean IS_COUNTER = false;

    public static String DEFAULT_COLOR = "#f3576c";

    public static boolean writeFileOnInternalStorage(String sBody, Context context) throws IOException {

        OutputStream outputStream;
        String file_name = String.format(Locale.ENGLISH, "%s%s%s", "BabyDay_", MyDate.date, ".json");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, file_name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, sBody);
            Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

            //Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);      //important!

            outputStream = resolver.openOutputStream(uri);
            outputStream.write(sBody.getBytes());
            outputStream.close();

            Objects.requireNonNull(outputStream).close();

        } else {

            String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(dir, file_name);

            FileWriter writer = new FileWriter(file);

            writer.append(sBody);
            writer.flush();
            writer.close();

        }

        return true;

    }

    public static void toShare(String sBody, Context context) {

        String title = String.format(Locale.ENGLISH, "%s%s%s", "BabyDay_", MyDate.date, ".json");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, title);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sBody);
        sendIntent.setPackage("com.google.android.apps.docs");

        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, context.getString(R.string.save_to));
        context.startActivity(shareIntent);

    }

    public static String importFile(Uri data, Context context) {

        BufferedReader reader = null;

        try {
            // open the user-picked file for reading:
            InputStream in = context.getContentResolver().openInputStream(data);
            // now read the content:
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            // Do something with the content in
            //Log.i(TAG, builder.toString());

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";

    }

    public static Spanned setHTML(String text) {

        return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);

    }

}
