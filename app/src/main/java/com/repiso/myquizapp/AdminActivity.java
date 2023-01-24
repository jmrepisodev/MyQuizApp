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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    private String username, email, rol;
    private Boolean isLogin;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Inicia sesión de usuario
        sessionManager = new SessionManager(getApplicationContext());
        username=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();
        isLogin=sessionManager.getLogin();

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
        AdminHomeFragment adminHomeFragment=new AdminHomeFragment();
        QuestionFragment questionFragment=new QuestionFragment();
        CategoryFragment categoryFragment=new CategoryFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_home_admin:
                        setFragment(adminHomeFragment);
                        break;
                    case R.id.item_category:
                        setFragment(categoryFragment);
                        break;
                    case R.id.item_question:
                        setFragment(questionFragment);
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
                    case R.id.item_home_admin:
                        setFragment(adminHomeFragment);
                        break;
                    case R.id.item_category:
                        setFragment(categoryFragment);
                        break;
                    case R.id.item_question:
                        setFragment(questionFragment);
                        break;
                }

                navigationView.setCheckedItem(item.getItemId());

                return true;
            }
        });

        if(savedInstanceState==null){
            setFragment(adminHomeFragment);
            bottomNavigationView.setSelectedItemId(R.id.item_home_admin);
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
     * Nuevo método alternativo al antiguo onActivityResult. No tiene código de respuesta.
     */
    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        //Refresca la actividad
                        Intent refresh=getIntent();
                        startActivity(refresh);
                        finish();

                    }
                }
            });

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


}