package com.repiso.myquizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminHomeFragment extends Fragment {

    private RecyclerViewAdapterResultados recyclerViewAdapterResultados;
    private RecyclerView recyclerView;
    private ArrayList<Resultado> resultadoArrayList;
    private DBHelper dbHelper;

    public AdminHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_home, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewResultados);

        resultadoArrayList=new ArrayList<>();
        dbHelper=DBHelper.getInstance(getContext());
        resultadoArrayList=dbHelper.getResultadosList();

        iniciarRecyclerview();

        return view;
    }


    private void iniciarRecyclerview() {
        if(resultadoArrayList.size()==0){
            Toast.makeText(getContext(), "La lista está vacía", Toast.LENGTH_LONG).show();
        }else{
            // Esta línea mejora el rendimiento, si sabemos que el contenid no va a afectar al tamaño del RecyclerView
            recyclerView.setHasFixedSize(true);
            // Establece el layoutManager del recyclerview
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            // Creamos el adaptador, pasándole los datos que va a mostrar
            recyclerViewAdapterResultados=new RecyclerViewAdapterResultados(resultadoArrayList, getContext());
            // Asociamos el adapter al recyclerView
            recyclerView.setAdapter(recyclerViewAdapterResultados);
            // muestra los cambios
            recyclerViewAdapterResultados.notifyDataSetChanged();
        }

    }



}