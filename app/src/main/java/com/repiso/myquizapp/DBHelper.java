package com.repiso.myquizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Clase que gestiona la creación y actualización de las bases de datos.
 * Abre automaticamene la base de datos si ya existe o crea una nueva base de datos si no existe.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyQuizzDatabase.db";
    private SQLiteDatabase database;
    private static DBHelper dbHelper;


    private static final String CREAR_USERS_TABLE="CREATE TABLE USERS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT," +
            "PASSWORD TEXT," +
            "EMAIL TEXT," +
            "ROL TEXT" +
            ")";


    private static final String CREAR_CATEGORY_TABLE="CREATE TABLE CATEGORY (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT," +
            "IMAGE_CATEGORY INTEGER" +
            ")";

    private static final String CREAR_QUESTIONS_TABLE="CREATE TABLE QUESTIONS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "QUESTION TEXT," +
            "OPTION1 TEXT," +
            "OPTION2 TEXT," +
            "OPTION3 TEXT," +
            "OPTION4 TEXT," +
            "ANSWER INTEGER," +
            "IMAGE INTEGER," +
            "LEVEL TEXT," +
            "ID_CATEGORY INTEGER," +
            "FOREIGN KEY(ID_CATEGORY) REFERENCES CATEGORY (ID)"+ "ON DELETE CASCADE" +
            ")";


    private static final String ELIMINAR_USERS_TABLE ="DROP TABLE IF EXISTS USERS";
    private static final String ELIMINAR_CATEGORY_TABLE ="DROP TABLE IF EXISTS CATEGORY" ;
    private static final String ELIMINAR_QUESTIONS_TABLE ="DROP TABLE IF EXISTS QUESTIONS" ;



    private static final String CREAR_RESULTADOS_TABLE="CREATE TABLE RESULTADOS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ID_USER INTEGER," +
            "ID_CATEGORY INTEGER," +
            "ACIERTOS INTEGER," +
            "FALLOS INTEGER," +
            "ENBLANCO INTEGER," +
            "SCORE INTEGER," +
            "FOREIGN KEY(ID_USER) REFERENCES USERS(ID)"+ "ON DELETE CASCADE," +
            "FOREIGN KEY(ID_CATEGORY) REFERENCES CATEGORY(ID)"+ "ON DELETE CASCADE" +
            ")";


    private static final String ELIMINAR_RESULTADOS_TABLE ="DROP TABLE IF EXISTS RESULTADOS" ;


    /**************************************************************************************************/

    /**
     * Método constructor: contexto, nombre base datos, cursorFactory, versión base de datos.
     * @param context
     */
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
    }

    /**
     * crea la base de datos por primera vez.
     * @param database nombre de la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        this.database=database;

        database.execSQL(CREAR_USERS_TABLE);
        database.execSQL(CREAR_CATEGORY_TABLE);
        database.execSQL(CREAR_QUESTIONS_TABLE);
        database.execSQL(CREAR_RESULTADOS_TABLE);

        //Llenamos la base de datos con datos de prueba
        fillUsuariosTable(Utilidades.getUsuarioList());
        fillCategoryTable(Utilidades.getCategoryList());
        fillQuestionTable(Utilidades.getQuestionList());
        fillResultadoList(Utilidades.getResultadoList());


    }

    /**
     * Actualiza la base de datos a una versión posterior
     * @param database base de datos
     * @param oldVersion version Antigua
     * @param newVersion versión nueva
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        database.execSQL(ELIMINAR_RESULTADOS_TABLE);
        database.execSQL(ELIMINAR_USERS_TABLE);
        database.execSQL(ELIMINAR_QUESTIONS_TABLE);
        database.execSQL(ELIMINAR_CATEGORY_TABLE);

        //Se crea la nueva versión de la tabla
        onCreate(database);

    }

    /**
     * Habilita restricciones de clave foránea
     * @param database base de datos
     */
    @Override
    public void onConfigure(SQLiteDatabase database) {
        super.onConfigure(database);
        database.setForeignKeyConstraintsEnabled(true);
    }


    /**
     * Administra las conexiones simultáneas a la base de datos.
     * Garantiza que solo exista una instancia de DBHelper en un momento dado.
     * @param context Contexto de aplicación
     * @return Objeto de la clase auxiliar SQlite
     */
    public static synchronized DBHelper getInstance(Context context) {
        //Consigue el contexto de aplicación del contexto de la actividad
        if (dbHelper == null) {
            dbHelper= new DBHelper(context);
        }
        return dbHelper;
    }

    /**
     * Actualiza la base de datos a una versión anterior
     * @param database base de datos
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }


    /*******************************************************************************************/

    /****** USUARIOS ******/


    /**
     * Inserta una lista de usuarios de prueba en la base de datos
     * @param usuariosArrayList
     */
    public void fillUsuariosTable(ArrayList<Usuario> usuariosArrayList){
        for (Usuario usuario: usuariosArrayList) {
            insertUsuario(usuario);
        }
    }

    /**
     * Inserta una nueva pregunta en la tabla Question de la base de datos.
     * Parte de una conexión abierta de la base de datos. Evita llamar recursivamente
     */
    public void insertUsuario(Usuario usuario) {

        //Instancia un objeto ContentValues, almacena una fila con pares columna-valor
        ContentValues values = new ContentValues();

        values.put("NAME", usuario.getName());
        values.put("PASSWORD", usuario.getPassword());
        values.put("EMAIL",usuario.getEmail());
        values.put("ROL", usuario.getRol());

        database.insert("USERS", null, values);

    }




    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param usuario usuario
     * @return Devuelve estado de la operación
     */
    protected Boolean addUser(Usuario usuario) {
        //Abrimos la base de datos en modo escritura
        database=getWritableDatabase();

        //Instancia un objeto ContentValues, almacena una fila con pares columna-valor
        ContentValues values = new ContentValues();

        values.put("NAME", usuario.getName());
        values.put("PASSWORD", usuario.getPassword());
        values.put("EMAIL",usuario.getEmail());
        values.put("ROL", usuario.getRol());

        //Inserta nuevo registro. Si da error devuelve -1
        long result = -1;
        if (database.isOpen()) {
            result = database.insert("USERS", null, values);
        }

        //Cierra la base de datos
        if(database !=null){
            database.close();}

        if(result!=-1) return true;
        else
            return false;

    }

    /**
     * Devuelve un objeto completo de la clase Usuario, según ID
     * @param id ID usuario
     * @return usuario
     */
    protected Usuario getUserbyID(int id){
        Usuario user=null;
        //Abrimos en modo lectura la base de datos
        database = getReadableDatabase();

        //Si la base de datos está abierta.
        if(database.isOpen()) {
            String sql="SELECT * FROM USERS WHERE ID=?";
            Cursor cursor= database.rawQuery(sql,new String[] {String.valueOf(id)});

            //si la base de datos tiene algún registro
            if (cursor != null && cursor.getCount() > 0) {
                //mientras la base de datos tenga registros, lo almacenamos en un cursor y lo agregamos al Array de usuarios
                while (cursor.moveToNext()){
                    user=new Usuario();
                    user.setId(cursor.getInt(0));
                    user.setName(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    user.setEmail(cursor.getString(3));
                    user.setRol(cursor.getString(4));
                }
                //cerramos el cursor
                cursor.close();
            }
            //Cerramos la base de datos
            database.close();
        }

        return user;
    }


    /**
     * Elimina un usuario de la tabla Usuarios de la base de datos
     * @param id ID de usuario
     * @return Devuelve el número de filas eliminadas
     */
    protected int deleteUser(int id){
        String[] parametro={String.valueOf(id)};
        database =getWritableDatabase();
        int result=0;
        if(database.isOpen()){
            result= database.delete("USERS","ID=?",parametro);
        }

        if(database !=null){
            database.close();}

        return result;
    }



    /**
     * Actualiza la información de usuario, según parámetros. Devuelve el número de registros actualizados
     * @param id id usuario
     * @param name nombre
     * @param password nombre
     * @param email email
     * @param rol rol
     * @return número de filas afectadas
     * @exception SQLException
     */
    public int updateUser(int id, String name, String password, String email, String rol) {

        int result=0;
        String sqlUpdate= "ID=?";

        //Array de parámetros
        String[] parametros = new String[] {String.valueOf(id)};

        database =getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("NAME", name);
        values.put("PASSWORD", password);
        values.put("EMAIL",email);
        values.put("ROL", rol);

        if(database.isOpen()){
            result= database.update("USERS", values, sqlUpdate, parametros);
        }

        if(database !=null) database.close();

        return result;
    }


    /**
     * Comprueba si existe el email de usuario en la base de datos. Evita nombres duplicados
     * @param email email usuario
     * @return Devuelve true si ya existe
     */
    protected Boolean checkUserEmail(String email){

        Boolean exists=false;
        database =this.getWritableDatabase();

        //Consultamos si existe un usuario con ese email en la tabla usuario
        Cursor cursor= database.rawQuery("SELECT * FROM USERS WHERE EMAIL=?",new String[] {email});

        //Si la base de datos contiene algún registro devuelve true
        if(cursor != null && cursor.getCount()>0) {
            exists=true;
        } else{
            exists=false;
        }

        //cerramos los recursos
        if(cursor!=null) {
            cursor.close();}
        if(database !=null){
            database.close();}

        return exists;

    }

    /**
     * Comprueba si la contraseña del usuario es válida.
     * Comprueba si existe un par nombre-password en la base de datos.
     * @param email email usuario
     * @param password contraseña
     * @return Devuelve un objeto de usuario si los datos de autenticación son correctos
     */

    @SuppressLint("Range")
    protected Usuario checkPasswordUser(String email, String password){
        Usuario user=null;
        database =getReadableDatabase();

        //Comprueba si la contraseña coincide con la contraseña almacenado en el par usuario-contraseña de la base de datos
        Cursor cursor= database.rawQuery("SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=?",new String[] {email, password});
        //Si la base de datos contiene registro devuelve true
        if(cursor != null && cursor.getCount()>0) {
            if (cursor.moveToFirst()){
                user=new Usuario();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setRol(cursor.getString(4));
            }
        }

        //cerramos los recursos
        if(cursor!=null) {
            cursor.close();}
        if(database !=null){
            database.close();}

        return user;
    }



    /*******************************************************************************************/


    /****** PREGUNTAS ******/


    /**
     * Inserta una lista de categorías en la base de datos
     * @param categoryArrayList
     */
    public void fillCategoryTable(ArrayList<Category> categoryArrayList){
        for (Category category: categoryArrayList) {
            insertCategory(category);
        }
    }

    /**
     * Inserta una nueva categoría en la tabla CATEGORIAS de la base de datos.
     * Parte de una conexión abierta de la base de datos. Evita llamar recursivamente
     */
    public void insertCategory(Category category) {

        //Instancia un objeto ContentValues, almacena una fila con pares columna-valor
        ContentValues values = new ContentValues();

        values.put("ID", category.getId());
        values.put("NAME", category.getName());
        values.put("IMAGE_CATEGORY", category.getImage());


        database.insert("CATEGORY", null, values);

    }


    /**
     * Devuelve una lista de categorías
     * @return lista categorías
     */
    @SuppressLint("Range")
    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> categoryList = new ArrayList<>();
        database = getReadableDatabase();

        if(database.isOpen()){

            Cursor cursor = database.rawQuery("SELECT * FROM CATEGORY", null);

            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                    category.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                    category.setImage(cursor.getInt(cursor.getColumnIndex("IMAGE_CATEGORY")));
                    categoryList.add(category);
                } while (cursor.moveToNext());
            }

            //cerramos el cursor
            if(cursor!=null) cursor.close();
            //cerramos la base de datos
            if(database!=null) database.close();

        }


        return categoryList;
    }


    /**
     * Inserta una lista de preguntas de prueba en la base de datos
     * @param questionArrayList
     */
    public void fillQuestionTable(ArrayList<Question> questionArrayList){
        for (Question question: questionArrayList) {
            insertQuestion(question);
        }
    }

    /**
     * Inserta una nueva pregunta en la tabla Question de la base de datos.
     * Parte de una conexión abierta de la base de datos. Evita llamar recursivamente
     */
    public void insertQuestion(Question question) {

        //Instancia un objeto ContentValues, almacena una fila con pares columna-valor
        ContentValues values = new ContentValues();

        values.put("QUESTION", question.getQuestion());
        values.put("OPTION1", question.getOption1());
        values.put("OPTION2", question.getOption2());
        values.put("OPTION3", question.getOption3());
        values.put("OPTION4", question.getOption4());
        values.put("ANSWER", question.getAnswer());
        values.put("IMAGE", question.getImage());
        values.put("LEVEL", question.getDifficulty());
        values.put("ID_CATEGORY", question.getCategoryID());

        database.insert("QUESTIONS", null, values);

    }

    /**
     * Inserta una nueva pregunta en la tabla Question de la base de datos.
     * Abre una conexión a la base de datos
     */
    public void addQuestion(Question question) {
        database = getWritableDatabase();

        //Inserta una nueva pregunta
        if (database.isOpen()) {
            insertQuestion(question);
        }
        //cierra la conexión
        if(database !=null){
            database.close();
        }

    }

    /**
     * Agrega una lista de preguntas. Abre una conexión a la base de datos.
     * @param questionsList lista de preguntas
     */
    public void addQuestionList(ArrayList<Question> questionsList) {
        database = getWritableDatabase();

        //Inserta una lista preguntas
        if (database.isOpen()) {
            for (Question question : questionsList) {
                insertQuestion(question);
            }
        }
        //cierra la conexión
        if(database !=null){
            database.close();
        }

    }


    /**
     * Elimina una pregunta del cuestionario
     * @param id id
     */
    public void deleteQuestion(String id){
        String[] parametro={id};
        database =getWritableDatabase();

        if(database.isOpen()){
            database.delete("questions","id",parametro);
        }
        if(database !=null){
            database.close();
        }
    }




    /**
     * Obtiene la lista completa de preguntas
     * @return Lista de preguntas
     */
    @SuppressLint("Range")
    public ArrayList<Question> getQuestionList() {
        ArrayList<Question> questionsList = new ArrayList<Question>();
        Question question=null;
        //abre la base de datos, en modo lectura
        database = getReadableDatabase();

        //Si la base de datos está abierta.
        if(database.isOpen()) {
            //realizamos la consulta
            Cursor cursor = database.rawQuery("SELECT * FROM QUESTIONS", null);
            //si la base de datos tiene algún registro
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) { //si hay registros
                    do {
                        question = new Question();
                        // ¡¡OJO!! Distingue mayúsculas y minúsculas
                        question.setQuestion(cursor.getString(cursor.getColumnIndex("QUESTION")));
                        question.setOption1(cursor.getString(cursor.getColumnIndex("OPTION1")));
                        question.setOption2(cursor.getString(cursor.getColumnIndex("OPTION2")));
                        question.setOption3(cursor.getString(cursor.getColumnIndex("OPTION3")));
                        question.setOption4(cursor.getString(cursor.getColumnIndex("OPTION4")));
                        question.setAnswer(cursor.getInt(cursor.getColumnIndex("ANSWER")));
                        question.setImage(cursor.getInt(cursor.getColumnIndex("IMAGE")));
                        question.setDifficulty(cursor.getString(cursor.getColumnIndex("LEVEL")));
                        question.setCategoryID(cursor.getInt(cursor.getColumnIndex("ID_CATEGORY")));

                        questionsList.add(question);

                    } while (cursor.moveToNext()); //mientras la base de datos tenga registros, lo almacenamos en un cursor y lo agregamos al Array de preguntas
                }
                //cerramos el cursor
                if(cursor!=null) cursor.close();
            }
            //cierra la conexión
            if(database !=null){
                database.close();
            }
        }

        return questionsList;
    }


    /**
     * Obtiene la lista de preguntas, según su categoría y nivel de dificultad
     * @param level Nivel de dificultad
     * @param category Categoría o área temática
     * @return
     */
    @SuppressLint("Range")
    public ArrayList<Question> getQuestionList(String level, int category) {
        ArrayList<Question> questionsList = new ArrayList<>();
        //abre la base de datos, en modo lectura
        database = getReadableDatabase();

        //Si la base de datos está abierta.
        if(database.isOpen()) {
            //Array de parámetros
            String[] parametros = new String[] {level, String.valueOf(category)};
            //Argumentos de selección
            String args="LEVEL=? AND ID_CATEGORY=?";
            Cursor cursor;

            //Opción 1: Realiza una consulta con los parámetros seleccionados
            cursor= database.rawQuery("SELECT * FROM QUESTIONS WHERE LEVEL=? AND ID_CATEGORY=?",parametros);

            //Opción 2: consulta con parámetros
            // cursor = db.query("questions", null, args, parametros, null, null, null);

            //si la base de datos tiene algún registro
            if (cursor != null && cursor.getCount() > 0) {
                //mientras la base de datos tenga registros
                if (cursor.moveToFirst()) { //si hay registros
                    do {
                        Question question = new Question();
                        // ¡¡OJO!! Distingue mayúsculas y minúsculas
                        question.setQuestion(cursor.getString(cursor.getColumnIndex("QUESTION")));
                        question.setOption1(cursor.getString(cursor.getColumnIndex("OPTION1")));
                        question.setOption2(cursor.getString(cursor.getColumnIndex("OPTION2")));
                        question.setOption3(cursor.getString(cursor.getColumnIndex("OPTION3")));
                        question.setOption4(cursor.getString(cursor.getColumnIndex("OPTION4")));
                        question.setAnswer(cursor.getInt(cursor.getColumnIndex("ANSWER")));
                        question.setImage(cursor.getInt(cursor.getColumnIndex("IMAGE")));
                        question.setDifficulty(cursor.getString(cursor.getColumnIndex("LEVEL")));
                        question.setCategoryID(cursor.getInt(cursor.getColumnIndex("ID_CATEGORY")));

                        questionsList.add(question);
                    } while (cursor.moveToNext()); //mientras haya registros
                }
                //cerramos el cursor
                cursor.close();
            }
            //cerramos la base de datos
            database.close();
        }

        return questionsList;
    }





    /*******************************************************************************************/


    /*******************************************************************************************/


    /****** RESULTADOS ******/

    /**
     * Agrega una lista de resultados de prueba. Parte de una conexión abierta.
     * @param resultadoArrayList lista de partidas
     */
    private void fillResultadoList(ArrayList<Resultado> resultadoArrayList) {

        for (Resultado resultado : resultadoArrayList) {
            insertResultado(resultado);

        }
    }


    /**
     * Inserta una nueva partida en la tabla Partidas de la base de datos. Parte de una conexión abierta
     */
    public void insertResultado(Resultado resultado) {

        //Instancia un objeto ContentValues, almacena una fila con pares columna-valor
        ContentValues values = new ContentValues();

        values.put("ID_USER", resultado.getUserID());
        values.put("ID_CATEGORY", resultado.getCategoryID());
        values.put("ACIERTOS", resultado.getAciertos());
        values.put("FALLOS", resultado.getFallos());
        values.put("ENBLANCO", resultado.getEnBlanco());
        values.put("SCORE", resultado.getScore());

        database.insert("RESULTADOS",null, values);

    }

    /**
     * Agrega un resultado a la base de datos. Abre una conexión a la base de datos e inserta un registro
     * @param resultado
     */
    public Boolean addResultado(Resultado resultado){
        database=getWritableDatabase();

        //Instancia un objeto ContentValues, almacena una fila con pares columna-valor
        ContentValues values = new ContentValues();

        values.put("ID_USER", resultado.getUserID());
        values.put("ID_CATEGORY", resultado.getCategoryID());
        values.put("ACIERTOS", resultado.getAciertos());
        values.put("FALLOS", resultado.getFallos());
        values.put("ENBLANCO", resultado.getEnBlanco());
        values.put("SCORE", resultado.getScore());

        //Inserta nuevo registro. Si da error devuelve -1
        long result = -1;
        if (database.isOpen()) {
            result = database.insert("RESULTADOS",null, values);
        }

        if(database !=null){
            database.close();
        }

        if(result!=-1) return true;
        else
            return false;
    }

    /**
     * Elimina una partida de la base de datos
     * @param id ID de partida
     * @return Devuelve el número de filas eliminadas
     */
    public Boolean deleteResultado(int id){
        String[] parametro={String.valueOf(id)};
        database =getWritableDatabase();
        int result=0;
        if(database.isOpen()){
            result= database.delete("RESULTADOS","ID=?",parametro);
        }

        if(database !=null){
            database.close();}

        if (result != 0) return true;
        else
            return false;
    }



   /************************************************************************/
   /**  RESULTADOS RANKING */

    /**
     * Obtiene el ranking de usuarios según la categoría seleccionada
     * @return
     */
    @SuppressLint("Range")
    public ArrayList<Ranking> getRankingList() {
        ArrayList<Ranking> rankingList = new ArrayList<>();
        //abre la base de datos, en modo lectura
        database = getReadableDatabase();

        //Si la base de datos está abierta.
        if(database.isOpen()) {

            Cursor cursor;

            //Opción 1: Realiza una consulta con los parámetros seleccionados
            cursor= database.rawQuery("SELECT USERS.NAME, RESULTADOS.SCORE FROM USERS, RESULTADOS WHERE USERS.ID=RESULTADOS.ID_USER ORDER BY SCORE DESC",null);

            //Opción 2: consulta con parámetros
            // cursor = db.query("tabla", null, args, params, null, null, null);

            //si la base de datos tiene algún registro
            if (cursor != null && cursor.getCount() > 0) {
                //mientras la base de datos tenga registros
                if (cursor.moveToFirst()) { //si hay registros
                    do {
                        Ranking ranking=new Ranking();
                        // ¡¡OJO!! Distingue mayúsculas y minúsculas
                        ranking.setUsername(cursor.getString(cursor.getColumnIndex("NAME")));
                        ranking.setScore(cursor.getInt(cursor.getColumnIndex("SCORE")));

                        rankingList.add(ranking);
                    } while (cursor.moveToNext()); //mientras haya registros
                }
                //cerramos el cursor
                cursor.close();
            }
            //cerramos la base de datos
            database.close();
        }

        return rankingList;
    }

    /**
     * Obtiene el ranking de usuarios según la categoría seleccionada
     * @return
     */
    @SuppressLint("Range")
    public ArrayList<Ranking> getRankingListByCategory (String categoria) {
        ArrayList<Ranking> rankingList = new ArrayList<>();
        Category category = null;
        //abre la base de datos, en modo lectura
        database = getReadableDatabase();

        //Si la base de datos está abierta.
        if(database.isOpen()) {
            String sql="SELECT * FROM CATEGORY WHERE NAME=?";
            Cursor cursor1= database.rawQuery(sql,new String[] {categoria});

            //si la base de datos tiene algún registro
            if (cursor1 != null && cursor1.getCount() ==1) {
                //mientras la base de datos tenga registros, lo almacenamos en un cursor y lo agregamos al Array de usuarios
                if (cursor1.moveToFirst()){
                    category=new Category();
                    category.setId(cursor1.getInt(0));
                    category.setName(cursor1.getString(1));
                    category.setImage(cursor1.getInt(2));
                }
                //cerramos el cursor
                cursor1.close();

                if(category!=null){

                    //Opción 1: Realiza una consulta con los parámetros seleccionados
                    Cursor cursor2= database.rawQuery("SELECT USERS.NAME, RESULTADOS.SCORE FROM USERS, RESULTADOS WHERE USERS.ID=RESULTADOS.ID_USER " +
                            "AND ID_CATEGORY=? ORDER BY SCORE DESC",new String[] {String.valueOf(category.getId())});
                    //si la base de datos tiene algún registro
                    if (cursor2 != null && cursor2.getCount() > 0) {
                        //mientras la base de datos tenga registros
                        if (cursor2.moveToFirst()) { //si hay registros
                            do {
                                Ranking ranking=new Ranking();
                                // ¡¡OJO!! Distingue mayúsculas y minúsculas
                                ranking.setUsername(cursor2.getString(cursor2.getColumnIndex("NAME")));
                                ranking.setScore(cursor2.getInt(cursor2.getColumnIndex("SCORE")));

                                rankingList.add(ranking);
                            } while (cursor2.moveToNext()); //mientras haya registros
                        }
                        //cerramos el cursor
                        cursor2.close();
                    }
                }


            }
            //Cerramos la base de datos
            database.close();
        }


        return rankingList;
    }



}
