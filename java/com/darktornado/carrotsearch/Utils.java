package com.darktornado.carrotsearch;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static final String PREFERENCES_NAME = "carrot_settings";

    public static void saveData(Context ctx, String name, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static void saveSettings(Context ctx, String name, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static void saveSettings(Context ctx, String name, int value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static String readData(Context ctx, String name) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(name, null);
    }

    public static boolean loadSettings(Context ctx, String name, boolean defaultSettings) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(name, defaultSettings);
    }

    public static int loadSettings(Context ctx, String name, int defaultSettings) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getInt(name, defaultSettings);
    }

}
