package mrc.sad_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroSliderActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    Button mBackBtn;
    Button mNextBtn;
    private int mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotLayout);
        mBackBtn = findViewById(R.id.prevBtn);
        mNextBtn = findViewById(R.id.nextBtn);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent mainIntent = new Intent(IntroSliderActivity.this, ChooseActivity.class);
            startActivity(mainIntent);
            finish();
        }


        // DOTS
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        // CLICK NEXT BUTTOMN COMMAND

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                if (mCurrentPage == 2) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                }
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        // for back button
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

    }


    public void addDotsIndicator(int position) {
        mDots = new TextView[2];
        mDotLayout.removeAllViews();
        for (int i = 0; i < 2; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            mCurrentPage = position;

            if (mCurrentPage == 0) {
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setEnabled(true);

                mNextBtn.setText("Next");
                mBackBtn.setText("");

            } else if (mCurrentPage == mDots.length - 1) {
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setEnabled(true);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");
            }
            // ELSE
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
