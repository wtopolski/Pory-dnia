package pl.wtopolski.android.sunsetwidget.provider;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesStorage {
    private static final String PREFERENCES_NAME = "PORY_DNIA";
    private static final String S_DEFAULT = "";
    private static final int I_DEFAULT = 0;
    private static final boolean B_DEFAULT = false;

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

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        return settings.getBoolean(key, B_DEFAULT);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
