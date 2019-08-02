package com.example.bloodbank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.SliderModel;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {
    Context context;
    ArrayList<SliderModel> arrayList;
    LayoutInflater inflater;
    View view;

    public SliderAdapter(Context context, ArrayList<SliderModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_slide, container, false);
        container.addView(view);

        ImageView sliderAdapterImg = view.findViewById(R.id.sliderAdapterImg);
        sliderAdapterImg.setImageResource(arrayList.get(position).getImgSlder());
        return view;
    }
}
