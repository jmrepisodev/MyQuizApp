package com.repiso.myquizapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    // Shared preferences file name
    public static final String SHARED_PREFS_SESION = "SharedPreferenceSession";
    public static final String KEY_ID_USER ="keyUserID";
    public static final String KEY_NAME_USER ="keyUserName";
    public static final String KEY_IMAGE_USER ="keyUserImage";
    public static final String KEY_EMAIL_USER ="keyUserEmail";
    public static final String KEY_IS_LOGIN = "keyLogin";
    public static final String KEY_PASSWORD = "keyPassword";
    public static final String KEY_FIRST_TIME = "keyFirstTime";
    private static final String KEY_ROL_USER = "KeyRolUser";

    private static final String KEY_HIGHSCORE="keyHighScore";


    private Context context;

    // Shared Preferences
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Método constructor
     * @param context
     */
    public SessionManager(Context context) {

        this.context = context;
    }

    /**
     * Establece el estado del login
     * @param login estado login
     */
    public void setLogin (boolean login){

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGIN,login);
        editor.apply();
    }

    /**
     * Obtiene el estado del login
     * @return estado login
     */
    public boolean getLogin(){
        Boolean isLogin;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        isLogin= sharedPreferences.getBoolean(KEY_IS_LOGIN, false);

        return isLogin ;
    }

    /**
     * Establece que es la primera vez
     * @param firstTime primera vez
     */
    public void setFirstTime (boolean firstTime){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_TIME,firstTime);
        editor.apply();
    }

    /**
     * Obtiene si es la primera vez que se conecta
     * @return estado primera vez
     */
    public boolean getFirstTime(){
        Boolean firstTime;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        firstTime= sharedPreferences.getBoolean(KEY_FIRST_TIME, true);

        return firstTime;
    }


    /**
     * Establece el nombre del usuario ha iniciado sesión
     * @param name
     */
    public void setUserName(String name){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_NAME_USER,name);
        editor.apply();
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión
     * @return
     */
    public String getUserName (){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        String name=sharedPreferences.getString(KEY_NAME_USER,null);

        return name;
    }

    /**
     * Establece el nombre del usuario ha iniciado sesión
     * @param email
     */
    public void setUserEmail(String email){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL_USER,email);
        editor.apply();
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión
     * @return
     */
    public String getUserEmail (){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        String email=sharedPreferences.getString(KEY_EMAIL_USER,null);

        return email;
    }

    /**
     * Establece el nombre del usuario ha iniciado sesión
     * @param email
     */
    public void setUserPassword(String email){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD,email);
        editor.apply();
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión
     * @return
     */
    public String getUserPassword (){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        String password=sharedPreferences.getString(KEY_PASSWORD,null);

        return password;
    }

    /**
     * Establece el nombre del usuario ha iniciado sesión
     * @param id ID usuario
     */
    public void setUserId(int id){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_USER,id);
        editor.apply();
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión
     * @return
     */
    public int getUserId (){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        int id=sharedPreferences.getInt(KEY_ID_USER,0);

        return id;
    }

    /**
     * Establece el rol del usuario ha iniciado sesión
     * @param rol Rol usuario
     */
    public void setUserRol(String rol){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_ROL_USER,rol);
        editor.apply();
    }

    /**
     * Obtiene el rol del usuario que ha iniciado sesión
     * @return
     */
    public String getUserRol (){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        String rol =sharedPreferences.getString(KEY_ROL_USER,null);

        return rol;
    }

    /**
     * Establece la imagen del usuario ha iniciado sesión
     * @param idImg ruta de la imagen
     */
    public void setUserImage(int idImg){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_IMAGE_USER,idImg);
        editor.apply();
    }

    /**
     * Obtiene la imagen del usuario que ha iniciado sesión
     * @return Devuelve la ruta de la imagen
     */
    public int getUserImage (){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        int idImg=sharedPreferences.getInt(KEY_IMAGE_USER,0);

        return idImg;
    }


    /**
     * Establece el estado del login
     * @param highScore estado login
     */
    public void setHighScore (int highScore){

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();
    }

    /**
     * Obtiene el estado del login
     * @return estado login
     */
    public int getHighScore(){
        int highScore;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        highScore= sharedPreferences.getInt(KEY_HIGHSCORE, 0);

        return highScore ;
    }


    /**
     * Cierra la sesión de usuario. Elimina todos los datos almacenados en SharedPreferences
     */
    public void logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_SESION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}

