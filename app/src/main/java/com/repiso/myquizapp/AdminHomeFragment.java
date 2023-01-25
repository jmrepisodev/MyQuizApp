package com.repiso.myquizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminHomeFragment extends Fragment {

    private RecyclerViewAdapterResultados recyclerViewAdapterResultados;
    private RecyclerView recyclerView;
    private ArrayList<Resultado> resultadoArrayList;
    private DBHelper dbHelper;

    private Spinner sp_category;
    private ImageButton btn_category, btn_general;
    private String categoria;
    private ArrayList<Category> categoryArrayList;
    private TextView tv_name, tv_email;
    private String username,email;
    private int userID;
    private String[] categoriasList;
    private SessionManager sessionManager;

    public AdminHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_home, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewResultados);
        sp_category=(Spinner)view.findViewById(R.id.spinnerCategoryRanking);
        btn_category=(ImageButton)view.findViewById(R.id.btn_category);
        btn_general=(ImageButton)view.findViewById(R.id.btn_general);
        tv_name=(TextView)view.findViewById(R.id.tv_name_home);

        //Inicia sesión de usuario
        sessionManager = new SessionManager(getContext());
        username=sessionManager.getUserName();
        userID=sessionManager.getUserId();
        email=sessionManager.getUserEmail();

        tv_name.setText(username);

        resultadoArrayList=new ArrayList<>();
        dbHelper=DBHelper.getInstance(getContext());

        //Obtenemos la lista de categorías de la base de datos
        categoryArrayList=dbHelper.getCategoryList();
        //Llenamos la lista de categorías desde un Array de recursos
        // categoriasList =this.getResources().getStringArray(R.array.categorias_ranking);

        //Llenamos el spinner de categorías desde la base de datos
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
        //Cargamos los resultados generales
        resultadoArrayList=dbHelper.getResultadosList();
        //mostramos la lista
        iniciarRecyclerview();

        // Listado de resultados por categoría
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Eliminamos la lista previa y notificamos los cambios
                if(resultadoArrayList.size()!=0){
                    resultadoArrayList.clear();
                    if(recyclerViewAdapterResultados!=null){
                        recyclerViewAdapterResultados.notifyDataSetChanged();
                    }
                }

                //Obtenemos la categoria
                categoria= sp_category.getSelectedItem().toString().trim();
                //cargamos la lista por categoría
                resultadoArrayList =dbHelper.getResultadoListByCategory(categoria);

                iniciarRecyclerview();
            }
        });

        btn_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Eliminamos la lista previa y notificamos los cambios
                if(resultadoArrayList.size()!=0){
                    resultadoArrayList.clear();
                    if(recyclerViewAdapterResultados!=null){
                        recyclerViewAdapterResultados.notifyDataSetChanged();
                    }
                }

                //Cargamos los resultados generales
                resultadoArrayList=dbHelper.getResultadosList();
                //mostramos la lista
                iniciarRecyclerview();
            }
        });

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