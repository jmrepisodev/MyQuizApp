package com.repiso.myquizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class RankingFragment extends Fragment {

    private RecyclerViewAdapterRanking recyclerViewAdapterRanking;
    private RecyclerView recyclerViewRanking;
    private ArrayList<Resultado> resultadoArrayList;
    private DBHelper dbHelper;


    public RankingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_ranking, container, false);

        recyclerViewRanking=(RecyclerView)view.findViewById(R.id.recycler_Ranking);

        //Inicializa las bases de datos SQLite
        dbHelper=DBHelper.getInstance(getContext());

        //Inicia la lista de usuarios
        resultadoArrayList =new ArrayList();

        //Obtiene el ranking de usuarios de la base de datos
        resultadoArrayList =dbHelper.getRankingList();

        iniciarRecyclerview();




        // Inflate the layout for this fragment
        return view;
    }



    private void iniciarRecyclerview() {
        if(resultadoArrayList.size()==0){
            Toast.makeText(getContext(), "La lista está vacía", Toast.LENGTH_LONG).show();
        }else{

            // Esta línea mejora el rendimiento, si sabemos que el contenid no va a afectar al tamaño del RecyclerView
            recyclerViewRanking.setHasFixedSize(true);
            // Establece el layoutManager del recyclerview
            recyclerViewRanking.setLayoutManager(new LinearLayoutManager(getContext()));
            // Creamos el adaptador, pasándole los datos que va a mostrar
            recyclerViewAdapterRanking=new RecyclerViewAdapterRanking(resultadoArrayList, getContext());
            // Asociamos el adapter al recyclerView
            recyclerViewRanking.setAdapter(recyclerViewAdapterRanking);
            // muestra los cambios
            recyclerViewAdapterRanking.notifyDataSetChanged();
        }

    }

}