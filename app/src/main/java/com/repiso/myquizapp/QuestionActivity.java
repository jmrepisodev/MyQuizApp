package com.repiso.myquizapp;

import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_ID;
import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_NAME;
import static com.repiso.myquizapp.MainActivity.KEY_LEVEL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{


    private CountDownTimer timer;
    //tiempo de la cuenta atrás del contador, en milisegundos
    private long timeLeftInMillis;

    //Tiempo en milisegundos
    protected static final long TIME_IN_MILLIS = 20000;

    protected static final String KEY_SCORE = "keyScore";
    protected static final String KEY_ACIERTOS = "keyAciertos";
    protected static final String KEY_FALLOS = "keyFallos";
    protected static final String KEY_ENBLANCO = "keyEnBlanco";
    protected static final String KEY_TOTAL_QUESTIONS = "keyTotalQuestions";

    protected static final String KEY_COUNT = "keyCount";
    protected static final String KEY_CURRENT_POSITON = "keyCurrentPosition";
    protected static final String KEY_TIMELEFT = "keyTimeLeft";
    protected static final String KEY_QUESTION_LIST = "keyQuestionList";


    private int currentPosition=0;
    private int counter=0;
    private int selectedOptionPosition=0;
    private int aciertos =0;
    private int fallos=0;
    private int enBlanco=0;
    private int totalquestion=0;
    private int score=0;
    private Boolean respuesta=false;
    private int totalRespondidas=0;
    private Boolean goBack =false;

    private MediaPlayer mp;

    private SessionManager sessionManager;
    private DBHelper dbHelper;

    private String level;
    private String categoryName;
    private int categoryID;

    private String username,email;
    private int userID;

    private TextView tv_question, tv_questionCounter, tv_score, tv_timer, tv_level,tv_category;
    private Button btn_next, btn_quit;
    private TextView option1,option2,option3,option4;
    private ImageView img_quiz;

    private ArrayList<Question> questionList;
    private Question question;

    private Resultado resultado;

    private long backPressedTime;
    private boolean isSoundON;
    private boolean isVibrationON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Realizamos las distintas asociaciones
        img_quiz=(ImageView)findViewById(R.id.imageViewQuestion);
        btn_next=(Button)findViewById(R.id.btn_next);
        btn_quit=(Button)findViewById(R.id.btn_quit);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_category=(TextView)findViewById(R.id.tv_category_quizz);
        tv_level=(TextView)findViewById(R.id.tv_level_quizz);
        tv_questionCounter=(TextView)findViewById(R.id.tv_counter);
        tv_score=(TextView)findViewById(R.id.tv_score_quizz);
        tv_timer=(TextView)findViewById(R.id.tv_timer_quizz);

        option1=(TextView) findViewById(R.id.option_1);
        option2=(TextView)  findViewById(R.id.option_2);
        option3=(TextView)  findViewById(R.id.option_3);
        option4=(TextView)  findViewById(R.id.option_4);

        //Implementamos funcionalidad a los botones
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        btn_next.setOnClickListener(this);
        btn_quit.setOnClickListener(this);

        //Inicia sesión de usuario
        sessionManager = new SessionManager(getApplicationContext());
        username=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();

        //Recupera las preferencias de configuración
        Preferencias.getPreferences(this);
        isSoundON =Preferencias.sound;


        //Recuperamos los extras de la actividad anterior
        if(getIntent().getExtras()!=null){

            level =getIntent().getExtras().getString((KEY_LEVEL));
            categoryID =getIntent().getExtras().getInt((KEY_CATEGORY_ID));
            categoryName =getIntent().getExtras().getString((KEY_CATEGORY_NAME));

            tv_category.setText(categoryName);
            tv_level.setText(level);
        }


        //Si es la primera vez que se ejecuta la actividad
        if (savedInstanceState == null) {


            //Inicializa las bases de datos SQLite
            dbHelper=DBHelper.getInstance(this);

            if(questionList==null){
                questionList=new ArrayList<>();
            }

            //llenamos el ArrayList de preguntas
            questionList= dbHelper.getQuestionList(level, categoryID);

            //Alternativa: llenar lista de array
            //questionList= Utilidades.getQuestionList();

            totalquestion=questionList.size();

            //si no hay preguntas en la base de datos lanza mensaje de error
            if(totalquestion==0){
                mostrarAlerta("ATENCIÓN","No existen preguntas disponibles en esta categoría");

            }else{
                //permutamos aleatoriamente la lista de preguntas
                Collections.shuffle(questionList);

                //Reiniciamos el tiempo
                timeLeftInMillis = TIME_IN_MILLIS;
                //reiniciamos las opciones por defecto
                resetDefaultView();
                //Mostramos el texto de las preguntas en pantalla
                setQuestion();
            }


        }else {
            //Recuperamos la lista (Necesario implementar Parcelable o Serializable)
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);

            //Recuperamos el valor del resto de variables: total preguntas, posicíón, contador, nueva pregunta...
            totalquestion = questionList.size();
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITON);
            counter=savedInstanceState.getInt(KEY_COUNT);
            question=questionList.get(currentPosition);

            //total respuestas correctas
            aciertos =savedInstanceState.getInt(KEY_ACIERTOS);
            fallos =savedInstanceState.getInt(KEY_FALLOS);
            enBlanco =savedInstanceState.getInt(KEY_ENBLANCO);
            score = savedInstanceState.getInt(KEY_SCORE);

            //Restaura el tiempo restante para finalizar
            timeLeftInMillis = savedInstanceState.getLong(KEY_TIMELEFT);
            //Renicia el contador con el tiempo restante
            startTimer();

            //restaura la sesión y vuelve a mostrar la pregunta en pantalla
            showQuestion();


        }



    }


    /**
     * Establece una nueva pregunta
     */
    private void setQuestion() {
        //Reinicia el contador
        timeLeftInMillis = TIME_IN_MILLIS;
        respuesta=false;
        startTimer();

        //Texto del botón
        if (currentPosition <(totalquestion-1)) {
            btn_next.setText("SIGUIENTE");
        }else {
            btn_next.setText("FINALIZAR");
        }

        //Si no se ha llegado al final de la ronda de preguntas
        if(currentPosition<totalquestion){

            //nueva pregunta
            question=questionList.get(currentPosition);
            //actualizamos el contador
            counter++;
            //mostramos la pregunta en pantalla

            tv_questionCounter.setText((counter)+"/"+totalquestion);
            tv_score.setText(String.valueOf(score));

            //actualizamos el texto de la pregunta y respuestas
            tv_question.setText(question.getQuestion());
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());

            //si la pregunta contiene imágenes, la hacemos visible
            if(question.getImage()!=0){
                img_quiz.setVisibility(View.VISIBLE);
                img_quiz.setImageResource(question.getImage());
            }


        }else{
            finishQuiz();

        }

    }

    /**
     * Muestra la pregunta de la posición actual
     */
    private void showQuestion(){

        tv_questionCounter.setText((counter)+"/"+totalquestion);
        tv_score.setText(String.valueOf(score));

        //actualizamos el texto de la pregunta y respuestas
        tv_question.setText(question.getQuestion());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());

        //si la pregunta contiene imágenes, la hacemos visible
        if(question.getImage()!=0){
            img_quiz.setVisibility(View.VISIBLE);
            img_quiz.setImageResource(question.getImage());
        }

    }

    /**
     * Finaliza y se dirige hacia la pantalla de resultado
     */
    private void finishQuiz() {

        //Calcula las preguntas en blanco
        enBlanco=totalquestion-(fallos+aciertos);
/*
        //Actualiza la base de datos
        resultado=new Resultado(userID,categoryID,aciertos,fallos,enBlanco,score);
        Boolean registrado=dbHelper.addResultado(resultado);

        if(registrado) Toast.makeText(getApplicationContext(), "Resultado registrado satisfactoriamente", Toast.LENGTH_SHORT).show();
*/
        Intent intent=new Intent(getApplicationContext(),ResultadoActivity.class);

        intent.putExtra(KEY_ACIERTOS, aciertos);
        intent.putExtra(KEY_FALLOS, fallos);
        intent.putExtra(KEY_ENBLANCO, enBlanco);
        intent.putExtra(KEY_TOTAL_QUESTIONS,totalquestion);
        intent.putExtra(KEY_SCORE,score);
        intent.putExtra(KEY_CATEGORY_ID, categoryID);
        intent.putExtra(KEY_CATEGORY_NAME, categoryName);
        intent.putExtra(KEY_LEVEL, level);
        startActivity(intent);

        finish();
    }

    /**
     * Reinicia las opciones de respuesta
     */
    private void resetDefaultView() {
        //reiniciamos las opciones de respuesta y las vistas por defefecto
        selectedOptionPosition=0;
        option1.setBackground(getDrawable(R.drawable.option_unselected));
        option2.setBackground(getDrawable(R.drawable.option_unselected));
        option3.setBackground(getDrawable(R.drawable.option_unselected));
        option4.setBackground(getDrawable(R.drawable.option_unselected));
       // img_quiz.setVisibility(View.GONE);
    }


    /**
     * Establece la posición de la opción seleccionada
     * @param option opcion seleccionada
     * @param positionSelected posicion de la opción seleccionada
     */
    private void selectedOptionView(TextView option, int positionSelected){
        this.selectedOptionPosition=positionSelected;
    }

    /**
     * Administra las funciones de los botones
     * @param view
     */
    public void onClick(View view) {
        switch(view.getId()){
            //Si presiona cualquier opción se comprueba las respuesta
            case R.id.option_1:
                if(respuesta==false){
                    selectedOptionView(option1,1);
                    checkAnswer();
                    respuesta=true;}
                break;
            case R.id.option_2:
                if(respuesta==false){
                    selectedOptionView(option2,2);
                    checkAnswer();
                    respuesta=true;}
                break;
            case R.id.option_3:
                if(respuesta==false){
                    selectedOptionView(option3,3);
                    checkAnswer();
                    respuesta=true;}
                break;
            case R.id.option_4:
                if(respuesta==false){
                    selectedOptionView(option4,4);
                    checkAnswer();
                    respuesta=true;}
                break;
            case R.id.btn_next:
                //Detiene el contador
                if(timer!=null) timer.cancel();

                if (currentPosition <=(totalquestion)) {
                    //Pasamos a la siguiente pregunta
                    currentPosition++;
                    //reiniciamos las opciones por defecto
                    resetDefaultView();
                    //Establece la siguiente pregunta
                    setQuestion();
                } else {
                    //Finaliza el cuestionario
                    finishQuiz();
                }
                break;
            case R.id.btn_quit:
                mostrarAlertaFinalizar();
                break;
        }

    }


    /**
     * Comprueba la respuesta seleccionada
     */
    private void checkAnswer(){
        totalRespondidas++;
        //Consigue el índice o posición de la pregunta

        question=questionList.get(currentPosition);

        //paramos el contador
        if(timer!=null)
            timer.cancel();

        //si no es correcta, mostramos el error
        if(question.getAnswer()!=selectedOptionPosition){
            //si el sonido está activado, reproduce un sonidp
            if(isSoundON ==true){
                //reproduce un sonido
                mp = MediaPlayer.create(this, R.raw.fail);
                mp.start();
            }
            fallos++;
            //muestra el error
            showWrong();


        }else{  //si es correcta
            //si el sonido está activado, reproduce un sonidp
            if(isSoundON ==true){
                //reproduce un sonido
                mp = MediaPlayer.create(this, R.raw.win);
                mp.start();
            }
            //sumamos puntuaciones y actualizamos el score
            aciertos++;
            score+=10;
            tv_score.setText(String.valueOf(score));
        }

        //Finalmente, mostramos la opcion correcta
        showAnswer(question.getAnswer());

    };

    /**
     * Marca la respuesta como incorrecta
     */
    private void showWrong() {
        //resetDefaultView();
        switch (selectedOptionPosition){
            case 1:
                option1.setBackground(getResources().getDrawable(R.drawable.option_wrong));
                break;
            case 2:
                option2.setBackground(getResources().getDrawable(R.drawable.option_wrong));
                break;
            case 3:
                option3.setBackground(getResources().getDrawable(R.drawable.option_wrong));
                break;
            case 4:
                option4.setBackground(getResources().getDrawable(R.drawable.option_wrong));
                break;
        }


    }

    /**
     * Muestra la respuesta correcta
     * @param correctPosition Respuesta correcta
     */
    private void showAnswer(int correctPosition){

        switch (correctPosition){
            case 1:
                option1.setBackground(getDrawable(R.drawable.option_right));
                break;
            case 2:
                option2.setBackground(getDrawable(R.drawable.option_right));
                break;
            case 3:
                option3.setBackground(getDrawable(R.drawable.option_right));
                break;
            case 4:
                option4.setBackground(getDrawable(R.drawable.option_right));
                break;
        }

    }

    /**
     * Inicia el contador
     */
    void startTimer() {
        timer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //actualiza la variable de tiempo restante
                timeLeftInMillis = millisUntilFinished;
                //actualizamos el valor del contador
                tv_timer.setText(String.valueOf(millisUntilFinished/1000));

                if (timeLeftInMillis < 10000) {
                    tv_timer.setTextColor(Color.BLUE);
                } else {
                    tv_timer.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onFinish() {

                //Reinica contador a 0
                timeLeftInMillis = 0;
                //Mostrar respuesta y mensaje de alerta
                showAnswer(currentPosition);
                //mostrarAlerta();
                showDialogGameOver();

            }
        }; timer.start();
    }



    /**
     * Muestra una alerta de GameOver
     */
    private void mostrarAlertaGameOver() {
        //ImageView image = new ImageView(this);
        //image.setImageResource(R.drawable.game_over);
        //LayoutInflater factory = LayoutInflater.from(this);
        //final View view = factory.inflate(R.layout.imagen, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER");
        builder.setMessage("LA PARTIDA HA TERMINADO");
        builder.setCancelable(false);
        //builder.setView(image);

        //Volver a jugar
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Ir a la pantalla resultados
        builder.setNegativeButton("Ver resultados", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishQuiz();
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    /**
     * Muestra una ventana de dialogo, con opciones para el usuario
     */
    public void showDialogGameOver(){

        //Create the Dialog here
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_game_over);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialgog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button cancel=dialog.findViewById(R.id.btn_cancel);


        if(isSoundON ==true){
            //reproduce un sonido
            mp = MediaPlayer.create(this, R.raw.game_over);
            mp.start();
        }

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
                finish();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        dialog.show();

    }


    /**
     * Método que permite salir de la aplicación si presionames el botón volver dos veces,
     * con cierto intervalo de tiempo (2 seguntos)
     */
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            //finishQuiz();
            finish();
        } else {
            Toast.makeText(this, "Pulsa dos veces para finalizar", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    /**
     * Al destruir la actividad, se cancela el contador
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Almacena el último estado de la aplicación, si existe algún cambio
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_ACIERTOS, aciertos);
        outState.putInt(KEY_COUNT, counter);
        outState.putInt(KEY_CURRENT_POSITON, currentPosition);
        outState.putLong(KEY_TIMELEFT, timeLeftInMillis);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }


    /**
     * Muestra un mensaje modal de alerta con opciones para el usuario
     */
    public void mostrarAlertaFinalizar(){

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setTitle("FINALIZAR TEST");
        builder.setIcon(R.drawable.nav_info);
        builder.setMessage("¿Está seguro que desea finalizar el test?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
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
     * Muestra un mensaje modal de alerta con opciones para el usuario
     */
    public void mostrarAlerta(String titulo, String mensaje){

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setIcon(R.drawable.nav_info);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }




}