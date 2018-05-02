package de.wirtgen.staiger.barkeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Staiger/Wirtgen on 19.03.2018.
 */

public class LanguageManager {

    private static final LanguageManager ourInstance = new LanguageManager();
    private static Language defaultLanguage;
    private static Language currentLanguage;

    public static LanguageManager getInstance() {
        return ourInstance;
    }

    public enum Language {
        ENGLISH("en_en", 2, Locale.ENGLISH.getLanguage()),
        GERMAN("de_de", 1, Locale.GERMAN.getLanguage());

        private final String localeString;
        private final long id;
        private final String locale;

        Language(String localeStr, long id, String locale){
            this.localeString = localeStr;
            this.id = id;
            this.locale = locale;
        }

        public long getId() {
            return id;
        }
    }


    private LanguageManager() {
        defaultLanguage = Language.ENGLISH;
        currentLanguage = Language.ENGLISH;
    }

    public static Language getCurrentLanguage(){
        return currentLanguage;
    }

    public static Context setLanguage(Context c, Language newLanguage){
        Log.d("BarkeeperApp", "LANGUAGE MANAGER setLanguage");
        currentLanguage = newLanguage;
        return update(c, newLanguage);
    }

    public static void getLanguageFromSharedPref(Context c){
        Log.d("BarkeeperApp", "LANGUAGE MANAGER getLanguageFromSharedPref");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);
        String lang = pref.getString("language_key", "");
        Log.d("BarkeeperApp", "LangKey: " + lang);
        if (!lang.isEmpty()){
            switch (lang){
                case "de": setLanguage(c, Language.GERMAN); break;
                case "en": setLanguage(c, Language.ENGLISH); break;
                default: setLanguage(c, Language.ENGLISH); break;
            }
        }
        else{
            setLanguage(c, Language.ENGLISH);
        }
    }

    private static Context update(Context c, Language l){
        Log.d("BarkeeperApp", "LANGUAGE MANAGER update");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);
        //pref.edit().putString("language_key", l.locale).apply();
        String lang = pref.getString("language_key", "");
        Log.d("BarkeeperApp", "update LangKey: " + lang);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("language_key", l.locale);
        editor.commit();
        //editor.apply();

        Locale locale = new Locale(l.locale);
        Locale.setDefault(locale);

        Resources r = c.getResources();
        Configuration config = r.getConfiguration();

        if (Build.VERSION.SDK_INT >= 17){
            config.setLocale(locale);
            c = c.createConfigurationContext(config);
        }
        else{
            config.locale = locale;
            r.updateConfiguration(config, r.getDisplayMetrics());
        }

        return c;
    }

}
