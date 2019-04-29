package com.example.yuvrajtalukdar.expensemonitor;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class help extends AppCompatActivity {
    viewPageAdapter adapter;
    int[] f46d = new int[]{C0302R.drawable.help1, C0302R.drawable.help2, C0302R.drawable.help3};
    private ViewPager viewPager;

    class C03131 implements OnClickListener {
        C03131() {
        }

        public void onClick(View v) {
            help.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0302R.layout.activity_help);
        getSupportActionBar().hide();
        ((ConstraintLayout) findViewById(C0302R.id.help_constrainlayout)).setBackgroundColor(-16711681);
        final Button skip_button = (Button) findViewById(C0302R.id.skip_button);
        skip_button.setTextColor(-65281);
        skip_button.setBackgroundColor(-16776961);
        skip_button.setOnClickListener(new C03131());
        final TextView dot = (TextView) findViewById(C0302R.id.dot_animation_textview);
        this.viewPager = (ViewPager) findViewById(C0302R.id.container);
        this.adapter = new viewPageAdapter(this, this.f46d);
        this.viewPager.setAdapter(this.adapter);
        String s1 = ".";
        dot.setText(Html.fromHtml("<font color=#cc0029>.</font><font color=#ffcc00>.</font><font color=#ffcc00>.</font>"));
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 0) {
                    dot.setText(Html.fromHtml("<font color=#cc0029>.</font><font color=#ffcc00>.</font><font color=#ffcc00>.</font>"));
                } else if (position == 1) {
                    dot.setText(Html.fromHtml("<font color=#ffcc00>.</font><font color=#cc0029>.</font><font color=#ffcc00>.</font>"));
                } else if (position == 2) {
                    dot.setText(Html.fromHtml("<font color=#ffcc00>.</font><font color=#ffcc00>.</font><font color=#cc0029>.</font>"));
                    skip_button.setText("Start");
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
