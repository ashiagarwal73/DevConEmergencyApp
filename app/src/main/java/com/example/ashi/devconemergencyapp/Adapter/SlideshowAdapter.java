package com.example.ashi.devconemergencyapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ashi.devconemergencyapp.R;

import java.util.ArrayList;

public class SlideshowAdapter extends PagerAdapter {

    ArrayList<Integer> imageResIds;

    public SlideshowAdapter(ArrayList<Integer> imageResIds) {
        this.imageResIds = imageResIds;
    }
    @Override
    public int getCount() {
        return imageResIds.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        System.out.println("instantiate item "+position);
        View slideLayout = LayoutInflater.from(container.getContext()).inflate(R.layout.slide_layout,container,false);
        ImageView imageView = slideLayout.findViewById(R.id.image_view_slide);
        imageView.setImageResource(imageResIds.get(position));

        container.addView(slideLayout);

        return slideLayout;
    }

    @Override
    public void destroyItem(ViewGroup container,int position, Object key) {
        container.removeView((View)key);
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
