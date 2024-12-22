package com.example.itcompanies;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageUtils {

    private static final String LANGUAGE_KEY = "language_preference";
    private static final String PREF_NAME = "app_preferences";

    public static void setAppLanguage(Context context, String languageCode) {
        // Sauvegarder la langue dans SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();

        // Appliquer la langue
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public static String getAppLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE_KEY, Locale.getDefault().getLanguage());
    }
}
