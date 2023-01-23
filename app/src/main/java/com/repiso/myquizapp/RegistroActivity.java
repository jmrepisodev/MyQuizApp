package com.repiso.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private EditText et_name, et_password, et_password_confirm, et_email;
    private String name, email, pass, repass,rol;
    private Button btn_registro;
    private TextView btn_login;
    private CheckBox checkBoxLegal;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et_name =(EditText) findViewById(R.id.et_name_registro);
        et_password =(EditText) findViewById(R.id.et_pass_registro);
        et_password_confirm =(EditText) findViewById(R.id.et_pass_confirm);
        et_email =(EditText) findViewById(R.id.et_email_registro);
        checkBoxLegal =(CheckBox)findViewById(R.id.checkBox_legal);
        btn_registro=(Button) findViewById(R.id.btn_registro);

        btn_login=(TextView)findViewById(R.id.tv_btn_login);
        //coloca el texto subrayado
        btn_login.setText(Html.fromHtml(getResources().getString(R.string.tv_btn_login)));

        //Inicializa las bases de datos SQLite. Creamos un objeto de la clase auxiliar (conexion a la base de datos).
        dbHelper=DBHelper.getInstance(this);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //conseguimos el texto de los campos
                name= et_name.getText().toString().trim();
                email= et_email.getText().toString().trim();
                pass= et_password.getText().toString().trim();
                repass= et_password_confirm.getText().toString().trim();
                rol="user";

                //Comprueba que todos los campos están rellenos
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Existen campos vacíos. Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
                }else{

                    //Validamos los datos
                    if(validarUserName(name) && pass.equals(repass) && checkBoxLegal.isChecked() && validarEmail(email)){ //comprueba que ambas contraseñas coinciden, el email es válido y se ha aceptado las condiciones

                        //Comprueba si no existe el email
                        if(dbHelper.checkUserEmail(email)==false){
                            Usuario usuario=new Usuario(name, email, pass, rol);
                            if(dbHelper.addUser(usuario)){
                                showDialogSuccess();
                            }else{
                                Toast.makeText(getApplicationContext(),"Error: No ha sido posible registrar la cuenta",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"Error: Ya existe un usuario con esos datos. Registre otro email",Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        if(!validarUserName(name)){
                            et_name.setError("El nombre solo puede contener caracteres alfanuméricos y contener de 4 a 16 caracteres");
                        }

                        if(!checkBoxLegal.isChecked()){
                            checkBoxLegal.setError("Debe aceptar todas las condiciones legales");
                        }

                        if(!pass.equals(repass)){
                            et_password.setError("Las contraseñas no coinciden");
                            et_password_confirm.setError("Las contraseñas no coinciden");
                        }

                        if(validarEmail(email)==false){
                            et_email.setError("EL email no es válido");
                        }

                    }
                }

            }


        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Valida el nombre de usuario.
     * Solo puede contener caracteres alfanuméricos o espacios y debe comenzar por una letra y tener una longitud entre 4 y 16 caracteres
     * @param name
     * @return
     */
    public Boolean validarUserName(String name) {

        //Si el nombre está vacio retorna false
        if (name == null) {
            return false;
        }

        //Expresión regular o patrón.
        String regex = "^[A-Za-z][\\w\\s]{3,15}$";
        // Compila el patrón
        Pattern pattern = Pattern.compile(regex);
        //Busca la coincidencia entre el patrón y la expresión regular
        Matcher matcher = pattern.matcher(name);

        //Retorna true si la entrada coincide con el patrón
        return matcher.matches();
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


    /**
     * Muestra una ventana de dialogo, con opciones para el usuario
     */
    private void showDialogSuccess(){

        //Create the Dialog here
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_registro);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialgog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Okay", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

                dialog.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}