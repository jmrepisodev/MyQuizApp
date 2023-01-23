package com.repiso.myquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settingsContainer, new SettingsFragment())
                    .commit();
        }

        //muestra el botón volver de la barra de menú
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //muestra el título en la barra herramientas
        //getSupportActionBar().setTitle(R.string.nav_setting);

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    /**
     * Implementa la funcionalidad del botón volver de la barra de menú
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        //vuelve a la pantalla anterior
        setResult(RESULT_OK);
        finish();
        return true;
    }
}