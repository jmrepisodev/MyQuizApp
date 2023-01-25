package com.repiso.myquizapp;

import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_ID;
import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_NAME;
import static com.repiso.myquizapp.MainActivity.KEY_LEVEL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

public class SpinActivity extends AppCompatActivity {

    private LuckyWheel luckyWheel;
    private ArrayList<WheelItem> wheelItems;
    private Button btn_spin;
    private int target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);


        luckyWheel=(LuckyWheel) findViewById(R.id.luckyWheel);
        btn_spin=(Button)findViewById(R.id.btn_spin);

        generateWheelItems();
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        btn_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //aleatorio, de 1 a 6
                target=(int) (Math.random()*6+1);
                luckyWheel.rotateWheelTo(target);
            }
        });

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
               // Toast.makeText(getApplicationContext(), "Target reached: "+target, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (target){
                    case 1:
                        playCategory("CIENCIAS",target);
                        break;
                    case 2:
                        playCategory("GEOGRAFIA",target);
                        break;
                    case 3:
                        playCategory("HISTORIA",target);
                        break;
                    case 4:
                        playCategory("LITERATURA",target);
                        break;
                    case 5:
                        playCategory("ESPECTACULOS",target);
                        break;
                    case 6:
                        playCategory("DEPORTES",target);
                        break;





                }
            }
        });


    }

    private void generateWheelItems() {
        wheelItems=new ArrayList<>();
        wheelItems.add(new WheelItem(Color.GREEN,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "CIENCIAS"));
        wheelItems.add(new WheelItem(Color.BLUE,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "GEOGRAFÍA"));
        wheelItems.add(new WheelItem(Color.RED,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "HISTORIA"));
        wheelItems.add(new WheelItem(Color.BLACK,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "LITERATURA"));
        wheelItems.add(new WheelItem(Color.MAGENTA,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "ESPECTACULOS"));
        wheelItems.add(new WheelItem(Color.GRAY,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "DEPORTES"));
    }


    /**
     * Muestra un mensaje modal de alerta con opciones para el usuario
     */
    public void playCategory(String categoryName, int categoryID){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Categoría: "+categoryName)
                .setItems(Utilidades.getLevelsList(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        String level=null;
                        // Toast.makeText(context, "Ha seleccionado: "+position, Toast.LENGTH_SHORT).show();
                        switch (position){
                            case 0:
                                level ="FACIL";
                                break;
                            case 1:
                                level ="DIFICIL";
                        }

                        Intent intent;
                        intent=new Intent(getApplicationContext(),QuestionActivity.class);
                        intent.putExtra(KEY_CATEGORY_ID,categoryID);
                        intent.putExtra(KEY_CATEGORY_NAME,categoryName);
                        intent.putExtra(KEY_LEVEL, level);
                        startActivity(intent);
                        finish();

                    }
                });
        builder.create().show();
    }


}