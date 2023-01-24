package com.repiso.myquizapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterRanking extends RecyclerView.Adapter<RecyclerViewAdapterRanking.ViewHolderRanking> {

    private ArrayList<Score> scoreList;
    private Score score;
    private Context context;


    /**
     * constructor del Adapter. Gestiona el arrayList
     * @param scoreList
     */
    public RecyclerViewAdapterRanking(ArrayList<Score> scoreList, Context context) {
        this.context=context;
        this.scoreList = scoreList;
    }

    /**
     * Método que infla el layout (archivo item XML) que representa a nuestros elementos,
     * y devuelve una instancia de la clase ViewHolder. Renderiza cada elemento del RecyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolderRanking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creamos e inflamos la vista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_list, parent,false);
        //Instanciamos un nuevo objeto viewholder. Aquí podriamos enlazar los estilos (definidos en el cardview xml)
        ViewHolderRanking vhr = new ViewHolderRanking(v);
        return vhr;
    }

    /**
     * Asigna los datos. Enlaza cada elemento de nuestro arraylist con cada ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull  ViewHolderRanking holder, @SuppressLint("RecyclerView") int position) {

        // obtenemos un elemento del arralist según su posición y lo enlazamos con el viewholder
        if(scoreList !=null && scoreList.size()>0){

            score = scoreList.get(position);

            holder.icono.setImageResource(R.drawable.imgprofile);
            holder.nombre.setText(String.valueOf(score.getUsername()));
            holder.score.setText(String.valueOf(score.getScore()));
            holder.position.setText(String.valueOf(position+1));

        }else {
            return;
        }

        /**
         * Método que permite generar eventos a cada elemento de la vista
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = scoreList.get(position);

            }
        });


    }

    /**
     * devuelve la cantidad de elementos a mostrar en el RecyclerView.
     * @return
     */
    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    /**
     * Clase interna que permite obtener referencias de los componentes visuales (views)
     * de cada elemento de la lista
     */
    public class ViewHolderRanking extends RecyclerView.ViewHolder {
        TextView nombre, score, level,category, position;
        ImageView icono;

        /**
         * Construcor ViewHolder
         */
        public ViewHolderRanking(@NonNull View itemView) {
            super(itemView);

            icono=(ImageView)itemView.findViewById(R.id.iv_icono_item);
            nombre =(TextView)itemView.findViewById(R.id.tv_nombre_item);
            score =(TextView)itemView.findViewById(R.id.tv_score_item);
            position =(TextView)itemView.findViewById(R.id.tv_position_item);


        }
    }


}

