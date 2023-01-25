package com.repiso.myquizapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterResultados extends RecyclerView.Adapter<RecyclerViewAdapterResultados.ViewHolderResultados> {

    private ArrayList<Resultado> resultadoArrayList;
    private Resultado resultado;
    private Context context;
    private DBHelper dbHelper;


    /**
     * constructor del Adapter. Gestiona el arrayList
     * @param resultadoArrayList
     */
    public RecyclerViewAdapterResultados(ArrayList<Resultado> resultadoArrayList, Context context) {
        this.context=context;
        this.resultadoArrayList = resultadoArrayList;
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
    public ViewHolderResultados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creamos e inflamos la vista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado_list, parent,false);
        //Instanciamos un nuevo objeto viewholder. Aquí podriamos enlazar los estilos (definidos en el cardview xml)
        ViewHolderResultados vhr = new ViewHolderResultados(v);
        return vhr;
    }

    /**
     * Asigna los datos. Enlaza cada elemento de nuestro arraylist con cada ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull  ViewHolderResultados holder, @SuppressLint("RecyclerView") int position) {

        // obtenemos un elemento del arralist según su posición y lo enlazamos con el viewholder
        if(resultadoArrayList !=null && resultadoArrayList.size()>0){

            resultado = resultadoArrayList.get(position);

            holder.icono.setImageResource(R.drawable.imgprofile);
            holder.nombre.setText(resultado.getUsername());
            holder.aciertos.setText("Aciertos: "+resultado.getAciertos());
            holder.errores.setText("Fallos: "+resultado.getFallos());
            holder.enBlanco.setText("En blanco: "+resultado.getEnBlanco());
            holder.score.setText("Score: " + resultado.getScore());

            int resultadoID=resultado.getID();

            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHelper=DBHelper.getInstance(view.getContext());
                    Toast.makeText(context, "position: "+position, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "ID: "+resultadoID, Toast.LENGTH_SHORT).show();
                    dbHelper.deleteResultado(resultadoID);
                    resultadoArrayList.remove(position);
                    notifyDataSetChanged();



                }
            });

        }else {
            return;
        }




    }

    /**
     * devuelve la cantidad de elementos a mostrar en el RecyclerView.
     * @return
     */
    @Override
    public int getItemCount() {
        return resultadoArrayList.size();
    }

    /**
     * Clase interna que permite obtener referencias de los componentes visuales (views)
     * de cada elemento de la lista
     */
    public class ViewHolderResultados extends RecyclerView.ViewHolder {
        TextView nombre, score,aciertos, errores, enBlanco;
        ImageButton btn_delete;
        ImageView icono;

        /**
         * Construcor ViewHolder
         */
        public ViewHolderResultados(@NonNull View itemView) {
            super(itemView);

            icono=(ImageView)itemView.findViewById(R.id.iv_icono_item);
            nombre =(TextView)itemView.findViewById(R.id.tv_nombre_item);
            score =(TextView)itemView.findViewById(R.id.tv_score_item);
            aciertos =(TextView)itemView.findViewById(R.id.tv_aciertos);
            errores =(TextView)itemView.findViewById(R.id.tv_fallos);
            enBlanco =(TextView)itemView.findViewById(R.id.tv_enblanco);
            btn_delete =(ImageButton)itemView.findViewById(R.id.ib_delete);


        }
    }


}

