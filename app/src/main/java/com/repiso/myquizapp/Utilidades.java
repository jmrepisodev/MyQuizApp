package com.repiso.myquizapp;

import java.util.ArrayList;

public class Utilidades {


    /**
     * Obtiene una lista de categorías
     * @return lista de categorías
     */
    public static ArrayList<Usuario> getUsuarioList() {
        ArrayList usuarioList=new ArrayList<Usuario>();

        Usuario usuario1=new Usuario("Admin", "admin@gmail.com","1234", "admin");
        usuarioList.add(usuario1);

        Usuario usuario2=new Usuario("Jose", "jose@gmail.com","1234", "user");
        usuarioList.add(usuario2);

        Usuario usuario3=new Usuario("Maria", "maria@gmail.com","1234", "user");
        usuarioList.add(usuario3);

        Usuario usuario4=new Usuario("Elena", "elena@gmail.com","1234", "user");
        usuarioList.add(usuario4);


        return usuarioList;
    }

    /**
     * Obtiene una lista de categorías
     * @return lista de categorías
     */
    public static ArrayList<Category> getCategoryList() {
        ArrayList categoryList=new ArrayList<Category>();

        Category category1=new Category(1,"CIENCIAS", R.drawable.quizz);
        categoryList.add(category1);

        Category category2=new Category(2,"GEOGRAFIA", R.drawable.quizz);
        categoryList.add(category2);

        Category category3=new Category(3,"HISTORIA", R.drawable.quizz);
        categoryList.add(category3);

        Category category4=new Category(4,"ARTE Y LITERATURA",R.drawable.quizz);
        categoryList.add(category4);

        Category category5=new Category(3,"ESPECTÁCULOS", R.drawable.quizz);
        categoryList.add(category5);

        Category category6=new Category(4,"DEPORTES",R.drawable.quizz);
        categoryList.add(category6);


        return categoryList;
    }


    /**
     * Obtiene un array de niveles de dificultad
     * @return niveles
     */
    public static String[] getLevelsList() {
        return new String[]{ "FACIL" , "DIFICIL"};
    }


