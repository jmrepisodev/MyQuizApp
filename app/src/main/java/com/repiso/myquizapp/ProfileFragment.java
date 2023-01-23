package com.repiso.myquizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileFragment extends Fragment {

    private EditText et_name, et_email, et_password, et_password_confirm;
    private Button btn_modificar;
    private String name, email, pass, repass, rol;
    private int userID;
    private DBHelper dbHelper;
    private SessionManager sessionManager;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        et_name=(EditText) view.findViewById(R.id.et_name_perfil);
        et_email=(EditText) view.findViewById(R.id.et_email_perfil);
        et_password=(EditText) view.findViewById(R.id.et_pass_perfil);
        et_password_confirm=(EditText) view.findViewById(R.id.et_pass_confirm_perfil);
        btn_modificar=(Button) view.findViewById(R.id.btn_modificar);

        //Inicia sesión de usuario. Recupera la sesión de usuario
        sessionManager = new SessionManager(getContext());
        name=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();
        pass=sessionManager.getUserPassword();
        rol=sessionManager.getUserRol();

        et_name.setText(name);
        et_password.setText(pass);
        et_password_confirm.setText(pass);
        et_email.setText(email);

        dbHelper=DBHelper.getInstance(getContext());

        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //conseguimos el texto de los campos
                name= et_name.getText().toString().trim();
                email= et_email.getText().toString().trim();
                pass= et_password.getText().toString().trim();
                repass= et_password_confirm.getText().toString().trim();

                //Comprueba que todos los campos están rellenos
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(email)){
                    Toast.makeText(getContext(),"Existen campos vacíos. Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
                }else{

                    //Validamos los datos
                    if(validarUserName(name) && pass.equals(repass) && validarEmail(email)){ //comprueba que ambas contraseñas coinciden, el email es válido y se ha aceptado las condiciones

                        //Comprueba si no existe el email
                        if(dbHelper.checkUserEmail(email)==false){

                            if(dbHelper.updateUser(userID,name, email, pass, rol)>0){
                                Toast.makeText(getContext(),"Actualizado correctamente",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(),"Error: No ha sido posible registrar la cuenta",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getContext(),"Error: Ya existe un usuario con esos datos. Registre otro email",Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        if(!validarUserName(name)){
                            et_name.setError("El nombre solo puede contener caracteres alfanuméricos y contener de 4 a 16 caracteres");
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

        return view;
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

}