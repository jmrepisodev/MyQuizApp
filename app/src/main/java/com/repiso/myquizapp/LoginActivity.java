package com.repiso.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private String email, pass;
    private Button btn_login;
    private TextView btn_registro;
    private DBHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email =(EditText) findViewById(R.id.et_email_login);
        et_password =(EditText) findViewById(R.id.et_pass_login);

        btn_login=(Button) findViewById(R.id.btn_login);

        btn_registro=(TextView)findViewById(R.id.tv_btn_registro);
        //coloca el texto subrayado
        btn_registro.setText(Html.fromHtml(getResources().getString(R.string.tv_btn_registro)));

        //Inicia sesión de usuario
        sessionManager = new SessionManager(this);

        //Inicializa la base de datos
        dbHelper=DBHelper.getInstance(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //conseguimos el texto de los campos
                email=et_email.getText().toString().trim();
                pass= et_password.getText().toString().trim();

                //Comprueba que todos los campos están rellenos
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Existen campos vacíos. Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
                }else{

                    if(validarEmail(email)){
                        Usuario usuario;
                        usuario=dbHelper.checkPasswordUser(email,pass);

                        if(usuario!=null){

                            //Guardamos la sesión de usuario
                            sessionManager.setLogin(true);
                            sessionManager.setUserId(usuario.getId());
                            sessionManager.setUserName(usuario.getName());
                            sessionManager.setUserEmail(usuario.getEmail());
                            sessionManager.setUserPassword(usuario.getPassword());
                            sessionManager.setUserRol(usuario.getRol());

                            //Limpiamos los campos de texto
                            et_email.getText().clear();
                            et_password.getText().clear();

                            if(usuario.getRol().equalsIgnoreCase("admin")){
                                //Iniciamos la actividad principal
                                Intent intent=new Intent(getApplicationContext(),AdminActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                //Iniciamos la actividad principal
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"Error: el email o la contraseña no son válidos",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        et_email.setError("Email no válido");
                    }

                }
            }
        });

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Comprueba que se ha introducido un email válido
     * @param email
     * @return
     */
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}