    /**
     * Rellena la tabla Questions de la base de datos
     */
    public static ArrayList<Question> getQuestionList() {
        ArrayList<Question> questionArrayList=new ArrayList<>();

        //Ciencias

        Question q1=new Question(1,"¿De qué color es el caballo blanco de santiago?",
                "blanco","rojo","negro","amarillo",1,0,Question.LEVEL_EASY, Question.CIENCIAS);
        questionArrayList.add(q1);

        Question q7=new Question(1,"¿Cuantas patas tiene un perro?",
                "tres","cuatro","muchas","amarillo",2,0,Question.LEVEL_EASY, Question.CIENCIAS);
        questionArrayList.add(q7);

        Question q9=new Question(1,"¿Como se llama el fruto del naranjo?",
                "naranja","rojo","bellota","amarillo",1,0,Question.LEVEL_EASY, Question.CIENCIAS);
        questionArrayList.add(q9);

        Question q10=new Question(1,"¿Cuántos patas tiene una araña?",
                "6","20","8","muchas",3,0,Question.LEVEL_EASY, Question.CIENCIAS);
        questionArrayList.add(q10);

        Question q11=new Question(1,"¿Cuanto es 4 + 4?",
                "8","20","50","muchas",1,0,Question.LEVEL_EASY, Question.CIENCIAS);

        questionArrayList.add(q11);


        Question q5=new Question(5,"¿Cuántos huesos hay en el cuerpo humano?",
                "206","600","20","Muchos",1,0,Question.LEVEL_HARD, Question.CIENCIAS);
        questionArrayList.add(q5);

        Question q8=new Question(1,"¿Qué velocidad alcanza un guepardo?",
                "80 km/h","rojo","50 km/h","amarillo",1,0,Question.LEVEL_HARD, Question.CIENCIAS);
        questionArrayList.add(q8);

        Question q12=new Question(1,"¿De que color es el jersey amarillo de Jaime?",
                "amarillo","rojo","50 km/h","amarillo",1,0,Question.LEVEL_HARD, Question.CIENCIAS);
        questionArrayList.add(q12);

        Question q13=new Question(1,"¿Que velocidad alcanza un patinete eléctrico?",
                "30 km/h","50 km/h","50 km/h","amarillo",1,0,Question.LEVEL_HARD, Question.CIENCIAS);
        questionArrayList.add(q13);

        Question q14=new Question(1,"¿Cuantas patas tiene una mesa?",
                "4","2","3","1",1,0,Question.LEVEL_HARD, Question.CIENCIAS);
        questionArrayList.add(q14);




        //Geografía

        Question q2=new Question(2,"¿Bandera?",
                "Argentina","Brasil","Alemania","Australia",4, R.drawable.ic_flag_of_australia,Question.LEVEL_EASY, Question.GEOGRAFIA);
        questionArrayList.add(q2);

        Question q4=new Question(4,"¿Bandera?",
                "Brasil","Australia","Bélgica","Alemania",1,R.drawable.ic_flag_of_brazil,Question.LEVEL_EASY, Question.GEOGRAFIA);
        questionArrayList.add(q4);

        Question q15=new Question(4,"¿Bandera?",
                "Dinamarca","Australia","Bélgica","Alemania",1,R.drawable.ic_flag_of_denmark,Question.LEVEL_EASY, Question.GEOGRAFIA);
        questionArrayList.add(q15);

        Question q16=new Question(4,"¿Bandera?",
                "Brasil","Australia","Bélgica","Alemania",4,R.drawable.ic_flag_of_germany,Question.LEVEL_EASY, Question.GEOGRAFIA);
        questionArrayList.add(q16);

        Question q17=new Question(4,"¿Bandera?",
                "Brasil","Australia","Kuwait","Alemania",3,R.drawable.ic_flag_of_kuwait,Question.LEVEL_EASY, Question.GEOGRAFIA);

        questionArrayList.add(q17);

        Question q21=new Question(2,"¿Bandera?",
                "Argentina","Brasil","Alemania","Fiji",4,R.drawable.ic_flag_of_fiji,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q21);

        Question q22=new Question(4,"¿Bandera?",
                "Brasil","Australia","Bélgica","India",3,R.drawable.ic_flag_of_belgium,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q22);

        Question q23=new Question(4,"¿Bandera?",
                "India","Australia","Bélgica","Alemania",1, R.drawable.ic_flag_of_india,Question.LEVEL_HARD, Question.GEOGRAFIA);

        questionArrayList.add(q23);
        Question q24=new Question(4,"¿Bandera?",
                "Brasil","Australia","Nueva Zelanda","Alemania",3,R.drawable.ic_flag_of_new_zealand,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q24);

        Question q25=new Question(4,"¿Bandera?",
                "Brasil","Argentina","Kuwait","Alemania",2,R.drawable.ic_flag_of_argentina, Question.LEVEL_HARD, Question.GEOGRAFIA);

        questionArrayList.add(q25);


        //Historia

        Question q3=new Question(3,"¿Cómo se llama el presidente de España?",
                "Mariano Rajoy","Pablo Iglesias","Pedro Sanchez","Irene Montero",3,0,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q3);

        Question q6=new Question(3,"¿Cómo se llama el presidente de USA?",
                "Mariano Rajoy","George Washington","Joe Biden","Trump",3,0,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q6);

        Question q18=new Question(3,"¿Cómo se llama el presidente de Francia?",
                "Mariano Rajoy","George Washington","François Macron","Trump",3,0,Question.LEVEL_HARD, Question.GEOGRAFIA);

        questionArrayList.add(q18);
        Question q19=new Question(3,"¿Cómo se llama el presidente de Reino Unido?",
                "Mariano Rajoy","Boris Jhonson","Joe Biden","Trump",2,0,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q19);

        Question q20=new Question(3,"¿Cómo se llama el presidente de Dinamarca?",
                "Mariano Rajoy","George Washington","Ninguna es correcta","Trump",3,0,Question.LEVEL_HARD, Question.GEOGRAFIA);
        questionArrayList.add(q20);

        return questionArrayList;
    }


    /**
     * Obtiene una lista de categorías
     * @return lista de categorías
     */
    public static ArrayList<Resultado> getResultadoList() {

        ArrayList resultadoList=new ArrayList<Resultado>();

        Resultado resultado1=new Resultado(2,1,3,1,1,30);
        resultadoList.add(resultado1);

        Resultado resultado2=new Resultado(3,1,2,2,1,20);
        resultadoList.add(resultado2);

        Resultado resultado3=new Resultado(4,2,1,3,1,10);
        resultadoList.add(resultado3);


        return resultadoList;
    }


}
