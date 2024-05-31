package com.example.bankingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bankingapp.R;

public class SlideIntroAdapter extends PagerAdapter {
    Context context;
    int[] images ={
            R.drawable.slide_intro_1,
            R.drawable.slide_intro_2,
    };
    String[] titles ={
        "Order from your favourite stores or vendors",
            "Choose from a wide range of delicious meals"

    };
    public SlideIntroAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slide_image = (ImageView) view.findViewById(R.id.image_slide);
        TextView slide_title = (TextView) view.findViewById(R.id.title_slide);

        slide_image.setImageResource(images[position]);
        slide_title.setText(titles[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
