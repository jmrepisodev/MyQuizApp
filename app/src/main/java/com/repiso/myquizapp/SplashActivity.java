package com.repiso.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private Boolean isLogin=false;
    private TextView appName;
    private ImageView appImage;
    private Boolean firsTime;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Realizamos las asociaciones elementos gráficos - variables
        appName=(TextView)findViewById(R.id.tv_app_name);
        appImage=(ImageView) findViewById(R.id.iv_splash);

        //Animación del texto
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        appName.setAnimation(animation);

        //Recupera la información de inicio de sesión
        SessionManager sessionManager=new SessionManager(this);
        isLogin=sessionManager.getLogin();
        rol=sessionManager.getUserRol();
        firsTime=sessionManager.getFirstTime();


        /**
         * Handler: administra y gestiona una cola de procesos o tareas, asociadas a un hilo de ejecución
         * Recibe un objeto que implemente Runnable y el tiempo de espera
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogin==true && rol!=null){

                    if(rol.equalsIgnoreCase("admin")){
                        Intent intent=new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }else {
                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        },3000); //Carga la actividad principal en 3 segundos



    }
}