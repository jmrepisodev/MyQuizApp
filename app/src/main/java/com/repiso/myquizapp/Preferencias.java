package com.repiso.myquizapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Preferencias {


    public static final String KEY_SOUND = "keySound";
    public static final String KEY_VIBRATION ="KeyVibration";
    public static final String KEY_LANGUAGE = "keyLanguage";
    public static final String KEY_THEME = "keyTheme";

    //Variables estáticas. Los valores se cambian para toda la aplicación
    public static Boolean sound, vibration;
    public static String language;


    private Context context;

    // Shared Preferences
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    /**
     * Método constructor
     * @param context contexto de aplicación
     */
    public Preferencias(Context context) {
        this.context = context;

    }

    /**
     * Método estático. Recupera las preferencias generales de configuración
     * @param context
     */
    public static void getPreferences(Context context){

        //Recuperamos las preferencias de configuración generales
        SharedPreferences preferencesSettings= PreferenceManager.getDefaultSharedPreferences(context);

        sound = preferencesSettings.getBoolean(KEY_SOUND, true);
        vibration = preferencesSettings.getBoolean(KEY_VIBRATION, true);
        language=preferencesSettings.getString(KEY_LANGUAGE,"es");

    }


    /**
     * Elimina todas las preferencias almacenadas
     * @param context
     */
    public static void clearPrefereces(Context context){
        //Recuperamos las preferencias de configuración
        SharedPreferences preferencesSettings= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=preferencesSettings.edit();
        //Elimina todas las preferencias
        editor.clear();
        editor.apply();
    }


}
