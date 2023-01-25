package com.repiso.myquizapp;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class CategoryFragment extends Fragment {

    private ImageView imageView;
    private EditText et_category;
    private String category;
    private Button btn_camera, btn_save;
    private DBHelper dbHelper;

    public CategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);

        imageView=(ImageView) view.findViewById(R.id.imageViewCategory);
        btn_camera=(Button) view.findViewById(R.id.btn_img);
        btn_save=(Button) view.findViewById(R.id.btn_save_category);
        et_category=(EditText)view.findViewById(R.id.editTextCategory);

       // dbHelper=new DBHelper(getContext());
        dbHelper=DBHelper.getInstance(getContext());

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=et_category.getText().toString().trim();

                if(TextUtils.isEmpty(category)){
                    mostrarAlerta("ERROR", "Error: Existen campos vacíos. Todos los campos son obligatorios");
                }else {
                    Category c=new Category(category,R.drawable.quizz);

                    if(c!=null && dbHelper.addCategory(c)!=-1){

                        mostrarAlerta("REGISTRO SATISFACTORIO", "La categoría se ha insertado correctamente");

                        //Limpiamos los campos de texto
                        et_category.getText().clear();

                    }else{
                        mostrarAlerta("REGISTRO NO VÁLIDO", "Error: No ha sido posible registrar la pregunta.");
                    }

                }
            }
        });


        return view;
    }


    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imgBitmap);
        }
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


