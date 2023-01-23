package com.repiso.myquizapp;

import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_ID;
import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_NAME;
import static com.repiso.myquizapp.MainActivity.KEY_LEVEL;
import static com.repiso.myquizapp.QuestionActivity.KEY_ACIERTOS;
import static com.repiso.myquizapp.QuestionActivity.KEY_ENBLANCO;
import static com.repiso.myquizapp.QuestionActivity.KEY_FALLOS;
import static com.repiso.myquizapp.QuestionActivity.KEY_SCORE;
import static com.repiso.myquizapp.QuestionActivity.KEY_TOTAL_QUESTIONS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    protected static final String HIGH_SCORE = "highScore";
    protected static final String KEY_HIGHSCORE = "keyHighscore";

    private int highscore;
    private RatingBar ratingBar;

    private MediaPlayer mp;

    private SessionManager sessionManager;
    private DBHelper dbHelper;

    private String username,email;
    private int userID;


    private String categoryName;
    private int categoryID;
    private String difficulty;
    private int totalQuestions;
    private int aciertos;
    private int score;
    private int errores;
    private int enBlanco;

    ImageView iv_result;
    TextView tv_name, tv_score, tv_hits, tv_title_score, tv_congratulations,tv_wrongs, tv_level,tv_category, tv_enBlanco, tv_highscore;
    Button btn_finish, btn_replay;

    private boolean isSoundON;
    private boolean isLogin, isVibrationON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        ratingBar=(RatingBar) findViewById(R.id.ratingBar);

        iv_result=(ImageView)findViewById(R.id.iv_result);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_level =(TextView)findViewById(R.id.tv_level_result);
        tv_category=(TextView)findViewById(R.id.tv_category_result);
        tv_score=(TextView)findViewById(R.id.tv_score_result);
        tv_hits =(TextView)findViewById(R.id.tv_hits);
        tv_wrongs=(TextView)findViewById(R.id.tv_wrongs);
        tv_enBlanco=(TextView)findViewById(R.id.tv_EnBlanco);
        tv_title_score =(TextView)findViewById(R.id.tv_title_score);
        btn_finish=(Button)findViewById(R.id.btn_finish);
        btn_replay=(Button)findViewById(R.id.btn_restart);
        tv_highscore=(TextView)findViewById(R.id.tv_highscore_resultado);


        //Inicia sesión de usuario
        sessionManager = new SessionManager(getApplicationContext());
        username=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();

        //Recupera las preferencias de configuración
        Preferencias.getPreferences(this);
        isSoundON=Preferencias.sound;

        cargarResultados();


        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),QuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    /**
     * Carga los resultados de la partida y muestra los resultados en pantalla
     */
    private void cargarResultados() {
        if(getIntent().getExtras()!=null){

            difficulty=getIntent().getExtras().getString(KEY_LEVEL);
            categoryID =getIntent().getExtras().getInt((KEY_CATEGORY_ID));
            categoryName =getIntent().getExtras().getString((KEY_CATEGORY_NAME));
            totalQuestions=getIntent().getExtras().getInt(KEY_TOTAL_QUESTIONS);
            aciertos=getIntent().getExtras().getInt(KEY_ACIERTOS);
            errores=getIntent().getExtras().getInt(KEY_FALLOS);
            enBlanco=getIntent().getExtras().getInt(KEY_ENBLANCO);
            score = getIntent().getExtras().getInt(KEY_SCORE);

        }


        //configurar ratingBar
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5f);
        ratingBar.setMax(totalQuestions);
        ratingBar.setRating(score/10);


        sessionManager=new SessionManager(this);
        highscore=sessionManager.getHighScore();
        if (score > highscore) {
            highscore=score;
            sessionManager.setHighScore(highscore);
        }

        tv_highscore.setText("HighScore: "+aciertos);
        tv_name.setText(username);
        tv_hits.setText("Aciertos: "+aciertos);
        tv_wrongs.setText("Errores: "+errores);
        tv_enBlanco.setText("En blanco: "+enBlanco);
        tv_score.setText(String.valueOf(score));
        tv_level.setText(difficulty);
        tv_category.setText(categoryName);
        tv_title_score.setText("Score: "+ aciertos +"/" +totalQuestions);


        //Se reproduce un sonido de aplausos si supera el 50%
        if(score>=((totalQuestions*10)/2)){
            if(isSoundON==true){
                //reproduce un sonido de victoria
                mp = MediaPlayer.create(this, R.raw.game_win);
                mp.start();
            }
            //si es inferior al 50% reproduce un sonido de game over
        }else if(score<((totalQuestions*10)/2)){
            if(isSoundON==true){
                //reproduce un sonido de victoria
                mp = MediaPlayer.create(this, R.raw.game_over);
                mp.start();
            }
        }

    }

    /**
     * Muestra un mensaje texto y una imagen diferente, según los resultados obtenidos
     * @param score
     */
    private void actualizarResultado(int score){

        switch (score)
        {
            case 0:
                tv_congratulations.setText("Has obtenido: 0%, Sigue aprendiendo");
                break;
            case 1:
                tv_congratulations.setText("Has obtenido: 20%, Sigue estudiando");
                break;
            case 2:
                tv_congratulations.setText("Has obtenido: 40%, Estudia un poco más");
                break;
            case 3:
                tv_congratulations.setText("Has obtenido: 60%, Buen intento");
                break;
            case 4:
                tv_congratulations.setText("Has obtenido: 80%, Lo has hecho genial");
                break;
            case 5:
                tv_congratulations.setText("Tu puntuación: 100%, Eres un crack");
                break;
        }
    }




}