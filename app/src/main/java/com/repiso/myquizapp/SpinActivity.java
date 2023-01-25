package com.repiso.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

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
    List<WheelItem> wheelItems;
    private Button btn_spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);


        luckyWheel=(LuckyWheel) findViewById(R.id.luckyWheel);
        btn_spin=(Button)findViewById(R.id.btn_spin);

        generateWheelItems();
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Toast.makeText(getApplicationContext(), "Target reached", Toast.LENGTH_SHORT).show();
            }
        });

        btn_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luckyWheel.rotateWheelTo(1);
            }
        });

    }

    private void generateWheelItems() {
        wheelItems=new ArrayList<>();
        wheelItems.add(new WheelItem(Color.RED,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "25"));
        wheelItems.add(new WheelItem(Color.GREEN,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "30"));
        wheelItems.add(new WheelItem(Color.BLUE,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "45"));
        wheelItems.add(new WheelItem(Color.BLACK,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "50"));
        wheelItems.add(new WheelItem(Color.YELLOW,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "55"));
        wheelItems.add(new WheelItem(Color.GRAY,
                BitmapFactory.decodeResource(getResources(),R.drawable.science), "60"));
    }


}