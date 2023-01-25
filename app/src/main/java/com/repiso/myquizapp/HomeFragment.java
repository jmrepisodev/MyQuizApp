package com.repiso.myquizapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewCategory;
    private RecyclerViewAdapterCategory recyclerViewAdapter;
    private ArrayList<Category> categoryList;

    private SessionManager sessionManager;
    private DBHelper dbHelper;

    private Button btn_spin, btn_share;
    private TextView tv_highscore;
    private int highScore;
    private String username, email;
    private int userID;
    private TextView tv_name;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewCategory =(RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        btn_spin=(Button)view.findViewById(R.id.btn_spin);
        btn_share=(Button)view.findViewById(R.id.btn_share);
        tv_highscore=(TextView)view.findViewById(R.id.tv_highscore);
        tv_name=(TextView)view.findViewById(R.id.tv_name_home);

        //Inicia la lista de categorías
        categoryList=new ArrayList();
        //categoryList= Utilidades.getCategoryList();

        //Inicializa las bases de datos SQLite
        dbHelper=DBHelper.getInstance(getContext());
        //Obtiene la lista de categorías de la base de datos
        categoryList=dbHelper.getCategoryList();

        iniciarRecyclerview();

        //Inicia sesión de usuario
        sessionManager = new SessionManager(getContext());

        username=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();
        highScore=sessionManager.getHighScore();

        tv_highscore.setText(String.valueOf(highScore));
        tv_name.setText(username);

        btn_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SpinActivity.class);
                startActivity(intent);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey!! Te invito a descargar y jugar con la app Multijuegos. Descarga aquí http://repisoserver.freevar.com");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Inicia el RecyclerView
     */
    private void iniciarRecyclerview() {
        // Esta línea mejora el rendimiento, si sabemos que el contenido no va a afectar al tamaño del RecyclerView
        recyclerViewCategory.setHasFixedSize(true);
        // Establece el layoutManager del recyclerview.
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(),2));
        // Creamos el adaptador, pasándole los datos que va a mostrar
        recyclerViewAdapter=new RecyclerViewAdapterCategory(categoryList, getContext());
        // Asociamos el adapter al recyclerView
        recyclerViewCategory.setAdapter(recyclerViewAdapter);
    }


}

