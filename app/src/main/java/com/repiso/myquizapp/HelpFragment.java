package com.repiso.myquizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class HelpFragment extends Fragment {

    private ViewPager2 viewPager2;
    private ArrayList<ViewPageItem> viewPageItemArrayList;

    public HelpFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_help, container, false);

        viewPager2=(ViewPager2) view.findViewById(R.id.viewpager);

        //Obtenemos los títulos,descripciones e imágenes de los itemViewPages (4 elementos, ordenados)
        String titulos[] = this.getResources().getStringArray(R.array.items_title_help);
        String descripciones[] = this.getResources().getStringArray(R.array.items_descriptions_help);
        int imagenes[]={R.drawable.quizz,R.drawable.quizz, R.drawable.quizz, R.drawable.quizz};


        viewPageItemArrayList=new ArrayList<>();

        //Instanciamos los ItemViewPages
        for(int i=0; i<titulos.length;i++){
            ViewPageItem viewPageItem=new ViewPageItem(imagenes[i],titulos[i],descripciones[i]);
            viewPageItemArrayList.add(viewPageItem);
        }

        SlidePagerAdapter slidePagerAdapter=new SlidePagerAdapter(viewPageItemArrayList);
        viewPager2.setAdapter(slidePagerAdapter);
        //Implementa el indicador de puntos
        CircleIndicator3 indicator = view.findViewById(R.id.circleIndicator2);
        indicator.setViewPager(viewPager2);

        // optional
        slidePagerAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        // viewPager2.setClipToPadding(false);
        // viewPager2.setClipChildren(false);
        //  viewPager2.setOffscreenPageLimit(2);
        // viewPager2.getChildAt(View.OVER_SCROLL_NEVER);



        return view;
    }
}