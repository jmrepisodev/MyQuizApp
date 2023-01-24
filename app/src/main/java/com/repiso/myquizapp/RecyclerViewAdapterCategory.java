package com.repiso.myquizapp;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_ID;
import static com.repiso.myquizapp.MainActivity.KEY_CATEGORY_NAME;
import static com.repiso.myquizapp.MainActivity.KEY_LEVEL;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterCategory extends RecyclerView.Adapter<RecyclerViewAdapterCategory.ViewHolderCategory> {


    private ArrayList<Category> categoryList;
    private SessionManager sessionManager;
    private Context context;

    /**
     * constructor del Adapter. Gestiona el arrayList
     * @param categoryList lista de categorías disponibles
     *
     */
    public RecyclerViewAdapterCategory(ArrayList<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context=context;

    }

    /**
     * Método que infla el layout (archivo item XML) que representa a nuestros elementos,
     * y devuelve una instancia de la clase ViewHolder. Renderiza cada elemento del RecyclerView
     * @param parent padre
     * @param viewType tipo de view
     * @return
     */
    @NonNull
    @Override
    public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creamos e inflamos la vista
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_category_list, parent,false);
        //Instanciamos un nuevo objeto viewholder. Aquí podriamos enlazar los estilos (definidos en el cardview xml)
        ViewHolderCategory vhc = new ViewHolderCategory(v);
        return vhc;
    }


    /**
     * Asigna los datos. Enlaza cada elemento de nuestro arraylist con cada ViewHolder
     * @param holder holder
     * @param position posición del item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategory holder, int position) {
        Category category;
        // obtenemos un elemento del arralist según su posición y lo enlazamos con el viewholder
        if(categoryList!=null && categoryList.size()>0){
            category=categoryList.get(position);

            holder.icono.setImageResource(category.getImage());
            holder.nombre.setText(category.getName());

        }else {
            return;
        }


        /**
         * Genera eventos a cada elemento de la vista
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
             /*  Intent intent =  new Intent(holder.itemView.getContext(), QuestionActivity.class);
               intent.setFlags(FLAG_ACTIVITY_NEW_TASK); //corrige error
               intent.putExtra(KEY_CATEGORY_ID, category.getId());
               holder.itemView.getContext().startActivity(intent);*/


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.select_level)
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

                                intent=new Intent(context,QuestionActivity.class);
                                intent.putExtra(KEY_CATEGORY_ID,category.getId());
                                intent.putExtra(KEY_CATEGORY_NAME,category.getName());
                                intent.putExtra(KEY_LEVEL, level);
                                context.startActivity(intent);

                            }
                        });
                builder.create().show();

            }
        });

    }

    /**
     * devuelve la cantidad de elementos a mostrar en el RecyclerView.
     * @return
     */
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    /**
     * Clase interna que permite obtener referencias de los componentes visuales (views)
     * de cada elemento de la lista
     */
    public class ViewHolderCategory extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView icono;

        /**
         * Construcor ViewHolder
         */
        public ViewHolderCategory(@NonNull View itemView) {
            super(itemView);
            icono=(ImageView)itemView.findViewById(R.id.iv_icono_item);
            nombre =(TextView)itemView.findViewById(R.id.tv_nombre_item);

        }
    }





}