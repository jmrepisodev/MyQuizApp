package com.repiso.myquizapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;

    private String username, email;
    private TextView tv_name, tv_email;
    private int userID;

    public static final String KEY_CATEGORY_ID = "keyCategoryID";
    public static final String KEY_CATEGORY_NAME = "keyCategorName";
    public static final String KEY_LEVEL = "keyLevel";
    private final int REQUEST_CODE_SETTING=7;
    Boolean sound;
    String language;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicia sesión de usuario
        sessionManager = new SessionManager(getApplicationContext());
        username=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();

        //Recupera las preferencias de configuración
        Preferencias.getPreferences(this);
        sound=Preferencias.sound;
        language=Preferencias.language;
        //Establece el idioma de la aplicación
        setLocale(language);


        //ActionBar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation Drawer
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigationView);

        //Botón menú lateral
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Menú inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);

        //Fragments
        HomeFragment homeFragment= new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        HelpFragment helpFragment = new HelpFragment();
        RankingFragment rankingFragment = new RankingFragment();

        View headerView = navigationView.getHeaderView(0);
        tv_name = (TextView) headerView.findViewById(R.id.header_name_user);
        tv_email = (TextView) headerView.findViewById(R.id.header_email_user);

        tv_name.setText(username);
        tv_email.setText(email);





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.item_home:
                        setFragment(homeFragment);
                        break;
                    case R.id.item_profile:
                        setFragment(profileFragment);
                        break;
                    case R.id.item_help:
                        setFragment(helpFragment);
                        break;
                    case R.id.item_ranking:
                        setFragment(rankingFragment);
                        break;
                }

                bottomNavigationView.setSelectedItemId(item.getItemId());

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_home:
                        setFragment(homeFragment);
                        break;
                    case R.id.item_profile:
                        setFragment(profileFragment);
                        break;
                    case R.id.item_help:
                        setFragment(helpFragment);
                        break;
                    case R.id.item_ranking:
                        setFragment(rankingFragment);
                        break;
                }

                navigationView.setCheckedItem(item.getItemId());

                return true;
            }
        });

        if(savedInstanceState==null){
            setFragment(homeFragment);
            navigationView.setCheckedItem(R.id.item_home);
            bottomNavigationView.setSelectedItemId(R.id.item_home);
        }


    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    /**
     * Inicializa y reemplaza un fragment por otro en el contenedor FrameLayout
     * @param fragment
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }


    /**
     * Agrega el menú a la actionBar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);

        return true;
    }

    /**
     * Gestiona los items de la actionBar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_setting:
                Intent intent=new Intent(getApplicationContext(), SettingActivity.class);
                //Lanza la actividad y permanece a la espera de una respuesta
                // startActivityForResult(intent,REQUEST_CODE_SETTING);
                startActivityIntent.launch(intent);
                break;

            case R.id.item_exit:
                cerrarSesion();
                break;
        }

        return true;
    }




    /**
     * Muestra un mensaje modal de alerta con opciones para el usuario
     */
    public void cerrarSesion(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("CERRAR SESIÓN");
        builder.setIcon(R.drawable.nav_info);
        builder.setMessage("¿Está seguro que desea cerrar sesión?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logout();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }


    /**
     * Recoge la respuesta de una actividad que ha sido llamada con StartActivityForResult.
     * Si el código de respuesta coincide y la respuesta es OK, realiza unas acciones. Método obsoloto
     * @param requestCode código respuesta
     * @param resultCode resultado
     * @param data data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SETTING) {
            if(resultCode == Activity.RESULT_OK){
                //Recupera las preferencias de configuración
                Preferencias.getPreferences(getApplicationContext());
                sound=Preferencias.sound;
                language=Preferencias.language;
                //Establece el idioma de la aplicación
                setLocale(language);

                //Refresca la actividad
                Intent refresh=getIntent();
                startActivity(refresh);
                finish();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    /**
     * Nuevo método alternativo al antiguo onActivityResult. No tiene código de respuesta.
     */
    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Recupera las preferencias de configuración
                        Preferencias.getPreferences(getApplicationContext());
                        sound=Preferencias.sound;
                        language=Preferencias.language;
                        //Establece el idioma de la aplicación
                        setLocale(language);

                        //Refresca la actividad
                        Intent refresh=getIntent();
                        startActivity(refresh);
                        finish();

                    }
                }
            });

    /**
     * Establece la configuración de idioma
     * @param language código de idioma
     */
    public void setLocale(String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, dm);
    }





}