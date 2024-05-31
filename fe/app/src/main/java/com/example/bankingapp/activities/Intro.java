package com.example.bankingapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.SlideIntroAdapter;

import java.util.stream.IntStream;

public class Intro extends AppCompatActivity {
    ViewPager slide_view_pager;
    SlideIntroAdapter slideIntroAdapter;
    Runnable autoChangeSlide;
    Handler handler;
    boolean isTouch;
    Button sign_up_button;
    TextView sign_in_button, skip_button;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        isTouch = false;

        slide_view_pager = findViewById(R.id.slides);
        skip_button = findViewById(R.id.skip_button);
        sign_up_button = findViewById(R.id.sign_up_button);
        sign_in_button = findViewById(R.id.sign_in_button);

        slideIntroAdapter = new SlideIntroAdapter(this);

        slide_view_pager.setAdapter(slideIntroAdapter);

        slide_view_pager.setOnTouchListener((view, event) -> {
            isTouch = true;
            return false;
        });

        slide_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                paginate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_DRAGGING && isTouch) {
                    // Bắt đầu scroll
                    handler.removeCallbacks(autoChangeSlide);

                } else if (state == ViewPager.SCROLL_STATE_IDLE && isTouch) {
                    // Kết thúc scroll
                    isTouch = false;
                    changeSlide(3000);
                }

            }
        });

        skip_button.setOnClickListener(view -> {
            Log.i("TAG", "click skip");
        });

        sign_up_button.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });

        sign_in_button.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        });

        initPagination(0);
        changeSlide(3000);

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(autoChangeSlide);
        super.onDestroy();

    }
    private void paginate(int activePosition) {
        LinearLayout pagination = findViewById(R.id.pagination);
        pagination.removeAllViews();

        IntStream.range(0, slideIntroAdapter.getCount())
                .mapToObj(index -> new TextView(this))
                .peek(dot -> {
                    dot.setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
                    dot.setTextSize(35);
                    dot.setTextColor(getResources().getColor(R.color.text_black_semi, getApplicationContext().getTheme()));
                })
                .forEach(pagination::addView);

        ((TextView)pagination.getChildAt(activePosition))
                .setTextColor(getResources().getColor(R.color.primary, getApplicationContext().getTheme()));
    }
    private void initPagination(int defaultPage) {

        paginate(defaultPage);
        slide_view_pager.setCurrentItem(defaultPage);
    }
    private void changeSlide(int time) {
        handler = new Handler();
        autoChangeSlide = () -> {
            int currentPage = slide_view_pager.getCurrentItem();
            int totalPage = slideIntroAdapter.getCount();
            int pageWillChange = currentPage == totalPage-1?0:currentPage+1;
            slide_view_pager.setCurrentItem(pageWillChange, true);
            handler.postDelayed(autoChangeSlide, time);
        };

        handler.postDelayed(autoChangeSlide, time);

    }

}