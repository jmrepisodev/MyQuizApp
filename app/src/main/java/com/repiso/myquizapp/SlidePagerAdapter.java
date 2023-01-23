package com.repiso.myquizapp;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SlidePagerAdapter extends RecyclerView.Adapter<SlidePagerAdapter.ViewHolder> {

   ArrayList<ViewPageItem> viewPageItemArrayList;

    public SlidePagerAdapter(ArrayList<ViewPageItem> viewPageItemArrayList) {
        this.viewPageItemArrayList = viewPageItemArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.img_help);
            title=itemView.findViewById(R.id.title_help);
            description=itemView.findViewById(R.id.tv_description_help);
            //scrolling
            description.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPageItem viewPageItem=viewPageItemArrayList.get(position);

        holder.imageView.setImageResource(viewPageItem.getImage());
        holder.title.setText(viewPageItem.getTitle());
        holder.description.setText(viewPageItem.getDescription());
    }



    @Override
    public int getItemCount() {
        return viewPageItemArrayList.size();
    }
}
