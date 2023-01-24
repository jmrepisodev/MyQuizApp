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

    private Spinner sp_category;
    private ImageButton btn_search;
    private String categoria;
    private ArrayList<Category> categoryArrayList;
    private String[] categoriasList;


    public RankingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_ranking, container, false);

        sp_category=(Spinner)view.findViewById(R.id.spinnerCategoryRanking);
        recyclerViewRanking=(RecyclerView)view.findViewById(R.id.recycler_Ranking);
        btn_search=view.findViewById(R.id.imageButtonSearch);

        //Inicializa las bases de datos SQLite
        dbHelper=DBHelper.getInstance(getContext());

        //Obtenemos la lista de categorías de la base de datos
        categoryArrayList=dbHelper.getCategoryList();
        //Llenamos la lista de categorías desde un Array de recursos
        // categoriasList =this.getResources().getStringArray(R.array.categorias_ranking);


        if(categoryArrayList!=null && categoryArrayList.size()>0){

            categoriasList = new String[categoryArrayList.size()];

            for (int i = 0; i <categoryArrayList.size(); i++) {
                categoriasList[i] = categoryArrayList.get(i).getName();
            }


            //Creamos un adapter para el spinner (contexto, layout, array)
            ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, categoriasList);
            adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //le pasamos el adapter al spinner
            sp_category.setAdapter(adapterCategorias);

        }


        //Inicia la lista de usuarios
        resultadoArrayList =new ArrayList();

        //Obtiene la lista de datos de la base de datos
        resultadoArrayList =dbHelper.getResultadosList();

        iniciarRecyclerview();


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Eliminamos la lista previa
                if(resultadoArrayList.size()!=0){
                    resultadoArrayList.clear();
                    if(recyclerViewAdapterRanking!=null){
                        recyclerViewAdapterRanking.notifyDataSetChanged();
                    }
                }

                //Obtenemos la categoria
                categoria= sp_category.getSelectedItem().toString().trim();
                //cargamos la lista por categoría
                resultadoArrayList =dbHelper.getResultadoListByCategory(categoria);

                iniciarRecyclerview();

            }
        });

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