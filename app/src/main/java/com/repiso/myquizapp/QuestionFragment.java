package com.repiso.myquizapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class QuestionFragment extends Fragment  {

    private EditText et_question, et_option_1, et_option_2, et_option_3, et_option_4;
    private Spinner sp_category, sp_answer, sp_level;
    private Button btn_save;
    private String question, option1, option2, option3, option4, answer, category, level;
    private DBHelper dbHelper;
    private String[] categoryList, levelList, answerList;

    private ArrayList<Category> categoryArrayList;

    public QuestionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_question, container, false);


        et_question=(EditText) view.findViewById(R.id.editTextQuestion);
        et_option_1=(EditText) view.findViewById(R.id.editTextOption_1);
        et_option_2=(EditText) view.findViewById(R.id.editTextOption_2);
        et_option_3=(EditText) view.findViewById(R.id.editTextOption_3);
        et_option_4=(EditText) view.findViewById(R.id.editTextOption_4);
        sp_answer=(Spinner) view.findViewById(R.id.spinnerAnswer);
        sp_level=(Spinner) view.findViewById(R.id.spinnerLevel);
        sp_category=(Spinner) view.findViewById(R.id.spinnerCategory);
        btn_save=(Button) view.findViewById(R.id.btn_save_question);


        //Inicializa las bases de datos SQLite
        dbHelper=DBHelper.getInstance(getContext());

        //Obtenemos la lista de categorías de la base de datos
        categoryArrayList=dbHelper.getCategoryList();

        if(categoryArrayList!=null && categoryArrayList.size()>0){

            categoryList = new String[categoryArrayList.size()];

            for (int i = 0; i <categoryArrayList.size(); i++) {
                categoryList[i] = categoryArrayList.get(i).getName();
            }


        //Creamos un adapter para el spinner (contexto, layout, array)
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categoryList);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //le pasamos el adapter al spinner
        sp_category.setAdapter(adapterCategorias);

        }

        //Llenamos las listas desde un Array de recursos
        // categoriasList =this.getResources().getStringArray(R.array.categorias);
        levelList=getResources().getStringArray(R.array.niveles);
        answerList=getResources().getStringArray(R.array.respuestas);

        //Creamos un adapter para cada spinner (contexto, layout, array)

        ArrayAdapter<String> adapterNiveles =new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,levelList);
        adapterNiveles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_level.setAdapter(adapterNiveles);

        ArrayAdapter<String> adapterRespuestas=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,answerList);
        adapterNiveles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_answer.setAdapter(adapterRespuestas);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question=et_question.getText().toString().trim();
                option1=et_option_1.getText().toString().trim();
                option2=et_option_2.getText().toString().trim();
                option3=et_option_3.getText().toString().trim();
                option4=et_option_4.getText().toString().trim();

                //answer=sp_answer.getSelectedItem().toString().trim();
                answer=String.valueOf(sp_answer.getSelectedItemPosition()+1);
                level=sp_level.getSelectedItem().toString().trim();
                //Recuperamos la posición +1 (ID categoría)
                int categoryID=sp_category.getSelectedItemPosition()+1;
               // Toast.makeText(getContext(),"ID CATEGORIA: "+categoryID,Toast.LENGTH_LONG).show();
                if(TextUtils.isEmpty(question) || TextUtils.isEmpty(option1) || TextUtils.isEmpty(option2) || TextUtils.isEmpty(option3) || TextUtils.isEmpty(option4) ){
                    mostrarAlerta("ERROR", "Error: Existen campos vacíos. Todos los campos son obligatorios");
                }else {
                    Question q=new Question(question,option1,option2, option3,option4,Integer.parseInt(answer),0,level,categoryID);

                    if(q!=null && dbHelper.addQuestion(q)!=-1){

                        mostrarAlerta("REGISTRO SATISFACTORIO", "La pregunta se ha insertado correctamente");

                        //Limpiamos los campos de texto
                        et_question.getText().clear();
                        et_option_1.getText().clear();
                        et_option_2.getText().clear();
                        et_option_3.getText().clear();
                        et_option_4.getText().clear();


                    }else{
                        mostrarAlerta("REGISTRO NO VÁLIDO", "Error: No ha sido posible registrar la pregunta.");
                    }

                }

            }
        });

        return view;
    }

    /**
     * Muestra un mensaje modal de alerta con opciones para el usuario
     */
    public void mostrarAlerta(String titulo, String mensaje){

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle(titulo);
        builder.setIcon(R.drawable.nav_info);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


}