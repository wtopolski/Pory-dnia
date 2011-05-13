package pl.wtopolski.android.sunsetwidget.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesStorage {
    private static final String PREFERENCES_NAME = "PORY_DNIA";
    public static final String CITY_SAVED = "CITY_SAVED";
    public static final String CITY_SAVED_ID = "CITY_SAVED_ID";
    private static final String S_DEFAULT = "";
    private static final int I_DEFAULT = 0;

    public static String getString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        return settings.getString(key, S_DEFAULT);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        return settings.getInt(key, I_DEFAULT);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
