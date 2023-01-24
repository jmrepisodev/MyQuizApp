package com.repiso.myquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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

}